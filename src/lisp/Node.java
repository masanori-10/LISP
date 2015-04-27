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

abstract class VoidNode extends Node {
	public abstract void startup();
}

class DummyNode extends VoidNode {
	private ArrayList<Node> childNode;
	private int processe;
	private Node processedNode;

	public DummyNode() {
		this.processe = 0;
	}

	public boolean addNode(Node node) {
		this.childNode.add(node);
		return true;
	}

	public void startup() {
		do {
			this.processedNode = this.childNode.get(processe);
			if (this.processedNode instanceof ValueNode) {
				((ValueNode) this.processedNode).getValue();
			} else if (this.processedNode instanceof BooleanNode) {
				((BooleanNode) this.processedNode).getBool();
			} else if (this.processedNode instanceof VoidNode) {
				((VoidNode) this.processedNode).startup();
			}
			this.processe++;
		} while (this.processe < this.childNode.size());
	}

}

class SetqNode extends VoidNode {
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

	public void startup() {
		this.idNode.setValue(this.valueNode.getValue());
	}
}

class IfNode extends VoidNode {
	private BoolNode boolNode;
	private Node trueNode, falseNode;

	public boolean addNode(Node node) {
		if (this.boolNode == null) {
			this.boolNode = (BoolNode) node;
			node.setParentNode(this);
			return true;
		} else if (this.trueNode == null) {
			this.trueNode = node;
			node.setParentNode(this);
			return true;
		} else if (this.falseNode == null) {
			this.falseNode = node;
			node.setParentNode(this);
			return true;
		} else {
			return false;
		}
	}

	public void startup() {
		if (this.boolNode.getBool() == true) {
			if (this.trueNode instanceof ValueNode) {
				((ValueNode) this.trueNode).getValue();
			} else if (this.trueNode instanceof BooleanNode) {
				((BooleanNode) this.trueNode).getBool();
			} else if (this.trueNode instanceof VoidNode) {
				((VoidNode) this.trueNode).startup();
			}
		} else {
			if (this.falseNode instanceof ValueNode) {
				((ValueNode) this.falseNode).getValue();
			} else if (this.falseNode instanceof BooleanNode) {
				((BooleanNode) this.falseNode).getBool();
			} else if (this.falseNode instanceof VoidNode) {
				((VoidNode) this.falseNode).startup();
			}
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

class FunctionNode extends VoidNode {
	private ArrayList<IdNode> argNode;
	private Node substanceNode;
	private boolean closeCheck;

	public FunctionNode() {
		this.closeCheck = false;
	}

	public boolean addNode(Node node) {
		if (this.closeCheck == false) {
			this.argNode.add((IdNode) node);
			this.argNode.get(this.argNode.size() - 1).setParentNode(this);
			return true;
		} else if (this.substanceNode == null) {
			this.substanceNode = node;
			node.setParentNode(this);
			return true;
		} else {
			return false;
		}
	}

	public void close() {
		this.closeCheck = true;
	}

	public void startup() {
		if (this.substanceNode instanceof ValueNode) {
			((ValueNode) this.substanceNode).getValue();
		} else if (this.substanceNode instanceof BooleanNode) {
			((BooleanNode) this.substanceNode).getBool();
		} else if (this.substanceNode instanceof VoidNode) {
			((VoidNode) this.substanceNode).startup();
		}
	}
}