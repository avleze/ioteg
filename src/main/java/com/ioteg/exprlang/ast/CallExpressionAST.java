package com.ioteg.exprlang.ast;

import java.util.Map;
import java.util.Vector;

public class CallExpressionAST extends ExpressionAST{
	private String fnName;
	private Vector<ExpressionAST> args;
	
	public CallExpressionAST(String fnName, Vector<ExpressionAST> args) {
		super();
		this.fnName = fnName;
		this.args = args;
	}

	public String getFnName() {
		return fnName;
	}

	public Vector<ExpressionAST> getArgs() {
		return args;
	}

	@Override
	public Double evaluate(Map<String, Double> symbols) {
		Double result = null;

		if(fnName.equals("sqrt"))
			result = Math.sqrt(args.get(0).evaluate(symbols));
		if(fnName.equals("abs"))
			result = Math.abs(args.get(0).evaluate(symbols));
		if(fnName.equals("sin"))
			result = Math.sin(args.get(0).evaluate(symbols));
		if(fnName.equals("cos"))
			result = Math.cos(args.get(0).evaluate(symbols));
		if(fnName.equals("tan"))
			result = Math.tan(args.get(0).evaluate(symbols));
		if(fnName.equals("asin"))
			result = Math.asin(args.get(0).evaluate(symbols));
		if(fnName.equals("acos"))
			result = Math.acos(args.get(0).evaluate(symbols));
		if(fnName.equals("atan"))
			result = Math.atan(args.get(0).evaluate(symbols));
		if(fnName.equals("floor"))
			result = Math.floor(args.get(0).evaluate(symbols));
		if(fnName.equals("ceil"))
			result = Math.ceil(args.get(0).evaluate(symbols));
		if(fnName.equals("log"))
			result = Math.log(args.get(0).evaluate(symbols));
		if(fnName.equals("round"))
			result = (double) Math.round(args.get(0).evaluate(symbols));
		if(fnName.equals("log10"))
			result = Math.log10(args.get(0).evaluate(symbols));
		if(fnName.equals("pow"))
			result = Math.pow(args.get(0).evaluate(symbols), args.get(1).evaluate(symbols));	
		if(fnName.equals("max"))
			result = Math.max(args.get(0).evaluate(symbols), args.get(1).evaluate(symbols));	
		if(fnName.equals("min"))
			result = Math.min(args.get(0).evaluate(symbols), args.get(1).evaluate(symbols));	
		
		return result; 
	}
}
