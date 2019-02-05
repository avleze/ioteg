package com.ioteg.eplutils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jdom2.Element;

import com.ioteg.EventGenerator;
import com.ioteg.Trio;

public class EPLConditionsParser {
	private List<Integer> iterationValues;
	private List<Map<String, List<Trio<String, String, String>>>> eplRestrictions;
	private String eplFilePath;
	private Element rootNode;
	
	public EPLConditionsParser(String eplFilePath, Element rootNode) {
		super();
		this.iterationValues = new ArrayList<>();
		this.eplRestrictions = new ArrayList<>();
		this.eplFilePath = eplFilePath;
		this.rootNode = rootNode;
	}

	/**
	 * A function to obtain values from the fields elements at the where clauses of
	 * the EPL queries to assign them to the fields of the events
	 * 
	 * @param EPLfile  contains the EPL queries
	 * @param rootNode is the root of the event definition
	 * @return 
	 * @throws IOException if the file causes problems
	 */
	public List<Map<String, List<Trio<String, String, String>>>> getRestrictions(Integer totalNumEvents) throws IOException {
		FileReader eplF = new FileReader(new File(eplFilePath));
		String query = "";

		try (BufferedReader bf = new BufferedReader(eplF)) {
			while ((query = bf.readLine()) != null) {
				if (query.toLowerCase().contains(" where ")) {
					String queryAfterWhere = "";
					if (query.toLowerCase().indexOf(" where ") != -1) 
						queryAfterWhere = query.substring(query.toLowerCase().indexOf(" where ") + 7);
					

					Pattern logicalOperatorsPattern = Pattern.compile(" or | and | OR | AND ");
					Matcher logicalOperatorsMatcher = logicalOperatorsPattern.matcher(queryAfterWhere);
					List<String> logicalOperators = new ArrayList<>();

					while (logicalOperatorsMatcher.find()) {
						logicalOperators.add(logicalOperatorsMatcher.group().toLowerCase().trim());
					}


					Pattern relationalOperatorsPattern = Pattern.compile("<=|>=|<|>|=|!=");
					Matcher relationalOperatorsMatcher = relationalOperatorsPattern.matcher(queryAfterWhere);
					Trio<String, String, String> lastFieldPut = null;
					int i = 0; 
					
					while (relationalOperatorsMatcher.find()) {
						String field = queryAfterWhere.substring(0, relationalOperatorsMatcher.start());
						field = field.trim();
						String operator = relationalOperatorsMatcher.group();
						queryAfterWhere = queryAfterWhere.substring(relationalOperatorsMatcher.end() + 1);

						queryAfterWhere = queryAfterWhere.trim();

						Pattern wordPattern = Pattern.compile("(['\"])(.*?)(['\"][$]?)");
						Pattern numberPattern = Pattern.compile("(?<!\\w)-?[0-9]+[$]?");
						Pattern booleanPattern = Pattern.compile("true|false[$]?");

						Matcher wordMatcher = wordPattern.matcher(queryAfterWhere);
						Matcher numberMatcher = numberPattern.matcher(queryAfterWhere);
						Matcher booleanMatcher = booleanPattern.matcher(queryAfterWhere);
						String finalvalue = "";
						String matchedStr = "";

						if (wordMatcher.find()) {
							finalvalue = wordMatcher.group(2);
							matchedStr = wordMatcher.group();
						} else if (numberMatcher.find()) {
							finalvalue = numberMatcher.group(0);
							matchedStr = finalvalue;
						} else if (booleanMatcher.find()) {
							finalvalue = booleanMatcher.group(0);
							matchedStr = finalvalue;
						}

						queryAfterWhere = queryAfterWhere.substring(queryAfterWhere.indexOf(matchedStr) + matchedStr.length());
						Trio<String, String, String> singleRestriction = new Trio<>(field, operator, finalvalue);

						if (!queryAfterWhere.isEmpty() && !logicalOperators.isEmpty()) {
							String logicalOp = logicalOperators.get(0);
							if (logicalOp.equals("or"))
								queryAfterWhere = queryAfterWhere.substring(3);
							else
								queryAfterWhere = queryAfterWhere.substring(4);
						}

						if (i > 0 && !logicalOperators.isEmpty()) {
							if (logicalOperators.get(0).equals("or"))
								manageOrEPLOperator(lastFieldPut, singleRestriction, eplRestrictions);
							else if (logicalOperators.get(0).equals("and"))
								manageAndEPLOperator(singleRestriction, eplRestrictions);
							logicalOperators.remove(0);
						} else {
							if (eplRestrictions.isEmpty())
								manageEmptyFieldValues(singleRestriction, eplRestrictions);
							else {
								addSingleRestriction(singleRestriction, eplRestrictions);
							}
						}
						relationalOperatorsMatcher.reset();
						relationalOperatorsMatcher = relationalOperatorsPattern.matcher(queryAfterWhere);
						i++;

						lastFieldPut = singleRestriction;
					}
				}
			}
		}
		
		removingComplexValues(totalNumEvents);
		
		return eplRestrictions;
	}

