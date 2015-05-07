package lisp;

import java.util.ArrayList;

import lisp.Enum.Token;

public class Parser {
	private Tree tree;
	private int index;
	private String currentToken;
	private Token token;
	private Node currentNode;
	private ArrayList<Integer> groupCount;
	private int groupCountEnd;
	private boolean inFunction;

	public Parser() {
		this.tree = new Tree();
		this.groupCount = new ArrayList<Integer>();
		this.groupCountEnd = -1;
	}

	public void parse(ArrayList<String> token) throws SyntaxException {
		this.index = 0;
		while (this.index < token.size()) {
			this.currentToken = token.get(this.index);
			this.token = Token.getEnum(this.currentToken);

			if (this.currentNode instanceof IfNode) {
				if (((IfNode) this.currentNode).needTheDummy()) {
					this.tree.addAndMoveNode(new DummyNode());
					this.groupCount.add(0);
					this.groupCountEnd++;
				}
			}
			if (this.currentNode instanceof FunctionNode) {
				this.tree.addAndMoveNode(new DummyNode());
				this.groupCount.add(0);
				this.groupCountEnd++;
			}

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
					this.tree
							.addAndMoveNode(new FunctionNode(this.currentToken));
					MapForFunction.setFunction(this.currentToken);
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
					if (!this.groupCount.isEmpty()) {
						this.groupCount.set(this.groupCountEnd,
								this.groupCount.get(this.groupCountEnd) + 1);
					}
					break;
				case CLOSE:
					if (!this.groupCount.isEmpty()) {
						this.groupCount.set(this.groupCountEnd,
								this.groupCount.get(this.groupCountEnd) - 1);
					}
					break;
				default:
					if (MapForFunction.existFunction(this.currentToken)) {
						this.tree.addAndMoveNode(new FunctionNode(
								this.currentToken));
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
							this.tree.addNode(new VariableNode(
									((DefunNode) this.currentNode)
											.getFunctionNode().getKey()
											+ "\\"
											+ this.currentToken));
							MapForArgument
									.setArgument(((DefunNode) this.currentNode)
											.getFunctionNode().getKey()
											+ "\\"
											+ this.currentToken);
						} else {
							this.tree.addNode(new VariableNode(
									this.currentToken));
						}
					}
				}
			}
			if (!this.groupCount.isEmpty()) {
				if (this.groupCount.get(this.groupCountEnd) == 0) {
					this.tree.moveToParant();
					this.groupCount.remove(this.groupCountEnd);
					this.groupCountEnd--;
				}
			}
			this.index++;
			this.tree.addNode(null);
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
