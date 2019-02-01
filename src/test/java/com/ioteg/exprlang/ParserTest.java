package com.ioteg.exprlang;

import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

import com.ioteg.exprlang.ast.BinaryExpressionAST;
import com.ioteg.exprlang.ast.CallExpressionAST;
import com.ioteg.exprlang.ast.ExpressionAST;
import com.ioteg.exprlang.ast.NumberExpressionAST;
import com.ioteg.exprlang.ast.UnaryExpressionAST;
import com.ioteg.exprlang.ast.VariableExpressionAST;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static org.hamcrest.Matchers.instanceOf;

public class ParserTest {
	@Test
	public void testBinaryExpressions() throws IOException {
		Parser parser = new Parser("2+4");
		ExpressionAST exp = parser.parse();
		assertThat(exp, instanceOf(BinaryExpressionAST.class));
		BinaryExpressionAST bExp = (BinaryExpressionAST) exp;
		
		assertThat(bExp.getOperator(), equalTo(Token.TOK_OP_SUM));
		assertThat(bExp.getLhs(), instanceOf(NumberExpressionAST.class));
		NumberExpressionAST nExp = (NumberExpressionAST) bExp.getLhs();
		assertThat(nExp.getValue(), equalTo(2.0));

		assertThat(bExp.getRhs(), instanceOf(NumberExpressionAST.class));
		nExp = (NumberExpressionAST) bExp.getRhs();
		assertThat(nExp.getValue(), equalTo(4.0));
		
		parser = new Parser("24.5*12.7");
		exp = parser.parse();
		assertThat(exp, instanceOf(BinaryExpressionAST.class));
		bExp = (BinaryExpressionAST) exp;
		
		assertThat(bExp.getOperator(), equalTo(Token.TOK_OP_MUL));
		assertThat(bExp.getLhs(), instanceOf(NumberExpressionAST.class));
		nExp = (NumberExpressionAST) bExp.getLhs();
		assertThat(nExp.getValue(), equalTo(24.5));

		assertThat(bExp.getRhs(), instanceOf(NumberExpressionAST.class));
		nExp = (NumberExpressionAST) bExp.getRhs();
		assertThat(nExp.getValue(), equalTo(12.7));
		
		parser = new Parser("24.5/12");
		exp = parser.parse();
		assertThat(exp, instanceOf(BinaryExpressionAST.class));
		bExp = (BinaryExpressionAST) exp;
		
		assertThat(bExp.getOperator(), equalTo(Token.TOK_OP_DIV));
		assertThat(bExp.getLhs(), instanceOf(NumberExpressionAST.class));
		nExp = (NumberExpressionAST) bExp.getLhs();
		assertThat(nExp.getValue(), equalTo(24.5));

		assertThat(bExp.getRhs(), instanceOf(NumberExpressionAST.class));
		nExp = (NumberExpressionAST) bExp.getRhs();
		assertThat(nExp.getValue(), equalTo(12.0));
		
		parser = new Parser("24.5-5");
		exp = parser.parse();
		assertThat(exp, instanceOf(BinaryExpressionAST.class));
		bExp = (BinaryExpressionAST) exp;
		
		assertThat(bExp.getOperator(), equalTo(Token.TOK_OP_SUB));
		assertThat(bExp.getLhs(), instanceOf(NumberExpressionAST.class));
		nExp = (NumberExpressionAST) bExp.getLhs();
		assertThat(nExp.getValue(), equalTo(24.5));

		assertThat(bExp.getRhs(), instanceOf(NumberExpressionAST.class));
		nExp = (NumberExpressionAST) bExp.getRhs();
		assertThat(nExp.getValue(), equalTo(5.0));
		
		parser = new Parser("24.5-5*4");
		exp = parser.parse();
		assertThat(exp, instanceOf(BinaryExpressionAST.class));
		bExp = (BinaryExpressionAST) exp;
		
		assertThat(bExp.getOperator(), equalTo(Token.TOK_OP_SUB));
		assertThat(bExp.getLhs(), instanceOf(NumberExpressionAST.class));
		nExp = (NumberExpressionAST) bExp.getLhs();
		assertThat(nExp.getValue(), equalTo(24.5));

		assertThat(bExp.getRhs(), instanceOf(BinaryExpressionAST.class));
		bExp = (BinaryExpressionAST) bExp.getRhs();
		
		assertThat(bExp.getLhs(), instanceOf(NumberExpressionAST.class));
		nExp = (NumberExpressionAST) bExp.getLhs();
		assertThat(nExp.getValue(), equalTo(5.0));
		
		assertThat(bExp.getRhs(), instanceOf(NumberExpressionAST.class));
		nExp = (NumberExpressionAST) bExp.getRhs();
		assertThat(nExp.getValue(), equalTo(4.0));
	}
	
