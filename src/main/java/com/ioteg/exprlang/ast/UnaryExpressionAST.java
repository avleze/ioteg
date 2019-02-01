package com.ioteg.exprlang.ast;

import java.util.Map;

import com.ioteg.exprlang.Token;

public class UnaryExpressionAST implements ExpressionAST {
	private ExpressionAST expression;
	private Token unaryOp;
	
	public UnaryExpressionAST(ExpressionAST expr, Token unaryOp) {
		super();
		this.expression = expr;
		this.unaryOp = unaryOp;
	}

	@Override
	public Double evaluate(Map<String, Double> symbols) {
		Double result = null;

		if(unaryOp == Token.TOK_OP_SUB)
			result = -expression.evaluate(symbols);
		
		return result;
	}

	public ExpressionAST getExpression() {
		return expression;
	}

	public Token getUnaryOp() {
		return unaryOp;
	}
	
}
