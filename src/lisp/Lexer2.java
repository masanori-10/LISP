package lisp;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Lexer2 {
	private ArrayList<String> token;
	private Pattern quick, slow, space, other;
	private Matcher m;

	public Lexer2() {
		this.token = new ArrayList<String>();
		this.quick = Pattern.compile("^(\\(|\\)|\\+|\\*|\\/|<=|>=|!=|=)$");
		this.slow = Pattern
				.compile("^([a-zA-Z]\\w*\\W|-?\\d+(\\.\\d+)?\\D|-\\D|<[^=]|>[^=])$");
		this.space = Pattern.compile("^[\\s]$");
		this.other = Pattern
				.compile("^[^\\s\\(\\)a-zA-Z0-9<>=!\\+\\-\\*\\/\\.]$");
	}

	public void lexe(String inputLine) throws SyntaxException {
		int lexemeBegin = 0, forward = 1;

		while (inputLine.length() > forward) {
			m = other.matcher(inputLine.substring(forward - 1, forward));
			if (m.find()) {
				throw new SyntaxException();
			}
			m = space.matcher(inputLine.substring(lexemeBegin, forward));
			if (m.find()) {
				lexemeBegin++;
				forward++;
			}
			m = quick.matcher(inputLine.substring(lexemeBegin, forward));
			if (m.find()) {
				this.token.add(inputLine.substring(lexemeBegin, forward));
				lexemeBegin++;
				forward++;
			}
			m = slow.matcher(inputLine.substring(lexemeBegin, forward));
			if (m.find()) {
				this.token.add(inputLine.substring(lexemeBegin, forward - 1));
				lexemeBegin = forward - 1;
			}
		}
	}

	public ArrayList<String> getToken() {
		return this.token;
	}

}
