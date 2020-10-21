import java.util.*;

public class Input {
	public void takeInput(String type, String key) {
		Scanner keyboard = new Scanner(System.in);
		ErrorSystem errorSystem = new ErrorSystem();
		Variables variables = new Variables();
		if (key.matches("^[a-zA-Z_$][a-zA-Z_$0-9]*$")) {
			if (type.equals("num")) {
				String textInput = keyboard.next();
				variables.setNumVariable(key, Double.parseDouble(textInput));
			} else if (type.equals("text")) {
				String textInput = keyboard.nextLine();
				variables.setTextVariable(key, textInput);
			} else {
				errorSystem.throwError("Invalid keyword (" + type + ")");
			}
		} else {
			errorSystem.throwError("Invalid variable name (" + key + ")");
		}
	}
}