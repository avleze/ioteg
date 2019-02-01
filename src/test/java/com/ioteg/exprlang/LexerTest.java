package com.ioteg.exprlang;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.jupiter.api.Test;

public class LexerTest {
	
	@Test
	public void testArithmeticOperators() throws IOException {
		Lexer lex = new Lexer("2+4");
		assertEquals(lex.getNextToken(), Token.TOK_NUMBER);
		assertEquals(lex.getCurrentToken(), Token.TOK_NUMBER);
		assertEquals(lex.getCurrentMatch(), "2");
		assertEquals(lex.getNextToken(), Token.TOK_OP_SUM);
		assertEquals(lex.getCurrentToken(), Token.TOK_OP_SUM);
		assertEquals(lex.getCurrentMatch(), "+");
		assertEquals(lex.getNextToken(), Token.TOK_NUMBER);
		assertEquals(lex.getCurrentToken(), Token.TOK_NUMBER);
		assertEquals(lex.getCurrentMatch(), "4");
		assertEquals(lex.getNextToken(), Token.TOK_EOF);
		
		lex = new Lexer("2*4");
		assertEquals(lex.getNextToken(), Token.TOK_NUMBER);
		assertEquals(lex.getCurrentToken(), Token.TOK_NUMBER);
		assertEquals(lex.getCurrentMatch(), "2");
		assertEquals(lex.getNextToken(), Token.TOK_OP_MUL);
		assertEquals(lex.getCurrentToken(), Token.TOK_OP_MUL);
		assertEquals(lex.getCurrentMatch(), "*");
		assertEquals(lex.getNextToken(), Token.TOK_NUMBER);
		assertEquals(lex.getCurrentToken(), Token.TOK_NUMBER);
		assertEquals(lex.getCurrentMatch(), "4");
		assertEquals(lex.getNextToken(), Token.TOK_EOF);
		
		lex = new Lexer("2-4");
		assertEquals(lex.getNextToken(), Token.TOK_NUMBER);
		assertEquals(lex.getCurrentToken(), Token.TOK_NUMBER);
		assertEquals(lex.getCurrentMatch(), "2");
		assertEquals(lex.getNextToken(), Token.TOK_OP_SUB);
		assertEquals(lex.getCurrentToken(), Token.TOK_OP_SUB);
		assertEquals(lex.getCurrentMatch(), "-");
		assertEquals(lex.getNextToken(), Token.TOK_NUMBER);
		assertEquals(lex.getCurrentToken(), Token.TOK_NUMBER);
		assertEquals(lex.getCurrentMatch(), "4");
		assertEquals(lex.getNextToken(), Token.TOK_EOF);
		
		lex = new Lexer("2/4");
		assertEquals(lex.getNextToken(), Token.TOK_NUMBER);
		assertEquals(lex.getCurrentToken(), Token.TOK_NUMBER);
		assertEquals(lex.getCurrentMatch(), "2");
		assertEquals(lex.getNextToken(), Token.TOK_OP_DIV);
		assertEquals(lex.getCurrentToken(), Token.TOK_OP_DIV);
		assertEquals(lex.getCurrentMatch(), "/");
		assertEquals(lex.getNextToken(), Token.TOK_NUMBER);
		assertEquals(lex.getCurrentToken(), Token.TOK_NUMBER);
		assertEquals(lex.getCurrentMatch(), "4");
		assertEquals(lex.getNextToken(), Token.TOK_EOF);
	}
	
