package lisp;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {
	private Tree tree;
	private IdList idList;
	private Pattern pGroup, pStatement, pId, pNumber, pOperator, pComparator;
	private Matcher mGroup, mStatement, mId, mNumber, mOperator, mComparator;

	public Parser() {
		this.tree = new Tree();
		this.idList = new IdList();
		this.pGroup = Pattern.compile("^[\\(\\)]$");
		this.pStatement = Pattern.compile("^(T|Nil|setq|if|defun)$");
		this.pId = Pattern.compile("^[a-zA-Z]\\w*$");
		this.pNumber = Pattern.compile("^-?\\d+(\\.\\d+)?$");
		this.pOperator = Pattern.compile("^[\\+\\-\\*\\/]$");
		this.pComparator = Pattern.compile("^(<=|>=|!=|=|<|>)$");
	}

	public void parse(ArrayList<String> token) throws SyntaxException {
		int processe = 0;
		while (processe < token.size()) {
			String processeToken = token.get(processe);
			mNumber = pNumber.matcher(processeToken);
			mStatement = pStatement.matcher(processeToken);
			if (mNumber.find()) {
				this.tree.addNode(new NumberNode(Double
						.parseDouble(processeToken)));
				processe++;
			} else if (mStatement.find()) {
				if (processeToken.equals("T")) {
					this.tree.addNode(new BoolNode(true));
				} else if (processeToken.equals("Nil")) {
					this.tree.addNode(new BoolNode(false));
				} else if (processeToken.equals("setq")) {
					this.tree.addNode(new SetqNode());
				} else if (processeToken.equals("if")) {
					this.tree.addNode(new IfNode());
				} else {
					this.tree.addNode(new DefunNode());
				}
				processe++;
			} else if (mId.find()) {
				int idNumber = this.idList.getIdNumber(processeToken);
				if (idNumber == -1) {
					idNumber = this.idList.setAndGetNewIdNumber(processeToken);
					this.idList.setIdNode();
					this.tree.addNode(this.idList.getIdNode(idNumber));
				} else {
					this.tree.addNode(this.idList.getIdNode(idNumber));
				}
				processe++;
			}

		}
	}

	public Tree getTree() {
		return this.tree;
	}
}
