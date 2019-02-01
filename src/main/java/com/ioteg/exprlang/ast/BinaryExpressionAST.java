package com.ioteg.exprlang.ast;

import java.util.Map;

import com.ioteg.exprlang.Token;

public class BinaryExpressionAST implements ExpressionAST {
	private Token operator;
	private ExpressionAST lhs;
	private ExpressionAST rhs;

	public BinaryExpressionAST(Token operator, ExpressionAST lhs, ExpressionAST rhs) {
		super();
		this.operator = operator;
		this.lhs = lhs;
		this.rhs = rhs;
	}

	public Token getOperator() {
		return operator;
	}

	public ExpressionAST getLhs() {
		return lhs;
	}

	public ExpressionAST getRhs() {
		return rhs;
	}

	@Override
	public Double evaluate(Map<String, Double> symbols) {
		Double result = null;

		switch (operator) {
		case TOK_OP_DIV:
			result = lhs.evaluate(symbols) / rhs.evaluate(symbols);
			break;
		case TOK_OP_MUL:
			result = lhs.evaluate(symbols) * rhs.evaluate(symbols);
			break;
		case TOK_OP_SUB:
			result = lhs.evaluate(symbols) - rhs.evaluate(symbols);
			break;
		case TOK_OP_SUM:
			result = lhs.evaluate(symbols) + rhs.evaluate(symbols);
			break;
		default:
			break;
		}
		
		return result;
	}
}
