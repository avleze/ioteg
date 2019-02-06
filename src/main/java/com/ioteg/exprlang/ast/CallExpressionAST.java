package com.ioteg.exprlang.ast;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.log4j.Logger;


public class CallExpressionAST implements ExpressionAST{
	private String fnName;
	private List<ExpressionAST> args;
	
	protected static Random r;
	protected static Logger logger;

	static {
		logger = Logger.getRootLogger();
	}
	
	public CallExpressionAST(String fnName, List<ExpressionAST> args) {
		super();
		this.fnName = fnName;
		this.args = args;
	}

	public String getFnName() {
		return fnName;
	}

	public List<ExpressionAST> getArgs() {
		return args;
	}

	@Override
	public Double evaluate(Map<String, Double> symbols) {
		Double result = null;
		Object resultObject = null;
		
		try {
			if(isUnaryFunction())
				resultObject = callUnaryFunction(symbols);
			else if(isBinaryFunction())
				resultObject = callBinaryFunction(symbols);
		} catch(Exception e) {
			logger.error(e);
		}
		
		if(resultObject instanceof Double)
			result = (Double) resultObject;
		else if(resultObject instanceof Long || resultObject instanceof Integer)
			result = Double.valueOf(resultObject.toString());
		else 
			result = null;
		
		return result; 
	}

	private boolean isBinaryFunction() {
		return args.size() == 2;
	}

	private boolean isUnaryFunction() {
		return args.size() == 1;
	}

	private Object callBinaryFunction(Map<String, Double> symbols)
			throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		Object resultObject;
		Method fn = Math.class.getMethod(fnName, double.class, double.class);
		resultObject = fn.invoke(null, args.get(0).evaluate(symbols), args.get(1).evaluate(symbols));
		return resultObject;
	}

	private Object callUnaryFunction(Map<String, Double> symbols)
			throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		Object resultObject;
		Method fn = Math.class.getMethod(fnName, double.class);
		resultObject = fn.invoke(null, args.get(0).evaluate(symbols));
		return resultObject;
	}
}
