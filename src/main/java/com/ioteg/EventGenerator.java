package com.ioteg;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

import org.apache.log4j.Logger;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import com.ioteg.eplutils.EPLConditionsParser;
import com.ioteg.exprlang.ExprParser.ExprLangParsingException;
import com.ioteg.generators.GeneratorsFactory;
import com.ioteg.generators.block.BlockGenerator;
import com.ioteg.generators.exceptions.NotExistingGeneratorException;
import com.ioteg.model.Block;
import com.ioteg.model.EventType;
import com.ioteg.resultmodel.ArrayResultBlock;
import com.ioteg.resultmodel.ResultEvent;

public class EventGenerator {

	protected static final Set<String> types;
	protected static List<Map<String, List<Trio<String, String, String>>>> fieldvalues = new ArrayList<>();
	public static int totalnumevents;
	protected static List<Integer> iterationvalues;
	protected static Random r;
	protected static Logger logger;

	static {
		types = new TreeSet<>();
		types.add("Integer");
		types.add("Float");
		types.add("Long");
		types.add("Boolean");
		types.add("String");
		types.add("Alphanumeric");
		types.add("Date");
		types.add("Time");

		logger = Logger.getRootLogger();
		try {
			r = SecureRandom.getInstanceStrong();
		} catch (NoSuchAlgorithmException e) {
			logger.error(e);
		}
	}

	public static void main(String[] args) throws Exception {

		String xmlEventSchema = args[0];
		String outputFormat = args[1];
		String eplFilePath = args[2];
		String output = "";
		String path = xmlEventSchema.substring(0, xmlEventSchema.lastIndexOf('/') + 1);
		SAXBuilder builder = new SAXBuilder();
		File xmlFile = new File(xmlEventSchema);

		try (FileWriter values = new FileWriter(path + output)) {

			if (!ValidationUtil.validStandart(xmlFile))
				System.exit(1);

			// To build a document from the xml
			Document document = builder.build(xmlFile);

			// To get the root
			Element rootNode = document.getRootElement();

			output = rootNode.getAttributeValue("name") + "." + outputFormat;

			if (!eplFilePath.equals("null"))
				getEPLValues(eplFilePath, rootNode);

			if (outputFormat.equals("json"))
				JsonUtil.jsonFormatValues(values, document);
			if (outputFormat.equals("xml"))
				XmlUtil.xmlFormatValues(values, document);
			if (outputFormat.equals("csv"))
				CsvUtil.csvFormatValues(values, document);

		} catch (IOException io) {
			logger.error(io.getMessage());
		} catch (JDOMException jdomex) {
			logger.error(jdomex.getMessage());
		}
	}

	/**
	 * A function to obtain values from the fields elements at the where clauses of
	 * the EPL queries to assign them to the fields of the events
	 * 
	 * @param eplFilePath contains the EPL queries
	 * @param rootNode    is the root of the event definition
	 * @throws IOException if the file causes problems
	 */
	public static void getEPLValues(String eplFilePath, Element rootNode) throws IOException {
		EPLConditionsParser eplConditionsParser = new EPLConditionsParser(eplFilePath, rootNode);
		fieldvalues = eplConditionsParser.getRestrictions(totalnumevents);
		iterationvalues = eplConditionsParser.getIterationValues();
	}



	/**
	 * Determine if the type is a "basic" type include in the IoT-EG
	 * 
	 * @param type is the String to check
	 * @return true or false
	 */
	public static boolean existType(String type) {

		return types.contains(type);
	}

	public static ResultEvent generateEvent(EventType event)
			throws NotExistingGeneratorException, ExprLangParsingException {

		ResultEvent resultEvent = new ResultEvent(event.getName(), new ArrayList<>());
		for (Block block : event.getBlocks()) {

			ArrayResultBlock resultBlocks = new ArrayResultBlock(new ArrayList<>(), block.getRepetition() != null);
			BlockGenerator bGenerator = GeneratorsFactory.makeBlockGenerator(block);
			resultBlocks.getResultBlocks()
					.addAll(bGenerator.generate(block.getRepetition() == null ? 1 : block.getRepetition()));
			resultEvent.getArrayResultBlocks().add(resultBlocks);
		}

		return resultEvent;
	}

}
