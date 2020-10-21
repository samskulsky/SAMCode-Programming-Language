import java.util.*;

public class Write {

	public void print(String text) {
		Variables variables = new Variables();
		String toPrint = "";
		char[] c = text.toCharArray();
			for (int i = 0; i < c.length; i++) {
				if (c[i] != '\\' && c[i] != '(') {
					toPrint += c[i];
				} else if (c[i] == '\\') {
					if (c[i+1] == 'n') {
						toPrint += "\n";
					} else if (c[i+1] == 't') {
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
					Line.TokenType varType = variables.getVariableType(varName);
					if (varType == Line.TokenType.NUM) {
						double num = variables.getNumVariable(varName);
						if (num % 1 == 0) {
							int numint = (int)num;
							toPrint += numint;
						} else {
							toPrint += num;
						}
					} else if (varType == Line.TokenType.TEXT) {
						toPrint += variables.getTextVariable(varName);
					}
				}
			}
		System.out.print(toPrint + " ");
	}

}