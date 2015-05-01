package lisp;

import java.io.IOException;

public class Main {
	private static boolean toNext;

	public static void main(String[] args) {

		Reader reader = new Reader();
		Lexer lexer = new Lexer();
		Parser parser = new Parser();
		Eval eval = new Eval();

		try {
			do {
				toNext = reader.read(args);
				lexer.lexe(reader.getInputLine());
				parser.parse(lexer.getToken());
				eval.evaluate(parser.getTree());
			} while (toNext);
		} catch (SyntaxException e) {
			System.out.println(e);
		} catch (IOException e) {
			System.out.println(e + "(Input is invalid.)");
		} catch (ComandLineArgumentException e) {
			System.out.println(e);
		} catch (FileReadException e) {
			System.out.println(e);
		}
	}
}