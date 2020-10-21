import java.util.*;

public class Line {
	String input;
	boolean keyword = false;

	public enum TokenType {
		WRITE, NUM, TEXT, BOOLEAN, LEFTP, RIGHTP, COMMA, ADD, SUBTRACT, MULTIPLY, DIVIDE, MOD, NOT, LESS, LESSOREQUAL, GREATER, GREATEROREQUAL, EQUAL, NOTEQUAL, AND, OR, IF, ELSE, CALC, INVALID, INPUT, ENDIF
	}

	private TokenType type = TokenType.INVALID;

	public void setKeyword(TokenType tokentype) {
			type = tokentype;
			keyword = true;
	}

	public TokenType getType(String str) {
		input = str;
		switch (input) {
			case "write": setKeyword(TokenType.WRITE); break;
			case "num": setKeyword(TokenType.NUM); break;
			case "text": setKeyword(TokenType.TEXT); break;
			case "boolean": setKeyword(TokenType.BOOLEAN); break;
			case "if": setKeyword(TokenType.IF); break;
			case "else": setKeyword(TokenType.ELSE); break;
			case "calc": setKeyword(TokenType.CALC); break;
			case "input": setKeyword(TokenType.INPUT); break;
			case "endif": setKeyword(TokenType.ENDIF); break;
		}
		return type;
	}
}