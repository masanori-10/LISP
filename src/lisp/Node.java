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

	public void startUp() {
		this.idNode.setValue(this.valueNode.getValue());
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

	public void close() {
		this.closeCheck--;
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

	public void close() {
		this.closeCheck--;
	}
}