	@Test
	public void testBinaryExpressionsWithError() throws IOException {
		Parser parser = new Parser("24.5-5*$(");
		ExpressionAST exp = parser.parse();
		assertThat(exp, equalTo(null));
	}
	
	@Test
	public void testCallFunction() throws IOException {
		Parser parser = new Parser("2+sqrt(4)");
		ExpressionAST exp = parser.parse();
		assertThat(exp, instanceOf(BinaryExpressionAST.class));
		BinaryExpressionAST bExp = (BinaryExpressionAST) exp;
		
		assertThat(bExp.getOperator(), equalTo(Token.TOK_OP_SUM));
		assertThat(bExp.getLhs(), instanceOf(NumberExpressionAST.class));
		NumberExpressionAST nExp = (NumberExpressionAST) bExp.getLhs();
		assertThat(nExp.getValue(), equalTo(2.0));

		assertThat(bExp.getRhs(), instanceOf(CallExpressionAST.class));
		CallExpressionAST cExp = (CallExpressionAST) bExp.getRhs();
		assertThat(cExp.getFnName(), equalTo("sqrt"));
		assertThat(cExp.getArgs().size(), equalTo(1));
		assertThat(cExp.getArgs().get(0), instanceOf(NumberExpressionAST.class));
		
		nExp = (NumberExpressionAST) cExp.getArgs().get(0);
		assertThat(nExp.getValue(), equalTo(4.0));
		
		parser = new Parser("2 + pow(2, 4)");
		exp = parser.parse();
		assertThat(exp, instanceOf(BinaryExpressionAST.class));
		bExp = (BinaryExpressionAST) exp;
		
		assertThat(bExp.getOperator(), equalTo(Token.TOK_OP_SUM));
		assertThat(bExp.getLhs(), instanceOf(NumberExpressionAST.class));
		nExp = (NumberExpressionAST) bExp.getLhs();
		assertThat(nExp.getValue(), equalTo(2.0));

		assertThat(bExp.getRhs(), instanceOf(CallExpressionAST.class));
		cExp = (CallExpressionAST) bExp.getRhs();
		assertThat(cExp.getFnName(), equalTo("pow"));
		assertThat(cExp.getArgs().size(), equalTo(2));
		
		assertThat(cExp.getArgs().get(0), instanceOf(NumberExpressionAST.class));
		nExp = (NumberExpressionAST) cExp.getArgs().get(0);
		assertThat(nExp.getValue(), equalTo(2.0));
		
		assertThat(cExp.getArgs().get(1), instanceOf(NumberExpressionAST.class));
		nExp = (NumberExpressionAST) cExp.getArgs().get(1);
		assertThat(nExp.getValue(), equalTo(4.0));
	}
	
