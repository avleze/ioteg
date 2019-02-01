package com.ioteg.exprlang;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Lexer {

	private Scanner inputReader;
	private Character lastChar;
	private String currentMatch;
	private Token currentToken;
	
	public static Map<String, Token> singleCharacterTokens;

	static {
		singleCharacterTokens = new HashMap<>();
		singleCharacterTokens.put("+", Token.TOK_OP_SUM);
		singleCharacterTokens.put("-", Token.TOK_OP_SUB);
		singleCharacterTokens.put("/", Token.TOK_OP_DIV);
		singleCharacterTokens.put("*", Token.TOK_OP_MUL);
		singleCharacterTokens.put("(", Token.TOK_OPEN_PAREN);
		singleCharacterTokens.put(")", Token.TOK_CLOSED_PAREN);
		singleCharacterTokens.put("$", Token.TOK_DOLLAR);
		singleCharacterTokens.put(",", Token.TOK_COMMA);

	}

	public Lexer(String str) {
		this.inputReader = new Scanner(str);
		this.inputReader.useDelimiter("");
		this.lastChar = ' ';
	}

	public Token getNextToken() throws IOException {

		while (Character.isWhitespace(lastChar)) {
			lastChar = getNextCharacter();
		}

		if (Character.isAlphabetic(lastChar)) {
			currentMatch = lastChar.toString();

			while (isalnum(lastChar = getNextCharacter())) {
				currentMatch += lastChar;
			}
			
			currentToken = Token.TOK_ID;
			return currentToken;
		}

		if (Character.isDigit(lastChar)) {
			currentMatch = lastChar.toString();

			while (Character.isDigit(lastChar = getNextCharacter())) {
				currentMatch += lastChar;
			}
			
			if(lastChar == '.')
			{
				currentMatch += lastChar;
			
				while (Character.isDigit(lastChar = getNextCharacter())) {
					currentMatch += lastChar;
				}
			}
			
			currentToken = Token.TOK_NUMBER;
			return currentToken;
		}

		Token associatedToken = singleCharacterTokens.get(Character.toString(lastChar));

		if (associatedToken != null) {
			currentMatch = Character.toString(lastChar);
			lastChar = getNextCharacter();
			currentToken = associatedToken;
			return associatedToken;
		}

		if (lastChar == '\0')
		{
			currentToken = Token.TOK_EOF;
			return currentToken;
		}
			

		currentMatch = Character.toString(lastChar);
		lastChar = getNextCharacter();
		currentToken = Token.UNKOWN;
		return currentToken;

	}

	private boolean isalnum(int ch) {
		return Character.isAlphabetic(ch) || Character.isDigit(ch);
	}

	public String getCurrentMatch() {
		return currentMatch;
	}
	
	public Token getCurrentToken() {
		return currentToken;
	}

	private Character getNextCharacter() {
		if (inputReader.hasNext())
			return Character.valueOf(inputReader.next().charAt(0));
		else
			return Character.valueOf('\0');
	}
}
