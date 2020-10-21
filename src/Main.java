import java.io.*;
import java.util.*;

class Main {

	public static String file = "main.samc";

	public static List<String> lines = new ArrayList<String>();

  public static void main(String[] args) throws FileNotFoundException {
		Scanner keyboard = new Scanner(System.in);
		System.out.println("\n\u001b[4m\u001b[32;1mSAMCODE V0.1 (Alpha)\u001b[0m");
		System.out.print("Enter file name: ");
		file = keyboard.nextLine() + ".samc";
		System.out.println("--------------------------------");

		Lexer lexer = new Lexer();

    FileReader fr = new FileReader(new File(file));
		Scanner inFile = new Scanner(fr);

		while (inFile.hasNextLine()) {
			lines.add(inFile.nextLine());
		}

		lexer.Tokenize(lines);

		System.exit(0);

  }
}