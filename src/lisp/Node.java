package lisp;

import java.util.ArrayList;

public abstract class Node {
	private Node parentNode;

	public Node() {
	}

	public abstract boolean addNode(Node node);

	public Node getParentNode() {
		return this.parentNode;
	}

	public void setParentNode(Node parentNode) {
		this.parentNode = parentNode;
	}
}

class DummyNode extends Node {
	private ArrayList<Node> childNode;

	public boolean addNode(Node node) {
		this.childNode.add(node);
		return true;
	}

	public ArrayList<Node> getChildNode() {
		return this.childNode;
	}
}

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

class SetqNode extends Node {
	private IdNode idNode;
	private ValueNode valueNode;

	public boolean addNode(Node node) {
		if (this.idNode == null) {
			this.idNode = (IdNode) node;
			idNode.setParentNode(this);
			return true;
		} else if (this.valueNode == null) {
			this.valueNode = (ValueNode) node;
			valueNode.setParentNode(this);
			return true;
		} else {
			return false;
		}
	}
}

class IfNode extends Node {
	private BoolNode boolNode;
	private ArrayList<Node> trueNode, falseNode;
	private int closeCheck;

	public IfNode() {
		this.closeCheck = 2;
	}

	public boolean addNode(Node node) {
		if (this.boolNode == null) {
			this.boolNode = (BoolNode) node;
			node.setParentNode(this);
			return true;
		} else if (this.closeCheck == 2) {
			this.trueNode.add(node);
			this.trueNode.get(this.trueNode.size() - 1).setParentNode(this);
			return true;
		} else if (this.closeCheck == 1) {
			this.falseNode.add(node);
			this.falseNode.get(this.falseNode.size() - 1).setParentNode(this);
			return true;
		} else {
			return false;
		}
	}
}

class DefunNode extends Node {
	private FunctionNode functionNode;

	public boolean addNode(Node node) {
		if (this.functionNode == null) {
			this.functionNode = (FunctionNode) node;
			node.setParentNode(this);
			return true;
		} else {
			return false;
		}
	}
}

class FunctionNode extends Node {
	private ArrayList<IdNode> argNode;
	private ArrayList<Node> substanceNode;
	private int closeCheck;

	public FunctionNode() {
		this.closeCheck = 2;
	}

	public boolean addNode(Node node) {
		if (this.closeCheck == 2) {
			this.argNode.add((IdNode) node);
			this.argNode.get(this.argNode.size() - 1).setParentNode(this);
			return true;
		} else if (this.closeCheck == 1) {
			this.substanceNode.add(node);
			this.substanceNode.get(this.substanceNode.size() - 1)
					.setParentNode(this);
			return true;
		} else {
			return false;
		}
	}
}