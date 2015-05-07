package lisp;

import java.util.ArrayList;

import lisp.Enum.Token;

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

	public void commandize() {
		CommandLine.addCommand(new Command(this.value));
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

class OperatorNode extends ValueNode {
	private Node rightNode, leftNode;
	private Token token;

	public OperatorNode(Token token) {
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