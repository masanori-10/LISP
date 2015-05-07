package lisp;

import lisp.Enum.Token;

public abstract class BooleanNode extends Node {
}

class BoolNode extends BooleanNode {
	private boolean bool;

	public BoolNode(boolean bool) {
		this.bool = bool;
	}

	public boolean getBool() {
		return this.bool;
	}
}

class ComparatorNode extends BooleanNode {
	private Node rightNode, leftNode;
	private Token token;

	public ComparatorNode(Token token) {
		this.token = token;
	}

	public boolean addNode(Node node) {
		if (this.rightNode == null) {
			this.rightNode = node;
			if (!(node == null)) {
				node.setParentNode(this);
			}
			return true;
		} else if (this.leftNode == null) {
			this.leftNode = node;
			if (!(node == null)) {
				node.setParentNode(this);
			}
			return true;
		} else {
			return false;
		}
	}

	public void commandize() {
		this.rightNode.commandize();
		this.leftNode.commandize();
		CommandLine.addCommand(new Command(this.token));
	}
}
