package lisp;

abstract class BooleanNode extends Node {
	public abstract boolean getBool();
}

class BoolNode extends BooleanNode {
	private boolean bool;

	public BoolNode(boolean bool) {
		this.bool = bool;
	}

	public boolean addNode(Node node) {
		return false;
	}

	public boolean getBool() {
		return this.bool;
	}
}

abstract class ComparatorNode extends BooleanNode {
	private ValueNode rightNode, leftNode;

	public boolean addNode(Node valueNode) {
		if (this.rightNode == null) {
			this.rightNode = (ValueNode) valueNode;
			valueNode.setParentNode(this);
			return true;
		} else if (this.leftNode == null) {
			this.leftNode = (ValueNode) valueNode;
			valueNode.setParentNode(this);
			return true;
		} else {
			return false;
		}
	}

	public ValueNode getRightNode() {
		return this.rightNode;
	}

	public ValueNode getLeftNode() {
		return this.leftNode;
	}
}

class EqualNode extends ComparatorNode {
	public boolean getBool() {
		return (super.getRightNode().getValue() == super.getLeftNode()
				.getValue());
	}
}

class NotEqualNode extends ComparatorNode {
	public boolean getBool() {
		return (super.getRightNode().getValue() != super.getLeftNode()
				.getValue());
	}
}

class LessNode extends ComparatorNode {
	public boolean getBool() {
		return (super.getRightNode().getValue() < super.getLeftNode()
				.getValue());
	}
}

class LessEqualNode extends ComparatorNode {
	public boolean getBool() {
		return (super.getRightNode().getValue() <= super.getLeftNode()
				.getValue());
	}
}

class GreaterNode extends ComparatorNode {
	public boolean getBool() {
		return (super.getRightNode().getValue() > super.getLeftNode()
				.getValue());
	}
}

class GreaterEqualNode extends ComparatorNode {
	public boolean getBool() {
		return (super.getRightNode().getValue() >= super.getLeftNode()
				.getValue());
	}
}
