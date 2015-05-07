package lisp;

import lisp.Enum.Token;

public abstract class ValueNode extends Node {
}

class NumberNode extends ValueNode {
	private double value;

	public NumberNode(double value) {
		this.value = value;
	}

	public void commandize(CommandLine commandLine) {
		commandLine.addCommand(new Command(this.value));
	}
}

class VariableNode extends ValueNode {
	private String key;

	public VariableNode(String key) {
		this.key = key;
	}

	public void commandize(CommandLine commandLine) {
		commandLine.addCommand(new Command(this.key));
	}
}

class ArgumentNode extends ValueNode {
	private String key;

	public ArgumentNode(String key) {
		this.key = key;
	}

	public void commandize(CommandLine commandLine) {
		commandLine.addCommand(new Command(this.key));
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

	public void commandize(CommandLine commandLine) {
		this.rightNode.commandize(commandLine);
		this.leftNode.commandize(commandLine);
		commandLine.addCommand(new Command(this.token));
	}
}