	@Test
	public void testVariableExpression() throws IOException {
		Parser parser = new Parser("$(hola)*sqrt(4)");
		ExpressionAST exp = parser.parse();
		assertThat(exp, instanceOf(BinaryExpressionAST.class));
		BinaryExpressionAST bExp = (BinaryExpressionAST) exp;
		
		assertThat(bExp.getOperator(), equalTo(Token.TOK_OP_MUL));
		assertThat(bExp.getLhs(), instanceOf(VariableExpressionAST.class));
		VariableExpressionAST vExp = (VariableExpressionAST) bExp.getLhs();
		assertThat(vExp.getName(), equalTo("hola"));

		assertThat(bExp.getRhs(), instanceOf(CallExpressionAST.class));
		CallExpressionAST cExp = (CallExpressionAST) bExp.getRhs();
		assertThat(cExp.getFnName(), equalTo("sqrt"));
		assertThat(cExp.getArgs().size(), equalTo(1));
		assertThat(cExp.getArgs().get(0), instanceOf(NumberExpressionAST.class));
		
		NumberExpressionAST nExp = (NumberExpressionAST) cExp.getArgs().get(0);
		assertThat(nExp.getValue(), equalTo(4.0));
	}
	
	@Test
	public void testVariableExpressionWithErr() throws IOException {
		Parser parser = new Parser("$hola)");
		ExpressionAST exp = parser.parse();
		assertThat(exp, equalTo(null));
		
		parser = new Parser("$()");
		exp = parser.parse();
		assertThat(exp, equalTo(null));
		
		parser = new Parser("$(hola+4");
		exp = parser.parse();
		assertThat(exp, instanceOf(BinaryExpressionAST.class));
		BinaryExpressionAST bExp = (BinaryExpressionAST) exp;
		
		assertThat(bExp.getOperator(), equalTo(Token.TOK_OP_SUM));
		assertThat(bExp.getLhs(), instanceOf(VariableExpressionAST.class));
		VariableExpressionAST vExp = (VariableExpressionAST) bExp.getLhs();
		assertThat(vExp.getName(), equalTo("hola"));

		assertThat(bExp.getRhs(), instanceOf(NumberExpressionAST.class));
		NumberExpressionAST nExp = (NumberExpressionAST) bExp.getRhs();
		assertThat(nExp.getValue(), equalTo(4.0));
	}
	
	@Test
	public void testParenthesisExpression() throws IOException {
		Parser parser = new Parser("($(hola)*sqrt(4))");
		ExpressionAST exp = parser.parse();
		assertThat(exp, instanceOf(BinaryExpressionAST.class));
		BinaryExpressionAST bExp = (BinaryExpressionAST) exp;
		
		assertThat(bExp.getOperator(), equalTo(Token.TOK_OP_MUL));
		assertThat(bExp.getLhs(), instanceOf(VariableExpressionAST.class));
		VariableExpressionAST vExp = (VariableExpressionAST) bExp.getLhs();
		assertThat(vExp.getName(), equalTo("hola"));

		assertThat(bExp.getRhs(), instanceOf(CallExpressionAST.class));
		CallExpressionAST cExp = (CallExpressionAST) bExp.getRhs();
		assertThat(cExp.getFnName(), equalTo("sqrt"));
		assertThat(cExp.getArgs().size(), equalTo(1));
		assertThat(cExp.getArgs().get(0), instanceOf(NumberExpressionAST.class));
		
		NumberExpressionAST nExp = (NumberExpressionAST) cExp.getArgs().get(0);
		assertThat(nExp.getValue(), equalTo(4.0));
	}
	
