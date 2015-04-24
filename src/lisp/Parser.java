package lisp;

import java.util.ArrayList;

public class Parser {
	private TreeNode treeNode;
	private int parenCount;

	public Parser() {
		this.treeNode = new TreeNode();
		this.parenCount = 0;
	}

	public void parse(ArrayList<Token> token) throws SyntaxException {
		int processe = 0;
		while (processe < token.size()) {
			switch (token.get(processe).getAttribute()) {
			case "group":
				if (token.get(processe).getName().equals("(")) {
					if (parenCount != 0) {
						this.treeNode.add(token[i]);
					}
					this.treeNode.open();
					this.parenCount++;
				} else {
					this.treeNode.close();
					this.parenCount--;
					if (parenCount == 0) {
						if (this.treeNode.getProcessedNode() != this.treeNode
								.getRootNode()) {
							throw new SyntaxException();
						}
						this.completeTree[this.completeNumber] = this.treeNode;
						this.treeNode = new TreeNode();
						this.completeNumber++;
					} else {
						while (this.treeNode.getParentNode().getToken() != null) {
							if (this.treeNode.getParentNode().getToken()
									.getArgumentCounter() == 0) {
								this.treeNode.close();
							} else {
								break;
							}
						}
					}
				}
				break;
			case "command":
			case "operator":
			case "comparator":
			case "defunedId":
				this.treeNode.add(token.get(processe));
				this.treeNode.moveToLastChild();
				break;
			case "number":
			case "id":
			case "bool":
				this.treeNode.add(token.get(processe));
				break;
			}
		}
	}

	public TreeNode getTreeNode() {
		return this.treeNode;
	}
}
