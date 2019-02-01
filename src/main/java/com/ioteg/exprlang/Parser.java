package com.ioteg.exprlang;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import com.ioteg.exprlang.ast.BinaryExpressionAST;
import com.ioteg.exprlang.ast.CallExpressionAST;
import com.ioteg.exprlang.ast.ExpressionAST;
import com.ioteg.exprlang.ast.NumberExpressionAST;
import com.ioteg.exprlang.ast.UnaryExpressionAST;
import com.ioteg.exprlang.ast.VariableExpressionAST;

public class Parser {
	private Lexer lexer;
	public static Map<Token, Integer> tokenPrecedence;

	static {
		tokenPrecedence = new HashMap<>();
		tokenPrecedence.put(Token.TOK_OP_SUM, 20);
		tokenPrecedence.put(Token.TOK_OP_SUB, 20);
		tokenPrecedence.put(Token.TOK_OP_MUL, 40);
		tokenPrecedence.put(Token.TOK_OP_DIV, 40);
	}

	public Parser(String str) {
		this.lexer = new Lexer(str);
	}

	public ExpressionAST parse() throws IOException {
		lexer.getNextToken();
		return parseExpression();
	}

	private ExpressionAST parseExpression() throws IOException {
		ExpressionAST lhs = parsePrimary();

		if (lhs == null)
			return null;

		return parseBinaryExpressionRHS(0, lhs);
	}

	private ExpressionAST parseBinaryExpressionRHS(int expressionPrecedence, ExpressionAST lhs) throws IOException {
		while (true) {
			int tokenPrecedence = getTokenPrecedence();

			if (tokenPrecedence < expressionPrecedence)
				return lhs;

			Token binOp = lexer.getCurrentToken();
			lexer.getNextToken();
			ExpressionAST rhs = parsePrimary();
			if (rhs == null)
				return null;

			// If BinOp binds less tightly with RHS than the operator after RHS, let
			// the pending operator take RHS as its LHS.
			int nextPrecedence = getTokenPrecedence();
			if (tokenPrecedence < nextPrecedence) {
				rhs = parseBinaryExpressionRHS(tokenPrecedence + 1, rhs);
				if (rhs == null)
					return null;
			}

			// Merge LHS/RHS.
			lhs = new BinaryExpressionAST(binOp, lhs, rhs);
		}
	}

	private int getTokenPrecedence() {
		Integer precedence = tokenPrecedence.get(lexer.getCurrentToken());
		if (precedence == null)
			return -1;
		else
			return precedence;
	}

	private ExpressionAST parsePrimary() throws IOException {
		ExpressionAST exp = null;

		switch (lexer.getCurrentToken()) {
		case TOK_DOLLAR:
			exp = parseVariableExpression();
			break;
		case TOK_OPEN_PAREN:
			exp = parseParenthesisExpression();
			break;
		case TOK_NUMBER:
			exp = parseNumberExpression();
			break;
		case TOK_ID:
			exp = parseCallFunctionExpression();
			break;
		case TOK_OP_SUB:
		case TOK_OP_SUM:
			exp = parseUnaryExpression();
			break;
		default:
			System.err.println("Expected a expression.");
			break;
		}

		return exp;
	}

	private ExpressionAST parseUnaryExpression() throws IOException {
		Token operator = lexer.getCurrentToken();
		lexer.getNextToken();
		ExpressionAST primary = parsePrimary();
		if(primary == null)
			return null;
		
		if(operator == Token.TOK_OP_SUM)
			return primary;
		return new UnaryExpressionAST(primary, operator);
	}

	private ExpressionAST parseCallFunctionExpression() throws IOException {
		String idName = lexer.getCurrentMatch();

		if (lexer.getNextToken() != Token.TOK_OPEN_PAREN) {
			System.err.println("The function " + idName + " needs a list of zero or more arguments. Expected (.");
			return null;
		}
		lexer.getNextToken();
		Vector<ExpressionAST> args = new Vector<>();

		if (lexer.getCurrentToken() != Token.TOK_CLOSED_PAREN) {

			while (true) {
				ExpressionAST arg = parseExpression();
				if (arg != null)
					args.add(arg);
				else
					return null;

				if (lexer.getCurrentToken() == Token.TOK_CLOSED_PAREN)
					break;
				if (lexer.getCurrentToken() != Token.TOK_COMMA)
					System.err.println("Unexpected input in the arguments list of the function " + idName + ".");

				lexer.getNextToken();
			}

		}

		lexer.getNextToken();
		return new CallExpressionAST(idName, args);
	}

	private ExpressionAST parseVariableExpression() throws IOException {

		if (lexer.getNextToken() != Token.TOK_OPEN_PAREN) {
			System.err.println("Expected (.");
			return null;
		}

		if (lexer.getNextToken() != Token.TOK_ID) {
			System.err.println("Expected IDENTIFIER.");
			return null;
		}

		String idName = lexer.getCurrentMatch();

		if (lexer.getNextToken() != Token.TOK_CLOSED_PAREN)
			System.err.println("Expected ).");
		else
			lexer.getNextToken();

		return new VariableExpressionAST(idName);
	}

	private ExpressionAST parseNumberExpression() throws IOException {

		ExpressionAST exp = new NumberExpressionAST(Double.valueOf(lexer.getCurrentMatch()));
		lexer.getNextToken();
		return exp;
	}

	private ExpressionAST parseParenthesisExpression() throws IOException {
		ExpressionAST exp = null;
		lexer.getNextToken();
		exp = parseExpression();

		if (exp == null)
			return null;

		if (lexer.getCurrentToken() != Token.TOK_CLOSED_PAREN)
			System.err.println("Expected ). But found " + lexer.getCurrentToken());
		lexer.getNextToken();
		return exp;
	}
}
