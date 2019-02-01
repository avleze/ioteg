package com.ioteg.exprlang;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Lexer {

	private Scanner inputReader;
	private Character lastChar;
	private StringBuilder currentMatch;
	private Token currentToken;
	
	protected static final Map<String, Token> singleCharacterTokens;

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

	public Token getNextToken() {

		while (Character.isWhitespace(lastChar)) {
			lastChar = getNextCharacter();
		}

		if (Character.isAlphabetic(lastChar)) {
			currentMatch = new StringBuilder(lastChar.toString());

			while (isalnum(lastChar = getNextCharacter())) {
				currentMatch.append(lastChar);
			}
			
			currentToken = Token.TOK_ID;
			return currentToken;
		}

		if (Character.isDigit(lastChar)) {
			currentMatch = new StringBuilder(lastChar.toString());

			while (Character.isDigit(lastChar = getNextCharacter())) {
				currentMatch.append(lastChar);
			}
			
			if(lastChar == '.')
			{
				currentMatch.append(lastChar);
			
				while (Character.isDigit(lastChar = getNextCharacter())) {
					currentMatch.append(lastChar);
				}
			}
			
			currentToken = Token.TOK_NUMBER;
			return currentToken;
		}

		Token associatedToken = singleCharacterTokens.get(Character.toString(lastChar));

		if (associatedToken != null) {
			currentMatch = new StringBuilder(lastChar.toString());
			lastChar = getNextCharacter();
			currentToken = associatedToken;
			return associatedToken;
		}

		if (lastChar == '\0')
		{
			currentToken = Token.TOK_EOF;
			return currentToken;
		}
			

		currentMatch = new StringBuilder(lastChar.toString());
		lastChar = getNextCharacter();
		currentToken = Token.UNKOWN;
		return currentToken;

	}

	private boolean isalnum(int ch) {
		return Character.isAlphabetic(ch) || Character.isDigit(ch);
	}

	public String getCurrentMatch() {
		return currentMatch.toString();
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