	private void addSingleRestriction(Trio<String, String, String> value, List<Map<String, List<Trio<String, String, String>>>> eplRestrictions) {
		for (Map<String, List<Trio<String, String, String>>> values : eplRestrictions)
			if (values.get(value.getFirst()) != null)
				values.get(value.getFirst()).add(value);
			else {
				List<Trio<String, String, String>> list = new ArrayList<>();
				list.add(value);
				values.put(value.getFirst(), list);
			}
	}

	private void manageEmptyFieldValues(Trio<String, String, String> value, List<Map<String, List<Trio<String, String, String>>>> eplRestrictions) {
		{
			Map<String, List<Trio<String, String, String>>> values = new HashMap<>();
			List<Trio<String, String, String>> list = new ArrayList<>();
			list.add(value);
			values.put(value.getFirst(), list);
			eplRestrictions.add(values);
		}
	}

	private void manageAndEPLOperator(Trio<String, String, String> value, List<Map<String, List<Trio<String, String, String>>>> eplRestrictions) {
		addSingleRestriction(value, eplRestrictions);
	}

	private void manageOrEPLOperator(Trio<String, String, String> lastFieldPut,
		Trio<String, String, String> value, List<Map<String, List<Trio<String, String, String>>>> eplRestrictions) {
		int originalSize = eplRestrictions.size();
		for (int i = 0; i < originalSize; ++i) {
			Map<String, List<Trio<String, String, String>>> values = new HashMap<>();
			Map<String, List<Trio<String, String, String>>> restrictions = eplRestrictions.get(i);
			for (Entry<String, List<Trio<String, String, String>>> entry : restrictions.entrySet()) {
				List<Trio<String, String, String>> list = new ArrayList<>();
				list.addAll(entry.getValue());
				values.put(entry.getKey(), list);
			}

			values.get(lastFieldPut.getFirst()).remove(lastFieldPut);
			values.get(value.getFirst()).add(value);
			eplRestrictions.add(values);
		}
		
	}
	
	
	private void removingComplexValues(Integer totalNumEvents) {

		List<Element> fieldchilds = new ArrayList<>();

		for (Element block : rootNode.getChildren("block")) 
			if (block.getAttributeValue("repeat") != null) 
				fieldchilds.addAll(block.getChildren("field"));
		
		
		for(Element field : fieldchilds)
			if(!EventGenerator.existType(field.getAttributeValue("type"))) {
				Iterator<Map<String, List<Trio<String, String, String>>>> iRestrictions = eplRestrictions.iterator();
				while(iRestrictions.hasNext())
				{
					Map<String, List<Trio<String, String, String>>> restrictions = iRestrictions.next();
					restrictions.remove(field.getAttributeValue("name"));
					
					if(restrictions.isEmpty())
						iRestrictions.remove();
				}
			}

		int eventsPerIteration = totalNumEvents / (eplRestrictions.size() + 1);
		int aux = eventsPerIteration;

		for (int i = 0; i < eplRestrictions.size(); i++) {
			iterationValues.add(eventsPerIteration);
			eventsPerIteration = eventsPerIteration + aux;
		}
	}

	public List<Integer> getIterationValues() {
		return iterationValues;
	}

	public List<Map<String, List<Trio<String, String, String>>>> getEplRestrictions() {
		return eplRestrictions;
	}
	
	
}
