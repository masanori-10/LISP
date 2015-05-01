package lisp;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lisp.Enum.Token;

public class Parser {
	private Tree tree;
	private IdList idList;
	private int processe;
	private String processedToken;
	private Token token;
	private Node processedNode;
	private ArrayList<Integer> groupCount;
	private int groupCountEnd;
	private boolean inFunction;
	private Pattern pStatement, pId, pNumber;
	private Matcher mStatement, mId, mNumber;

	public Parser() {
		this.tree = new Tree();
		this.idList = new IdList();
		this.groupCount = new ArrayList<Integer>();
		this.groupCountEnd = -1;
		this.pStatement = Pattern
				.compile("^(T|Nil|setq|if|defun|\\+|-|\\*|\\/|<=|>=|!=|=|<|>|\\(|\\))$");
		this.pId = Pattern.compile("^[a-zA-Z]\\w*$");
		this.pNumber = Pattern.compile("^-?\\d+(\\.\\d+)?$");
	}

	public void parse(ArrayList<String> token) throws SyntaxException {
		this.processe = 0;
		while (this.processe < token.size()) {
			this.processedToken = token.get(this.processe);
			this.token = Token.getEnum(this.processedToken);
			mNumber = pNumber.matcher(this.processedToken);
			mStatement = pStatement.matcher(this.processedToken);
			mId = pId.matcher(this.processedToken);
			if (mNumber.find()) {
				this.tree.addNode(new NumberNode(Double
						.parseDouble(this.processedToken)));
			} else if (mStatement.find()) {
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
					this.processe++;
					this.processedToken = token.get(this.processe);
					this.tree.addAndMoveNode(this.idList
							.addFunctionNode(this.processedToken));
					break;
				case PLUS:
					this.tree.addAndMoveNode(new PlusNode());
					break;
				case MINUS:
					this.tree.addAndMoveNode(new MinusNode());
					break;
				case MULT:
					this.tree.addAndMoveNode(new MultNode());
					break;
				case DIVIDE:
					this.tree.addAndMoveNode(new DivideNode());
					break;
				case LESSEQUAL:
					this.tree.addAndMoveNode(new LessEqualNode());
					break;
				case GREATEREQUAL:
					this.tree.addAndMoveNode(new GreaterEqualNode());
					break;
				case NOTEQUAL:
					this.tree.addAndMoveNode(new NotEqualNode());
					break;
				case EQUAL:
					this.tree.addAndMoveNode(new EqualNode());
					break;
				case LESS:
					this.tree.addAndMoveNode(new LessNode());
					break;
				case GREATER:
					this.tree.addAndMoveNode(new GreaterNode());
					break;
				case OPEN:
					this.tree.addNode(null);
					this.processedNode = this.tree.getProcessedNode();
					if ((this.processedNode instanceof IfNode)
							|| (this.processedNode instanceof FunctionNode)) {
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
				}
			} else if (mId.find()) {
				if (this.idList.checkFunctionName(this.processedToken)) {
					this.tree.addAndMoveNode(this.idList
							.getFunctionNode(this.processedToken));
					this.processedNode = this.tree.getProcessedNode();
					this.tree
							.addAndMoveNode(new DummyNode(
									((FunctionNode) this.processedNode)
											.getArgCount()[0]));
				} else {
					this.processedNode = this.tree.getProcessedNode();
					while (true) {
						if (this.processedNode instanceof DefunNode) {
							this.inFunction = true;
							break;
						}
						if (this.processedNode.getParentNode() == null) {
							this.inFunction = false;
							break;
						} else {
							this.processedNode = this.processedNode
									.getParentNode();
						}
					}
					if (inFunction) {
						this.tree.addNode(new IdInFunctionNode(this.idList
								.getIdInFunctionValue(
										((DefunNode) this.processedNode)
												.getFunctionNode(),
										this.processedToken)));
					} else {
						this.tree.addNode(new IdNode(this.idList
								.getIdValue(this.processedToken)));
					}
				}
			}
			this.processe++;
		}
	}

	public Tree getTree() {
		return this.tree;
	}
}
