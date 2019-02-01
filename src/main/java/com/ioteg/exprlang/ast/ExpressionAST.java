package com.ioteg.exprlang.ast;

import java.util.Map;

public abstract class ExpressionAST {
	
	public abstract Double evaluate(Map<String, Double> symbols);
}
