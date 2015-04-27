package lisp;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {
	private TreeNode treeNode;
	private int parenCount;
	private Pattern group, bool, command, id, number, operator, comparator;
	private Matcher m;

	public Parser() {
		this.treeNode = new TreeNode();
		this.parenCount = 0;
		this.group = Pattern.compile("^[\\(\\)]$");
		this.bool = Pattern.compile("^(T|Nil)$");
		this.command = Pattern.compile("^(setq|if|defun)$");
		this.id = Pattern.compile("^[a-zA-Z]\\w*$");
		this.number = Pattern.compile("^-?\\d+(\\.\\d+)?$");
		this.operator = Pattern.compile("^[\\+\\-\\*\\/]$");
		this.comparator = Pattern.compile("^(<=|>=|!=|=|<|>)$");
	}

	public void parse(ArrayList<String> token) throws SyntaxException {
		int processe = 0;
		while (processe < token.size()) {
			m = number.matcher(token.get(processe));
			if (m.find()) {

			}
		}
	}

	public TreeNode getTreeNode() {
		return this.treeNode;
	}
}
