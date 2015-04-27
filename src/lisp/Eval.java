package lisp;

public class Eval {
	private Node result;
	private TreeNode[] tree;
	private int treeNumber;
	private FunctionList function;

	public Eval() {
		this.result = new Node();
		this.function = new FunctionList();
	}

	public void evaluate(TreeNode[] tree, int treeNumber)
			throws OtherException, SyntaxException {
		this.tree = tree;
		this.treeNumber = treeNumber;
		for (int i = 0; i < this.treeNumber; i++) {
			this.result = this.evaluate(this.tree[i].getProcessedNode()
					.getChild(1));
			if (this.result != null) {
				if (this.result.getAttribute().equals("bool")) {
					System.out.println(this.result.getName());
				} else {
					System.out.println(this.result.getValue());
				}
			} else {
				this.result = new Node();
			}
		}
	}

	public Node evaluate(Tree processedNode) throws OtherException,
			SyntaxException {
		double operandX, operandY;
		switch (processedNode.getToken().getAttribute()) {
		case "operator":
			operandX = this.evaluate(processedNode.getChild(1)).getValue();
			operandY = this.evaluate(processedNode.getChild(2)).getValue();
			this.result.setAttribute("number");
			this.result.setName(null);
			switch (processedNode.getToken().getName()) {
			case "+":
				this.result.setValue(operandX + operandY);
				break;
			case "-":
				this.result.setValue(operandX - operandY);
				break;
			case "*":
				this.result.setValue(operandX * operandY);
				break;
			case "/":
				this.result.setValue(operandX / operandY);
				break;
			default:
				throw new OtherException();
			}
			return this.result;
		case "comparator":
			String bool;
			operandX = this.evaluate(processedNode.getChild(1)).getValue();
			operandY = this.evaluate(processedNode.getChild(2)).getValue();
			switch (processedNode.getToken().getName()) {
			case "<=":
				if (operandX <= operandY) {
					bool = "T";
				} else {
					bool = "Nil";
				}
				break;
			case ">=":
				if (operandX >= operandY) {
					bool = "T";
				} else {
					bool = "Nil";
				}
				break;
			case "!=":
				if (operandX != operandY) {
					bool = "T";
				} else {
					bool = "Nil";
				}
				break;
			case "=":
				if (operandX == operandY) {
					bool = "T";
				} else {
					bool = "Nil";
				}
				break;
			case "<":
				if (operandX < operandY) {
					bool = "T";
				} else {
					bool = "Nil";
				}
				break;
			case ">":
				if (operandX > operandY) {
					bool = "T";
				} else {
					bool = "Nil";
				}
				break;
			default:
				throw new OtherException();
			}
			this.result.setAttribute("bool");
			this.result.setName(bool);
			this.result.setValue(0.0);
			return this.result;
		case "command":
			switch (processedNode.getToken().getName()) {
			case "setq":
				if (processedNode.getChild(1).getToken().getAttribute()
						.equals("id")) {
					for (int i = 0; i < treeNumber; i++) {
						this.adjustId(processedNode.getChild(1).getToken(),
								this.tree[i].getProcessedNode().getChild(1));
					}
				} else {
					throw new SyntaxException();
				}
				processedNode
						.getChild(1)
						.getToken()
						.setValue(
								this.evaluate(processedNode.getChild(2))
										.getValue());
				return null;
			case "if":
				this.result = this.evaluate(processedNode.getChild(1));
				if (!(this.result.getAttribute().equals("bool"))) {
					throw new SyntaxException();
				}
				if (this.result.getName().equals("T")) {
					this.result = this.evaluate(processedNode.getChild(2));
				} else if (this.result.getName().equals("Nil")) {
					this.result = this.evaluate(processedNode.getChild(3));
				} else {
					throw new OtherException();
				}
				return this.result;
			case "defun":
				Node[] argument = new Node[10];
				processedNode.getChild(1).getToken().setAttribute("defunedId");
				for (int i = 0; i < treeNumber; i++) {
					this.adjustId(processedNode.getChild(1).getToken(),
							this.tree[i].getProcessedNode().getChild(1));
				}
				if (processedNode.getChild(2).getToken().getAttribute()
						.equals("id")) {
					this.adjustId(processedNode.getChild(2).getToken(),
							processedNode.getChild(3));
					processedNode.getChild(1).getToken().setArgumentCounter(1);
					argument[0] = processedNode.getChild(2).getToken();
				} else if (processedNode.getChild(2).getToken().getAttribute()
						.equals("paren")) {
					int i = 1;
					while (processedNode.getChild(2).getChild(i) != null) {
						this.adjustId(processedNode.getChild(2).getChild(i)
								.getToken(), processedNode.getChild(3));
						argument[i - 1] = processedNode.getChild(2).getChild(i)
								.getToken();
						i++;
					}
					processedNode.getChild(1).getToken()
							.setArgumentCounter(i - 1);
				} else {
					throw new SyntaxException();
				}
				function.setFunction(processedNode.getChild(1).getToken()
						.getName(), argument, processedNode.getChild(3));
				return null;
			default:
				throw new OtherException();
			}
		case "defunedId":
			double[] argumentValue = new double[10];
			double[] previousValue = new double[10];
			for (int i = 0; true; i++) {
				if (processedNode.getParent().getChild(i) == processedNode) {
					for (int j = i + 1; j <= i
							+ processedNode.getToken().getArgumentCounter(); j++) {
						argumentValue[j - i - 1] = this.evaluate(
								processedNode.getParent().getChild(j))
								.getValue();
					}
					break;
				}
			}
			previousValue = function.getArgument(processedNode.getToken()
					.getName());
			this.result = this.evaluate(function.getFunction(processedNode
					.getToken().getName(), argumentValue));
			function.setArgument(processedNode.getToken().getName(),
					previousValue);
			return this.result;
		case "number":
			this.result.setAttribute("number");
			this.result.setName(null);
			this.result.setValue(processedNode.getToken().getValue());
			return this.result;
		case "id":
			this.result.setAttribute("id");
			this.result.setName(processedNode.getToken().getName());
			this.result.setValue(processedNode.getToken().getValue());
			return this.result;
		case "bool":
			this.result.setAttribute("bool");
			this.result.setName(processedNode.getToken().getName());
			this.result.setValue(0.0);
			return this.result;
		case "paren":
			if (!(processedNode.getChild(1).getToken().getAttribute()
					.equals("defunedId"))) {
				for (int i = 2; processedNode.getChild(i) != null; i++) {
					this.evaluate(processedNode.getChild(i));
				}
			}
			return this.evaluate(processedNode.getChild(1));
		default:
			throw new OtherException();
		}
	}

	public void adjustId(Node token, Tree tree) {
		if (tree.getToken().getAttribute().equals("id")) {
			if (tree.getToken().getName().equals(token.getName())) {
				tree.setToken(token);
			}
		}
		for (int i = 1; tree.getChild(i) != null; i++) {
			this.adjustId(token, tree.getChild(i));
		}
	}
}
