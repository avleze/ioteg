package com.ioteg.exprlang.ast;

import java.util.Map;

public interface ExpressionAST {
	
	public abstract Double evaluate(Map<String, Double> symbols);
}