	@Test
	public void testIdentifiers() throws IOException {
		Lexer lex = new Lexer("2+$(hola)");
		assertEquals(lex.getNextToken(), Token.TOK_NUMBER);
		assertEquals(lex.getCurrentToken(), Token.TOK_NUMBER);
		assertEquals(lex.getCurrentMatch(), "2");
		assertEquals(lex.getNextToken(), Token.TOK_OP_SUM);
		assertEquals(lex.getCurrentToken(), Token.TOK_OP_SUM);
		assertEquals(lex.getCurrentMatch(), "+");
		assertEquals(lex.getNextToken(), Token.TOK_DOLLAR);
		assertEquals(lex.getCurrentToken(), Token.TOK_DOLLAR);
		assertEquals(lex.getCurrentMatch(), "$");
		assertEquals(lex.getNextToken(), Token.TOK_OPEN_PAREN);
		assertEquals(lex.getCurrentToken(), Token.TOK_OPEN_PAREN);
		assertEquals(lex.getCurrentMatch(), "(");
		assertEquals(lex.getNextToken(), Token.TOK_ID);
		assertEquals(lex.getCurrentToken(), Token.TOK_ID);
		assertEquals(lex.getCurrentMatch(), "hola");
		assertEquals(lex.getNextToken(), Token.TOK_CLOSED_PAREN);
		assertEquals(lex.getCurrentToken(), Token.TOK_CLOSED_PAREN);
		assertEquals(lex.getCurrentMatch(), ")");
		assertEquals(lex.getNextToken(), Token.TOK_EOF);
		
		lex = new Lexer("sqrt($(hola))");
		assertEquals(lex.getNextToken(), Token.TOK_ID);
		assertEquals(lex.getCurrentToken(), Token.TOK_ID);
		assertEquals(lex.getCurrentMatch(), "sqrt");
		assertEquals(lex.getNextToken(), Token.TOK_OPEN_PAREN);
		assertEquals(lex.getCurrentToken(), Token.TOK_OPEN_PAREN);
		assertEquals(lex.getCurrentMatch(), "(");
		assertEquals(lex.getNextToken(), Token.TOK_DOLLAR);
		assertEquals(lex.getCurrentToken(), Token.TOK_DOLLAR);
		assertEquals(lex.getCurrentMatch(), "$");
		assertEquals(lex.getNextToken(), Token.TOK_OPEN_PAREN);
		assertEquals(lex.getCurrentToken(), Token.TOK_OPEN_PAREN);
		assertEquals(lex.getCurrentMatch(), "(");
		assertEquals(lex.getNextToken(), Token.TOK_ID);
		assertEquals(lex.getCurrentToken(), Token.TOK_ID);
		assertEquals(lex.getCurrentMatch(), "hola");
		assertEquals(lex.getNextToken(), Token.TOK_CLOSED_PAREN);
		assertEquals(lex.getCurrentToken(), Token.TOK_CLOSED_PAREN);
		assertEquals(lex.getCurrentMatch(), ")");
		assertEquals(lex.getNextToken(), Token.TOK_CLOSED_PAREN);
		assertEquals(lex.getCurrentToken(), Token.TOK_CLOSED_PAREN);
		assertEquals(lex.getCurrentMatch(), ")");
		assertEquals(lex.getNextToken(), Token.TOK_EOF);
	}
	
	@Test
	public void testFloatingPointNumbers() throws IOException {
		Lexer lex = new Lexer("20.45+$(hola)");
		assertEquals(lex.getNextToken(), Token.TOK_NUMBER);
		assertEquals(lex.getCurrentToken(), Token.TOK_NUMBER);
		assertEquals(lex.getCurrentMatch(), "20.45");
		assertEquals(lex.getNextToken(), Token.TOK_OP_SUM);
		assertEquals(lex.getCurrentToken(), Token.TOK_OP_SUM);
		assertEquals(lex.getCurrentMatch(), "+");
		assertEquals(lex.getNextToken(), Token.TOK_DOLLAR);
		assertEquals(lex.getCurrentToken(), Token.TOK_DOLLAR);
		assertEquals(lex.getCurrentMatch(), "$");
		assertEquals(lex.getNextToken(), Token.TOK_OPEN_PAREN);
		assertEquals(lex.getCurrentToken(), Token.TOK_OPEN_PAREN);
		assertEquals(lex.getCurrentMatch(), "(");
		assertEquals(lex.getNextToken(), Token.TOK_ID);
		assertEquals(lex.getCurrentToken(), Token.TOK_ID);
		assertEquals(lex.getCurrentMatch(), "hola");
		assertEquals(lex.getNextToken(), Token.TOK_CLOSED_PAREN);
		assertEquals(lex.getCurrentToken(), Token.TOK_CLOSED_PAREN);
		assertEquals(lex.getCurrentMatch(), ")");
		assertEquals(lex.getNextToken(), Token.TOK_EOF);
	}
	
	@Test
	public void testUnkown() throws IOException {
		Lexer lex = new Lexer(".~Unkown");
		assertEquals(lex.getNextToken(), Token.UNKOWN);
		assertEquals(lex.getCurrentToken(), Token.UNKOWN);
		assertEquals(lex.getCurrentMatch(), ".");
		assertEquals(lex.getNextToken(), Token.UNKOWN);
		assertEquals(lex.getCurrentToken(), Token.UNKOWN);
		assertEquals(lex.getCurrentMatch(), "~");
		assertEquals(lex.getNextToken(), Token.TOK_ID);
		assertEquals(lex.getCurrentToken(), Token.TOK_ID);
		assertEquals(lex.getCurrentMatch(), "Unkown");
		assertEquals(lex.getNextToken(), Token.TOK_EOF);
	}
	
}
