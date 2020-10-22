import java.util.*;

public class Lexer {

	public static int lineNumber = 0;
	public static boolean inIfStatement = false, runCode = true;

	public void Tokenize(List<String> lines) {
		ErrorSystem errorSystem = new ErrorSystem();
		int numberOfLines = lines.size();
		Write write = new Write();
		for (int i = 0; i < lines.size(); i++) {
			String line = lines.get(i);

			lineNumber++;
			Variables variables = new Variables();

			Scanner inLine = new Scanner(line);

			String keyword = inLine.next();

			Line currentLine = new Line();
			Line.TokenType type = currentLine.getType(keyword);

			String restOfLine = "";

			while (inLine.hasNext()) {
				restOfLine += inLine.next();
				if (inLine.hasNext()) {
					restOfLine += " ";
				}
			}
			//System.out.print(i+1 + " " + lineShouldRun() + ": ");

			if (keyword.equals("*")) {
				continue;
			} else if (variables.getVariableType(keyword.replace(":", "")) == type.NUM) {
				if (lineShouldRun()) {
					currentLine.setKeyword(Line.TokenType.NUM);
					parseVariable("NUM", line);
				}
			} else if (variables.getVariableType(keyword.replace(":", "")) == type.TEXT) {
				if (lineShouldRun()) {
					currentLine.setKeyword(Line.TokenType.TEXT);
					parseVariable("TEXT", line);
				}
			} else {
				if (type == Line.TokenType.ENDIF) {
					inIfStatement = false;
					runCode = true;
				} else if (lineShouldRun()) {
					switch (type) {
					case WRITE:
						write.print(restOfLine);
						break;
					case NUM:
						parseVariable("NUM", restOfLine);
						break;
					case TEXT:
						parseVariable("TEXT", restOfLine);
						break;
					case INPUT:
						parseInput(restOfLine);
						break;
					case IF:
						parseIf(restOfLine);
						continue;
					default:
						errorSystem.throwError("Invalid keyword");
					}
				}
			}
		}
	}

	public Boolean lineShouldRun() {
		if (runCode) {
			return true;
		} else {
			return false;
		}
	}

	public void parseIf(String restOfLine) {
		runCode = true;
		ErrorSystem errorSystem = new ErrorSystem();
		Conditionals conditionals = new Conditionals();
		if (restOfLine.charAt(restOfLine.length() - 1) == ':') {
			restOfLine = restOfLine.replace(":", "");
			runCode = conditionals.evaluateConditional(restOfLine);
			inIfStatement = true;
		} else {
			errorSystem.throwError("If statement not formatted correctly");
		}
	}

	public void parseInput(String restOfLine) {
		Input input = new Input();
		Scanner inLine = new Scanner(restOfLine);
		if (inLine.hasNext()) {
			String as = inLine.next();
			String type = inLine.next();
			String key = inLine.next();
			if (type.charAt(type.length() - 1) == ':' && as.equals("as")) {
				type = type.replace(":", "");
				input.takeInput(type, key);
			}
		}
	}

	public void parseVariable(String type, String restOfLine) {
		ErrorSystem errorSystem = new ErrorSystem();
		Variables variables = new Variables();
		Scanner inLine = new Scanner(restOfLine);
		String key = inLine.next();

		Line currentLine = new Line();
		Line.TokenType tokenType = Line.TokenType.INVALID;

		if (type.equals("NUM") && key.charAt(key.length() - 1) == ':') {
			key = key.replace(":", "");
			double value = 0;
			String next = inLine.next();
			if (next.equals("RANDOM")) {
				String random = inLine.nextLine();
				random = random.replaceAll(" ", "");
				if (random.charAt(0) == '[' && random.charAt(random.length()-1) == ']') {
					random = random.replace("[","");
					random = random.replace("]", "");
					String[] values = random.split(",");
					double minValue = 0, maxValue = 0;
					try {
						if (variables.getVariableType(values[0]) == tokenType.NUM) {
							minValue = variables.getNumVariable(values[0]);
						} else {
							try {
								minValue = Double.parseDouble(values[0]);
							} catch (NumberFormatException nfe) {
								errorSystem.throwError("A num variable must be made of nums");
							}
						}

						if (variables.getVariableType(values[1]) == tokenType.NUM) {
							maxValue = variables.getNumVariable(values[1]);
						} else {
							try {
								maxValue = Double.parseDouble(values[1]);
							} catch (NumberFormatException nfe) {
								errorSystem.throwError("A num variable must be made of nums");
							}
						}

						variables.setRandomNumVariable(key, minValue, maxValue);
					} catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
						errorSystem.throwError("RANDOM statements must be followed by a min and max value in brackets");
					}
				} else {
					errorSystem.throwError("RANDOM statements must be followed by a min and max value in brackets");
				}
			} else if (variables.getVariableType(next) == tokenType.NUM) {
				value = variables.getNumVariable(next);
			} else {
				try {
					value = Double.parseDouble(next);
				} catch (NumberFormatException nfe) {
					errorSystem.throwError("A num variable must be made of nums");
				}
			}
			double previousValue = value;
			double currentValue = value;
			while (inLine.hasNext()) {
				String operator = inLine.next();
				next = inLine.next();
				if (variables.getVariableType(next) == tokenType.NUM) {
					currentValue = variables.getNumVariable(next);
				} else {
					try {
						currentValue = Double.parseDouble(next);
					} catch (NumberFormatException nfe) {
						errorSystem.throwError("A num variable must be made of nums");
					}
				}
				switch (operator) {
				case "+":
					currentValue += previousValue;
					break;
				case "-":
					currentValue = previousValue - currentValue;
					break;
				case "*":
					currentValue *= previousValue;
					break;
				case "/":
					currentValue = previousValue / currentValue;
					break;
				case "%":
					currentValue = previousValue % currentValue;
					break;
				default:
					errorSystem.throwError("Invalid operator " + operator);
				}
				previousValue = currentValue;
			}

			variables.setNumVariable(key, currentValue);

		} else if (type.equals("TEXT") && key.charAt(key.length() - 1) == ':') {
			key = key.replace(":", "");
			String value = "";
			while (inLine.hasNext()) {
				value += inLine.next();
				if (inLine.hasNext()) {
					value += " ";
				}
			}
			variables.setTextVariable(key, value);
		}
	}

}