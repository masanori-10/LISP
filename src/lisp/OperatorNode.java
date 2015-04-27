package lisp;

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