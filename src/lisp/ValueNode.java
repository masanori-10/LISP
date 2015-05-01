package lisp;

import java.util.ArrayList;

public abstract class ValueNode extends Node {
}

class NumberNode extends ValueNode {
	private double value;

	public NumberNode(double value) {
		this.value = value;
	}

	public double getValue() {
		return this.value;
	}
}

class IdNode extends ValueNode {
	private Double[] value;

	public IdNode(Double[] value) {
		this.value = value;
	}

	public double getValue() {
		return this.value[0];
	}

	public void setValue(Double value) {
		this.value[0] = value;
	}
}

class IdInFunctionNode extends ValueNode {
	private ArrayList<Double> value;

	public IdInFunctionNode(ArrayList<Double> value) {
		this.value = value;
	}

	public double getValue() {
		return this.value.get(this.value.size() - 1);
	}

	public void addValue(double value) {
		this.value.add(value);
	}

	public void removeLastValue() {
		this.value.remove(this.value.size() - 1);
	}
}

abstract class OperatorNode extends ValueNode {
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

class PlusNode extends OperatorNode {
	public double getValue() throws SyntaxException {
		return this.getRightNode().getValue() + this.getLeftNode().getValue();
	}
}

class MinusNode extends OperatorNode {
	public double getValue() throws SyntaxException {
		return this.getRightNode().getValue() - this.getLeftNode().getValue();
	}
}

class MultNode extends OperatorNode {
	public double getValue() throws SyntaxException {
		return this.getRightNode().getValue() * this.getLeftNode().getValue();
	}
}

class DivideNode extends OperatorNode {
	public double getValue() throws SyntaxException {
		return this.getRightNode().getValue() / this.getLeftNode().getValue();
	}
}