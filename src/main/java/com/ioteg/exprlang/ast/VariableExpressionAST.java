package com.ioteg.exprlang.ast;

import java.util.Map;

public class VariableExpressionAST implements ExpressionAST{
	private String name;
	
	public VariableExpressionAST(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	@Override
	public Double evaluate(Map<String, Double> symbols) {
		return symbols.get(name);
	}
}
