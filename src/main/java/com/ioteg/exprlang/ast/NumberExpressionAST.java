package com.ioteg.exprlang.ast;

import java.util.Map;

public class NumberExpressionAST extends ExpressionAST{
	private Double value;
	
	public NumberExpressionAST(Double value) {
		this.value = value;
	}

	public Double getValue() {
		return value;
	}

	@Override
	public Double evaluate(Map<String, Double> symbols) {
		return value;
	}
	
}
