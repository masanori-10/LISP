package lisp;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Lexer {
	private ArrayList<Node> token;
	private boolean defunCheck;
	private Pattern other, space, group, bool, command, id, number, operator1,
			operator2, comparator1, comparator2, stock;
	private Matcher m;

	public Lexer() {
		this.token = new ArrayList<Node>();
		this.defunCheck = false;
		this.other = Pattern
				.compile("^[^\\s\\(\\)a-zA-Z0-9<>=!\\+\\-\\*\\/\\.]$");
		this.space = Pattern.compile("^[\\s]$");
		this.group = Pattern.compile("^[\\(\\)]$");
		this.bool = Pattern.compile("^(T|Nil)[^a-zA-Z0-9]$");
		this.command = Pattern.compile("^(setq|if|defun)[^a-zA-Z0-9]$");
		this.id = Pattern.compile("^[a-zA-Z][a-zA-Z0-9]*[^a-zA-Z0-9]$");
		this.number = Pattern
				.compile("^-?[0-9]+(\\.[0-9]+)?(E[+\\-]?[0-9])?[^\\.E0-9]$");
		this.operator1 = Pattern.compile("^[\\+\\*\\/]$");
		this.operator2 = Pattern.compile("^-[^0-9]$");
		this.comparator1 = Pattern.compile("^<=|>=|!=|=$");
		this.comparator2 = Pattern.compile("^<[^=]|>[^=]$");
		this.stock = Pattern
				.compile("^[a-zA-Z][a-zA-Z0-9]*|-?[0-9]+(\\.[0-9]+)?(E[+\\-]?[0-9])?|[<>!\\-]$");
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
			m = group.matcher(inputLine.substring(lexemeBegin, forward));
			if (m.find()) {
				this.token.add(new Node("group", inputLine.substring(
						lexemeBegin, forward)));
				lexemeBegin++;
				forward++;
			}
			m = bool.matcher(inputLine.substring(lexemeBegin, forward));
			if (m.find()) {
				this.token.add(new Node("bool", inputLine.substring(
						lexemeBegin, forward - 1)));
				lexemeBegin = forward - 1;
			}
			m = command.matcher(inputLine.substring(lexemeBegin, forward));
			if (m.find()) {
				this.token.add(new Node("command", inputLine.substring(
						lexemeBegin, forward - 1)));
				if (this.token.get(this.token.size() - 1).getName()
						.equals("defun")) {
					this.defunCheck = true;
				}
				lexemeBegin = forward - 1;
			}
			m = id.matcher(inputLine.substring(lexemeBegin, forward));
			if (m.find()) {
				if (this.defunCheck == false) {
					this.token.add(new Node("id", inputLine.substring(
							lexemeBegin, forward - 1)));
				} else {
					this.token.add(new Node("defunedId", inputLine.substring(
							lexemeBegin, forward - 1)));
					this.defunCheck = false;
				}
				lexemeBegin = forward - 1;
			}
			m = number.matcher(inputLine.substring(lexemeBegin, forward));
			if (m.find()) {
				this.token.add(new Node("number", Double.parseDouble(inputLine
						.substring(lexemeBegin, forward - 1))));
				lexemeBegin = forward - 1;
			}
			m = operator1.matcher(inputLine.substring(lexemeBegin, forward));
			if (m.find()) {
				this.token.add(new Node("operator", inputLine.substring(
						lexemeBegin, forward)));
				lexemeBegin = forward;
				forward++;
			}
			m = operator2.matcher(inputLine.substring(lexemeBegin, forward));
			if (m.find()) {
				this.token.add(new Node("operator", inputLine.substring(
						lexemeBegin, forward - 1)));
				lexemeBegin = forward - 1;
			}
			m = comparator1.matcher(inputLine.substring(lexemeBegin, forward));
			if (m.find()) {
				this.token.add(new Node("comparator", inputLine.substring(
						lexemeBegin, forward)));
				lexemeBegin = forward;
				forward++;
			}
			m = comparator2.matcher(inputLine.substring(lexemeBegin, forward));
			if (m.find()) {
				this.token.add(new Node("comparator", inputLine.substring(
						lexemeBegin, forward - 1)));
				lexemeBegin = forward - 1;
			}
			m = stock.matcher(inputLine.substring(lexemeBegin, forward));
			if (m.find()) {
				forward++;
			}
		}
	}

	public Node getToken(int i) {
		return this.token[i];
	}

	public ArrayList<Node> getToken() {
		return this.token;
	}
}
