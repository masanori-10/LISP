package lisp;

import java.io.IOException;

public class Main {
	public static void main(String[] args) {

		Reader reader = new Reader();
		Lexer lexer = new Lexer();
		Parser parser = new Parser();
		Eval eval = new Eval();

		try {
			reader.read();
			lexer.lexe(reader.getInputLine());
			parser.parse(lexer2.getToken());
			eval.evaluate(parser.getCompleteTree(), parser.getCompleteNumber());
		} catch (SyntaxException e) {
			System.out.println(e);
		} catch (IOException e) {
			System.out.println(e + "(Input is invalid.)");
		} catch (OtherException e) {
			System.out.println(e);
		}
	}
}