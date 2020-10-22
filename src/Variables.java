import java.util.*;

public class Variables {
	public static Hashtable<String, String> texts = new Hashtable<String, String>();
	public static Hashtable<String, Double> nums = new Hashtable<String, Double>();

	Line.TokenType type;
	
	public Line.TokenType getVariableType(String key) {
		if (texts.containsKey(key) && !nums.containsKey(key)) {
			return type.TEXT;
		} else if (nums.containsKey(key) && !texts.containsKey(key)) {
			return type.NUM;
		} else {
			return type.INVALID;
		}
	}

	public String getTextVariable(String key) {
		return texts.get(key);
	}

	public double getNumVariable(String key) {
		return nums.get(key);
	}

	public void setTextVariable(String key, String value) {
		String toPrint = "";
		char[] c = value.toCharArray();
		for (int i = 0; i < c.length; i++) {
			if (c[i] != '\\' && c[i] != '(') {
				toPrint += c[i];
			} else if (c[i] == '\\') {
				if (c[i + 1] == 'n') {
					toPrint += "\n";
				} else if (c[i + 1] == 't') {
					toPrint += "\t";
				}
				i++;
			} else {
				String varName = "";
				i++;
				while (c[i] != ')') {
					varName += c[i];
					i++;
				}
				Line.TokenType varType = getVariableType(varName);
				if (varType == Line.TokenType.NUM) {
					double num = getNumVariable(varName);
					if (num % 1 == 0) {
						int numint = (int) num;
						toPrint += numint;
					} else {
						toPrint += num;
					}
				} else if (varType == Line.TokenType.TEXT) {
					toPrint += getTextVariable(varName);
				}
			}
		}
		if (key.matches("^[a-zA-Z_$][a-zA-Z_$0-9]*$")) {
			if (texts.contains(key)) {
				texts.replace(key, toPrint);
			} else {
				texts.put(key, toPrint);
			}
		} else {
			ErrorSystem errorSystem = new ErrorSystem();
			errorSystem.throwError("Invalid text name (" + key + ")");
		}

	}

	public void setNumVariable(String key, double value) {
		if (key.matches("^[a-zA-Z_$][a-zA-Z_$0-9]*$")) {
			if (nums.contains(key)) {
				nums.replace(key, value);
			} else {
				nums.put(key, value);
			}
		} else {
			ErrorSystem errorSystem = new ErrorSystem();
			errorSystem.throwError("Invalid num name (" + key + ")");
		}
	}

	public void setRandomNumVariable(String key, double startingValue, double endingValue) {
		double value = Math.random()*endingValue+startingValue;
		if (key.matches("^[a-zA-Z_$][a-zA-Z_$0-9]*$")) {
			if (nums.contains(key)) {
				nums.replace(key, value);
			} else {
				nums.put(key, value);
			}
		} else {
			ErrorSystem errorSystem = new ErrorSystem();
			errorSystem.throwError("Invalid num name (" + key + ")");
		}
	}
}