	@Test
	public void testParenthesisExpressionWhithoutClosing() throws IOException {
		Parser parser = new Parser("($(hola)*sqrt(4)");
		ExpressionAST exp = parser.parse();
		assertThat(exp, instanceOf(BinaryExpressionAST.class));
		BinaryExpressionAST bExp = (BinaryExpressionAST) exp;
		
		assertThat(bExp.getOperator(), equalTo(Token.TOK_OP_MUL));
		assertThat(bExp.getLhs(), instanceOf(VariableExpressionAST.class));
		VariableExpressionAST vExp = (VariableExpressionAST) bExp.getLhs();
		assertThat(vExp.getName(), equalTo("hola"));

		assertThat(bExp.getRhs(), instanceOf(CallExpressionAST.class));
		CallExpressionAST cExp = (CallExpressionAST) bExp.getRhs();
		assertThat(cExp.getFnName(), equalTo("sqrt"));
		assertThat(cExp.getArgs().size(), equalTo(1));
		assertThat(cExp.getArgs().get(0), instanceOf(NumberExpressionAST.class));
		
		NumberExpressionAST nExp = (NumberExpressionAST) cExp.getArgs().get(0);
		assertThat(nExp.getValue(), equalTo(4.0));
	}
	
	@Test
	public void testUnaryExpression() throws IOException {
		Parser parser = new Parser("($(hola)*-sqrt(-4))");
		ExpressionAST exp = parser.parse();
		assertThat(exp, instanceOf(BinaryExpressionAST.class));
		BinaryExpressionAST bExp = (BinaryExpressionAST) exp;
		
		assertThat(bExp.getOperator(), equalTo(Token.TOK_OP_MUL));
		assertThat(bExp.getLhs(), instanceOf(VariableExpressionAST.class));
		VariableExpressionAST vExp = (VariableExpressionAST) bExp.getLhs();
		assertThat(vExp.getName(), equalTo("hola"));
		assertThat(bExp.getRhs(), instanceOf(UnaryExpressionAST.class));
		UnaryExpressionAST uExp = (UnaryExpressionAST) bExp.getRhs();
		
		assertThat(uExp.getExpression(), instanceOf(CallExpressionAST.class));
		CallExpressionAST cExp = (CallExpressionAST) uExp.getExpression();
		assertThat(cExp.getFnName(), equalTo("sqrt"));
		assertThat(cExp.getArgs().size(), equalTo(1));
		assertThat(cExp.getArgs().get(0), instanceOf(UnaryExpressionAST.class));
		
		uExp = (UnaryExpressionAST) cExp.getArgs().get(0);
		assertThat(uExp.getUnaryOp(), equalTo(Token.TOK_OP_SUB));
		assertThat(uExp.getExpression(), instanceOf(NumberExpressionAST.class));
		
		NumberExpressionAST nExp = (NumberExpressionAST) uExp.getExpression();
		assertThat(nExp.getValue(), equalTo(4.0));
		
		parser = new Parser("+sqrt(4)");
		exp = parser.parse();
		assertThat(exp, instanceOf(CallExpressionAST.class));
		cExp = (CallExpressionAST) exp;
		assertThat(cExp.getFnName(), equalTo("sqrt"));
		assertThat(cExp.getArgs().size(), equalTo(1));
		assertThat(cExp.getArgs().get(0), instanceOf(NumberExpressionAST.class));
		nExp = (NumberExpressionAST) cExp.getArgs().get(0);
		assertThat(nExp.getValue(), equalTo(4.0));
	}
	
	@Test
	public void testUnaryExpressionWithError() throws IOException {
		Parser parser = new Parser("-sqrt((4)");
		ExpressionAST exp = parser.parse();
		assertThat(exp, equalTo(null));
	}
	
	@Test
	public void testCallExpressionWithNoArgumentsList() throws IOException {
		Parser parser = new Parser("(hola)*sqrt(4)");
		ExpressionAST exp = parser.parse();
		assertThat(exp, equalTo(null));
	}
	
	@Test
	public void testCallExpressionWithIncompleteArgumentsList() throws IOException {
		Parser parser = new Parser("(hola(4,))*sqrt(4)");
		ExpressionAST exp = parser.parse();
		assertThat(exp, equalTo(null));
	}
	
