package lisp;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Lexer {
	private ArrayList<String> token;
	private Pattern pQuick, pSlow, pSpace, pOther;
	private Matcher mQuick, mSlow, mSpace, mOther;

	public Lexer() {
		this.token = new ArrayList<String>();
		this.pQuick = Pattern.compile("^(\\(|\\)|\\+|\\*|\\/|<=|>=|!=|=)$");
		this.pSlow = Pattern
				.compile("^([a-zA-Z]\\w*\\W|-?\\d+(\\.\\d+)?\\D|-\\D|<[^=]|>[^=])$");
		this.pSpace = Pattern.compile("^[\\s]$");
		this.pOther = Pattern
				.compile("^[^\\s\\(\\)a-zA-Z0-9<>=!\\+\\-\\*\\/\\.]$");
	}

	public void lexe(String inputLine) throws SyntaxException {
		int lexemeBegin = 0, forward = 1;

		while (inputLine.length() > forward) {
			mOther = pOther.matcher(inputLine.substring(forward - 1, forward));
			mSpace = pSpace.matcher(inputLine.substring(lexemeBegin, forward));
			mQuick = pQuick.matcher(inputLine.substring(lexemeBegin, forward));
			mSlow = pSlow.matcher(inputLine.substring(lexemeBegin, forward));
			if (mOther.find()) {
				throw new SyntaxException();
			} else if (mSpace.find()) {
				lexemeBegin++;
				forward++;
			} else if (mQuick.find()) {
				this.token.add(inputLine.substring(lexemeBegin, forward));
				lexemeBegin++;
				forward++;
			} else if (mSlow.find()) {
				this.token.add(inputLine.substring(lexemeBegin, forward - 1));
				lexemeBegin = forward - 1;
			} else {
				forward++;
			}
		}
	}

	public ArrayList<String> getToken() {
		return this.token;
	}

}
