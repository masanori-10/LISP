package lisp;

public abstract class Node {
	private Node parentNode;

	public Node() {
	}

	public void commandize() {

	}

	public boolean addNode(Node node) {
		return false;
	}

	public double getValue() throws SyntaxException {
		throw new SyntaxException();
	}

	public boolean getBool() throws SyntaxException {
		throw new SyntaxException();
	}

	public void setq() throws SyntaxException {
		throw new SyntaxException();
	}

	public Node getParentNode() {
		return this.parentNode;
	}

	public void setParentNode(Node parentNode) {
		this.parentNode = parentNode;
	}
}

class SetqNode extends Node {
	private IdNode idNode;
	private Node valueNode;

	public boolean addNode(Node node) {
		if (this.idNode == null) {
			this.idNode = (IdNode) node;
			if (!(node == null)) {
				node.setParentNode(this);
			}
			return true;
		} else if (this.valueNode == null) {
			this.valueNode = node;
			if (!(node == null)) {
				node.setParentNode(this);
			}
			return true;
		} else {
			return false;
		}
	}

	public void setq() throws SyntaxException {
		this.idNode.setValue(this.valueNode.getValue());
	}
}

class DefunNode extends Node {
	private FunctionNode functionNode;

	public boolean addNode(Node node) {
		if (this.functionNode == null) {
			this.functionNode = (FunctionNode) node;
			if (!(node == null)) {
				node.setParentNode(this);
			}
			return true;
		} else {
			return false;
		}
	}

	public FunctionNode getFunctionNode() {
		return this.functionNode;
	}
}
