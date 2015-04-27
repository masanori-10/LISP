package lisp;

public abstract class Node {
	private Node parentNode;

	public Node() {
	}

	public Node getParentNode() {
		return this.parentNode;
	}

	public void setParentNode(Node parentNode) {
		this.parentNode = parentNode;
	}

	public abstract double getValue();
}

class NumberNode extends Node {
	private double value;

	public NumberNode(double value) {
		super();
		this.value = value;
	}

	public double getValue() {
		return this.value;
	}
}

class IdNode extends Node {
	private String name;
	private double value;

	public IdNode(String name) {
		this.name = name;
	}

	public double getValue() {
		return this.value;
	}

	public String getName() {
		return this.name;
	}

	public void setValue(double value) {
		this.value = value;
	}
}

abstract class OperatorNode extends Node {
	private Node operandX, operandY;

	public void addNode(Node node) {
		if (this.operandX == null) {
			this.operandX = node;
		} else if (this.operandY == null) {
			this.operandY = node;
		}
	}

	public Node getOperandX() {
		return this.operandX;
	}

	public Node getOperandY() {
		return this.operandY;
	}
}

class PlusNode extends OperatorNode {
	public double getValue() {
		return super.getOperandX().getValue() + super.getOperandY().getValue();
	}
}

class MinusNode extends OperatorNode {
	public double getValue() {
		return super.getOperandX().getValue() - super.getOperandY().getValue();
	}
}

class MultNode extends OperatorNode {
	public double getValue() {
		return super.getOperandX().getValue() * super.getOperandY().getValue();
	}
}

class DivideNode extends OperatorNode {
	public double getValue() {
		return super.getOperandX().getValue() / super.getOperandY().getValue();
	}
}
class SetqNode {
	private IdNode id;
	private Node;

}