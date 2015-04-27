package lisp;

abstract class ValueNode extends Node {
	public abstract double getValue();
}

class NumberNode extends ValueNode {
	private double value;

	public NumberNode(double value) {
		super();
		this.value = value;
	}

	public boolean addNode(Node node) {
		return false;
	}

	public double getValue() {
		return this.value;
	}
}

class IdNode extends ValueNode {
	private double value;

	public boolean addNode(Node node) {
		return false;
	}

	public double getValue() {
		return this.value;
	}

	public void setValue(double value) {
		this.value = value;
	}
}

abstract class OperatorNode extends ValueNode {
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

class PlusNode extends OperatorNode {
	public double getValue() {
		return super.getRightNode().getValue() + super.getLeftNode().getValue();
	}
}

class MinusNode extends OperatorNode {
	public double getValue() {
		return super.getRightNode().getValue() - super.getLeftNode().getValue();
	}
}

class MultNode extends OperatorNode {
	public double getValue() {
		return super.getRightNode().getValue() * super.getLeftNode().getValue();
	}
}

class DivideNode extends OperatorNode {
	public double getValue() {
		return super.getRightNode().getValue() / super.getLeftNode().getValue();
	}
}