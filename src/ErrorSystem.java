import java.util.*;

public class ErrorSystem {
	public void throwError(String error) {
		System.out.println("\n\u001b[31mError in file \u001b[1m" + Main.file.toString().toUpperCase() + ":\u001b[0m\u001b[31m");
		System.out.println("\tLINE " + Lexer.lineNumber + ": " + error + "\n");
		System.exit(0);
	}
}