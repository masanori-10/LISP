package lisp;

import lisp.Enum.Token;

public abstract class Node {
	private Node parentNode;

	public Node() {
	}

	public void commandize(CommandLine commandLine) {

	}

	public boolean addNode(Node node) {
		return false;
	}

	public Node getParentNode() {
		return this.parentNode;
	}

	public void setParentNode(Node parentNode) {
		this.parentNode = parentNode;
	}
}

class SetqNode extends Node {
	private VariableNode variableNode;
	private Node valueNode;

	public boolean addNode(Node node) {
		if (this.variableNode == null) {
			this.variableNode = (VariableNode) node;
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

	public void commandize(CommandLine commandLine) {
		this.variableNode.commandize(commandLine);
		this.valueNode.commandize(commandLine);
		commandLine.addCommand(new Command(Token.SETQ));
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
