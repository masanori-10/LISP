package lisp;

import lisp.Enum.Token;

public abstract class ValueNode extends Node {
}

class NumberNode extends ValueNode {
	private double value;

	public NumberNode(double value) {
		this.value = value;
	}

	public void makeCommand(CommandLine commandLine) {
		commandLine.addCommand(new Command(this.value));
	}
}

class VariableNode extends ValueNode {
	private String key;
	private boolean isSet;

	public VariableNode(String key) {
		this.key = key;
		this.isSet = false;
	}

	public VariableNode(String key, boolean isSet) {
		this(key);
		this.isSet = true;
	}

	public void makeCommand(CommandLine commandLine) {
		if (this.isSet) {
			this.makeCommandKey(commandLine);
		} else {
			commandLine.addCommand(new Command(this.key));
		}
	}

	public void makeCommandKey(CommandLine commandLine) {
		commandLine.addCommand(new Command(this.key, true));
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

	public void makeCommand(CommandLine commandLine) {
		this.rightNode.makeCommand(commandLine);
		this.leftNode.makeCommand(commandLine);
		commandLine.addCommand(new Command(this.token));
	}
}