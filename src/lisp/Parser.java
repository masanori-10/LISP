package lisp;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {
	private Tree tree;
	private IdList idList;
	private int processe;
	private String processedToken;
	private int groupCount;
	private ArrayList<Integer> inIf;
	private int inDefun;
	private int functionNumber;
	private int idInFunctionNumber;
	private int idNumber;
	private Pattern pStatement, pId, pNumber;
	private Matcher mStatement, mId, mNumber;

	public Parser() {
		this.tree = new Tree();
		this.idList = new IdList();
		this.groupCount = 0;
		this.pStatement = Pattern
				.compile("^(T|Nil|setq|if|defun|\\+|-|\\*|\\/|<=|>=|!=|=|<|>|\\(|\\))$");
		this.pId = Pattern.compile("^[a-zA-Z]\\w*$");
		this.pNumber = Pattern.compile("^-?\\d+(\\.\\d+)?$");
	}

	public void parse(ArrayList<String> token) throws SyntaxException {
		this.processe = 0;
		while (this.processe < token.size()) {
			this.processedToken = token.get(this.processe);
			mNumber = pNumber.matcher(this.processedToken);
			mStatement = pStatement.matcher(this.processedToken);
			mId = pId.matcher(this.processedToken);
			if (mNumber.find()) {
				this.tree.addNode(new NumberNode(Double
						.parseDouble(this.processedToken)));
				this.processe++;
			} else if (mStatement.find()) {
				if (this.processedToken.equals("T")) {
					this.tree.addNode(new BoolNode(true));
				} else if (this.processedToken.equals("Nil")) {
					this.tree.addNode(new BoolNode(false));
				} else if (this.processedToken.equals("setq")) {
					this.tree.addAndMoveNode(new SetqNode());
				} else if (this.processedToken.equals("if")) {
					this.inIf.add(this.groupCount);
					this.tree.addAndMoveNode(new IfNode());
					if (this.tree.getProcessedNode() instanceof IfNode) {

					}
				} else if (this.processedToken.equals("defun")) {
					this.inDefun = this.groupCount;
					this.tree.addAndMoveNode(new DefunNode());
					this.processe++;
					this.processedToken = token.get(this.processe);
					this.functionNumber = this.idList
							.getFunctionNumber(this.processedToken);
					if (this.functionNumber == -1) {
						this.functionNumber = this.idList
								.setAndGetNewFunctionNumber(this.processedToken);
						this.idList.setFunctionNode();
						this.tree.addAndMoveNode(this.idList
								.getFunctionNode(this.functionNumber));
					} else {
						this.tree.addAndMoveNode(this.idList
								.getFunctionNode(this.functionNumber));
					}
					this.processe++;
					this.processedToken = token.get(this.processe);
					if (this.processedToken.equals("(")) {
						this.processe++;
						this.processedToken = token.get(this.processe);
						do {
							this.idInFunctionNumber = this.idList
									.setAndGetNewIdInFunctionNumber(
											this.functionNumber,
											this.processedToken);
							this.idList
									.setIdInFunctionNode(this.functionNumber);
							this.tree.addNode(this.idList.getIdInFunctionNode(
									this.functionNumber,
									this.idInFunctionNumber));
							this.processe++;
							this.processedToken = token.get(this.processe);
						} while (!(this.processedToken.equals(")")));
					} else {
						this.idList.setAndGetNewIdInFunctionNumber(
								this.functionNumber, this.processedToken);
						this.idList.setIdInFunctionNode(this.functionNumber);
						this.tree.addNode(this.idList.getIdInFunctionNode(
								this.functionNumber, 0));
					}
					((FunctionNode) this.tree.getProcessedNode()).close();
				} else if (this.processedToken.equals("+")) {
					this.tree.addAndMoveNode(new PlusNode());
				} else if (this.processedToken.equals("-")) {
					this.tree.addAndMoveNode(new MinusNode());
				} else if (this.processedToken.equals("*")) {
					this.tree.addAndMoveNode(new MultNode());
				} else if (this.processedToken.equals("/")) {
					this.tree.addAndMoveNode(new DivideNode());
				} else if (this.processedToken.equals("<=")) {
					this.tree.addAndMoveNode(new LessEqualNode());
				} else if (this.processedToken.equals(">=")) {
					this.tree.addAndMoveNode(new GreaterEqualNode());
				} else if (this.processedToken.equals("!=")) {
					this.tree.addAndMoveNode(new NotEqualNode());
				} else if (this.processedToken.equals("=")) {
					this.tree.addAndMoveNode(new EqualNode());
				} else if (this.processedToken.equals("<")) {
					this.tree.addAndMoveNode(new LessNode());
				} else if (this.processedToken.equals(">")) {
					this.tree.addAndMoveNode(new GreaterNode());
				} else if (this.processedToken.equals("(")) {
					this.tree.addNode(null);
					if (this.tree.getProcessedNode() instanceof IfNode) {
						this.tree.addNode(new DummyNode());
					}
					this.groupCount++;
				} else {
					this.groupCount--;
					if (this.groupCount == this.inDefun - 1) {
						((FunctionNode) this.tree.getProcessedNode()).close();
						this.inDefun = 99;
					} else if (this.groupCount == this.inIf.get(this.inIf
							.size() - 1) - 1) {
						((IfNode) this.tree.getProcessedNode()).close();
						this.inIf.remove(this.inIf.size() - 1);
					}
				}
				this.processe++;
			} else if (mId.find()) {
				if (inDefun <= groupCount) {
					this.idInFunctionNumber = this.idList
							.getIdInFunctionNumber(this.functionNumber,
									this.processedToken);
					this.tree.addNode(this.idList.getIdInFunctionNode(
							this.functionNumber, this.idInFunctionNumber));
				} else {
					this.functionNumber = this.idList
							.getFunctionNumber(this.processedToken);
					if (this.functionNumber != -1) {
						this.tree.addAndMoveNode(this.idList
								.getFunctionNode(this.functionNumber));

					} else {
						this.idNumber = this.idList
								.getIdNumber(this.processedToken);
						if (this.idNumber == -1) {
							this.idNumber = this.idList
									.setAndGetNewIdNumber(this.processedToken);
							this.idList.setIdNode();
							this.tree.addNode(this.idList
									.getIdNode(this.idNumber));
						} else {
							this.tree.addNode(this.idList
									.getIdNode(this.idNumber));
						}
					}
				}
				this.processe++;
			}
		}
	}

	public Tree getTree() {
		return this.tree;
	}
}
