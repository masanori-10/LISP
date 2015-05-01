package lisp;

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

abstract class ComparatorNode extends BooleanNode {
	private Node rightNode, leftNode;

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

	public Node getRightNode() {
		return this.rightNode;
	}

	public Node getLeftNode() {
		return this.leftNode;
	}
}

class EqualNode extends ComparatorNode {
	public boolean getBool() throws SyntaxException {
		return (this.getRightNode().getValue() == this.getLeftNode().getValue());
	}
}

class NotEqualNode extends ComparatorNode {
	public boolean getBool() throws SyntaxException {
		return (this.getRightNode().getValue() != this.getLeftNode().getValue());
	}
}

class LessNode extends ComparatorNode {
	public boolean getBool() throws SyntaxException {
		return (this.getRightNode().getValue() < this.getLeftNode().getValue());
	}
}

class LessEqualNode extends ComparatorNode {
	public boolean getBool() throws SyntaxException {
		return (this.getRightNode().getValue() <= this.getLeftNode().getValue());
	}
}

class GreaterNode extends ComparatorNode {
	public boolean getBool() throws SyntaxException {
		return (this.getRightNode().getValue() > this.getLeftNode().getValue());
	}
}

class GreaterEqualNode extends ComparatorNode {
	public boolean getBool() throws SyntaxException {
		return (this.getRightNode().getValue() >= this.getLeftNode().getValue());
	}
}