	@Test
	public void testCallExpressionWithUnexpectedArgumentsList() throws IOException {
		Parser parser = new Parser("(hola(4~))*sqrt(4)");
		ExpressionAST exp = parser.parse();
		assertThat(exp, equalTo(null));
	}
	
	@Test
	public void testEvalExpression() throws IOException {
		Map<String, Double> symbols = new HashMap<>();

		Parser parser = new Parser("pow(sin($(PI)),2)+pow(cos($(PI)),2)");
		ExpressionAST exp = parser.parse();
		symbols.put("PI", Math.PI);
		assertThat(exp.evaluate(symbols), equalTo(1.0));
		
		parser = new Parser("1-2");
		exp = parser.parse();
		assertThat(exp.evaluate(symbols), equalTo(-1.0));
		
		parser = new Parser("4*2");
		exp = parser.parse();
		assertThat(exp.evaluate(symbols), equalTo(8.0));
		
		parser = new Parser("4/2");
		exp = parser.parse();
		assertThat(exp.evaluate(symbols), equalTo(2.0));
		
		parser = new Parser("-(-200)");
		exp = parser.parse();
		assertThat(exp.evaluate(symbols), equalTo(200.0));
	}
	
	@Test
	public void testEvalFunctions() throws IOException {
		Map<String, Double> symbols = new HashMap<>();
		symbols.put("E", Math.E);
		symbols.put("PI", Math.PI);

		Parser parser = new Parser("pow(3,2)");
		ExpressionAST exp = parser.parse();
		assertThat(exp.evaluate(symbols), equalTo(9.0));
		
		parser = new Parser("sqrt(4)");
		exp = parser.parse();
		assertThat(exp.evaluate(symbols), equalTo(2.0));
		
		parser = new Parser("abs(-2)");
		exp = parser.parse();
		assertThat(exp.evaluate(symbols), equalTo(2.0));
		
		parser = new Parser("max(-2,1)");
		exp = parser.parse();
		assertThat(exp.evaluate(symbols), equalTo(1.0));
		
		parser = new Parser("min(-2,1)");
		exp = parser.parse();
		assertThat(exp.evaluate(symbols), equalTo(-2.0));
		
		parser = new Parser("log10(max(-100,100))");
		exp = parser.parse();
		assertThat(exp.evaluate(symbols), equalTo(2.0));
		
		parser = new Parser("log(pow($(E),-3/2))");
		exp = parser.parse();
		assertThat(exp.evaluate(symbols), equalTo(-1.5));
		
		parser = new Parser("ceil($(E))");
		exp = parser.parse();
		assertThat(exp.evaluate(symbols), equalTo(3.0));
		
		parser = new Parser("floor($(E))");
		exp = parser.parse();
		assertThat(exp.evaluate(symbols), equalTo(2.0));
		
		parser = new Parser("round($(E))");
		exp = parser.parse();
		assertThat(exp.evaluate(symbols), equalTo(3.0));
		
		parser = new Parser("round($(E)-0.3)");
		exp = parser.parse();
		assertThat(exp.evaluate(symbols), equalTo(2.0));
		
		parser = new Parser("tan($(PI))");
		exp = parser.parse();
		assertThat(exp.evaluate(symbols), greaterThanOrEqualTo(-0.0000001));
		assertThat(exp.evaluate(symbols), lessThanOrEqualTo(0.0000001));
		
		parser = new Parser("atan(0)");
		exp = parser.parse();
		assertThat(exp.evaluate(symbols), equalTo(0.0));
		
		parser = new Parser("atan(1)");
		exp = parser.parse();
		assertThat(exp.evaluate(symbols), equalTo(Math.PI/4));
		
		parser = new Parser("asin(1)");
		exp = parser.parse();
		assertThat(exp.evaluate(symbols), equalTo(Math.PI/2));
		
		parser = new Parser("acos(0)");
		exp = parser.parse();
		assertThat(exp.evaluate(symbols), equalTo(Math.PI/2));
	}
}
