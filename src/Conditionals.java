import java.util.*;

public class Conditionals {

	public static List<Boolean> conds = new ArrayList<Boolean>();

	public boolean evaluateConditional(String conditional) {
		conds.clear();
		String next = "AND";
		Scanner inLine = new Scanner(conditional);
		Variables variables = new Variables();
		ErrorSystem errorSystem = new ErrorSystem();

		while (inLine.hasNext()) {
			String value1 = inLine.next();
			String condition = inLine.next();
			String value2 = inLine.next();
			if (variables.getVariableType(value1) == Line.TokenType.TEXT) {
				if (variables.getVariableType(value2) == Line.TokenType.TEXT) {
					if (condition.equals("=")) {
						if (value1.equals(value2)) {
							conds.add(true);
						} else {
							conds.add(false);
						}
					} else if (condition.equals("!=")) {
						if (!value1.equals(value2)) {
							conds.add(true);
						} else {
							conds.add(false);
						}
					} else {
						errorSystem.throwError("Condition " + condition + " cannot be used to compare texts");
					}
				} else {
					errorSystem.throwError("A text variable must be compared with another text variable");
				}
			} else {
				double value1d = 0;
				double value2d = 0;
				if (variables.getVariableType(value1) == Line.TokenType.NUM) {
					value1d = variables.getNumVariable(value1);
				} else {
					try {
						value1d = Double.parseDouble(value1);
					} catch (NumberFormatException nfe) {
						errorSystem.throwError("Only texts and nums can be used in conditionals");
					}
				}

				if (variables.getVariableType(value2) == Line.TokenType.NUM) {
					value2d = variables.getNumVariable(value2);
				} else {
					try {
						value2d = Double.parseDouble(value2);
					} catch (NumberFormatException nfe) {
						errorSystem.throwError("Only texts and nums can be used in conditionals");
					}
				}

				switch (condition) {
					case "=": conds.add(value1d == value2d); break;
					case "!=": conds.add(value1d != value2d); break;
					case ">": conds.add(value1d > value2d); break;
					case "<": conds.add(value1d < value2d); break;
					case ">=": conds.add(value1d >= value2d); break;
					case "<=": conds.add(value1d <= value2d); break;
					default: errorSystem.throwError("Condition " + condition + " cannot be used to compare nums");
				}

			}

			if (inLine.hasNext()) {
				next = inLine.next();
			}
		}
		
		if (next.equals("AND")) {
			boolean allTrue = true;
			for (boolean tf : conds) {
				if (!tf) {
					allTrue = false;
				}
			}
			return allTrue;
		} else if (next.equals("OR")) {
			boolean oneTrue = false;
			for (boolean tf : conds) {
				if (tf) {
					oneTrue = true;
				}
			}
			return oneTrue;
		} else {
			errorSystem.throwError("Condition " + next + " cannot be used to compare variables");
			return false;
		}
	}
}