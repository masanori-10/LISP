package lisp;

import java.util.ArrayList;

import lisp.Enum.Token;

public class Parser {
	private Tree tree;
	private IdList idList;
	private int index;
	private String currentToken;
	private Token token;
	private Node currentNode;
	private ArrayList<Integer> groupCount;
	private int groupCountEnd;
	private boolean inFunction;

	public Parser() {
		this.tree = new Tree();
		this.idList = new IdList();
		this.groupCount = new ArrayList<Integer>();
		this.groupCountEnd = -1;
	}

	public void parse(ArrayList<String> token) throws SyntaxException {
		this.index = 0;
		while (this.index < token.size()) {
			this.currentToken = token.get(this.index);
			this.token = Token.getEnum(this.currentToken);
			if (this.isNumber(this.currentToken)) {
				this.tree.addNode(new NumberNode(Double
						.parseDouble(this.currentToken)));
			} else {
				switch (this.token) {
				case T:
					this.tree.addNode(new BoolNode(true));
					break;
				case NIL:
					this.tree.addNode(new BoolNode(false));
					break;
				case SETQ:
					this.tree.addAndMoveNode(new SetqNode());
					break;
				case IF:
					this.tree.addAndMoveNode(new IfNode());
					break;
				case DEFUN:
					this.tree.addAndMoveNode(new DefunNode());
					this.index++;
					this.currentToken = token.get(this.index);
					this.tree.addAndMoveNode(this.idList
							.addFunctionNode(this.currentToken));
					break;
				case PLUS:
				case MINUS:
				case MULT:
				case DIVIDE:
					this.tree.addAndMoveNode(new OperatorNode(this.token));
					break;
				case LESSEQUAL:
				case GREATEREQUAL:
				case NOTEQUAL:
				case EQUAL:
				case LESS:
				case GREATER:
					this.tree.addAndMoveNode(new ComparatorNode(this.token));
					break;
				case OPEN:
					this.tree.addNode(null);
					this.currentNode = this.tree.getProcessedNode();
					if ((this.currentNode instanceof IfNode)
							|| (this.currentNode instanceof FunctionNode)) {
						this.tree.addAndMoveNode(new DummyNode());
						this.groupCount.add(1);
						this.groupCountEnd++;
					} else if (!this.groupCount.isEmpty()) {
						this.groupCount.set(this.groupCountEnd,
								this.groupCount.get(this.groupCountEnd) + 1);
					}
					break;
				case CLOSE:
					this.tree.addNode(null);
					if (!this.groupCount.isEmpty()) {
						this.groupCount.set(this.groupCountEnd,
								this.groupCount.get(this.groupCountEnd) - 1);
						if (this.groupCount.get(this.groupCountEnd) == 0) {
							this.tree.moveToParant();
							this.groupCount.remove(this.groupCountEnd);
							this.groupCountEnd--;
						}
					}
					break;
				default:
					if (this.idList.checkFunctionName(this.currentToken)) {
						this.tree.addAndMoveNode(this.idList
								.getFunctionNode(this.currentToken));
						this.currentNode = this.tree.getProcessedNode();
						this.tree
								.addAndMoveNode(new DummyNode(
										((FunctionNode) this.currentNode)
												.getArgCount()[0]));
					} else {
						this.currentNode = this.tree.getProcessedNode();
						while (true) {
							if (this.currentNode instanceof DefunNode) {
								this.inFunction = true;
								break;
							}
							if (this.currentNode.getParentNode() == null) {
								this.inFunction = false;
								break;
							} else {
								this.currentNode = this.currentNode
										.getParentNode();
							}
						}
						if (inFunction) {
							this.tree.addNode(new IdInFunctionNode(this.idList
									.getIdInFunctionValue(
											((DefunNode) this.currentNode)
													.getFunctionNode(),
											this.currentToken)));
						} else {
							this.tree.addNode(new IdNode(this.idList
									.getIdValue(this.currentToken)));
						}
					}
				}
			}
			this.index++;
		}
	}

	public Tree getTree() {
		return this.tree;
	}

	private boolean isNumber(String str) {
		try {
			Double.parseDouble(str);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
}
