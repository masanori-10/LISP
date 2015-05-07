package lisp;

import java.util.ArrayList;

import lisp.Enum.Token;

public abstract class OtherNode extends Node {
}

class DummyNode extends OtherNode {
	private ArrayList<Node> childNode;

	public DummyNode() {
		this.childNode = new ArrayList<Node>();
	}

	public boolean addNode(Node node) {
		this.childNode.add(node);
		if (node == null) {
			this.childNode.remove(this.childNode.size() - 1);
		} else {
			node.setParentNode(this);
		}
		return true;
	}

	public void commandize(CommandLine commandLine) {
		for (int i = 0; i < this.childNode.size() - 1; i++) {
			this.childNode.get(i).commandize(commandLine);
		}
		commandLine.addCommand(new Command(Token.DUMMY));
	}
}

class IfNode extends OtherNode {
	private Node boolNode;
	private Node trueNode, falseNode;
	private boolean needTheDummy;

	public IfNode() {
		this.needTheDummy = false;
	}

	public boolean addNode(Node node) {
		if (this.boolNode == null) {
			this.boolNode = node;
			if (!(node == null)) {
				node.setParentNode(this);
			}
			this.needTheDummy = true;
			return true;
		} else if (this.trueNode == null) {
			this.trueNode = node;
			if (!(node == null)) {
				node.setParentNode(this);
			}
			return true;
		} else if (this.falseNode == null) {
			this.falseNode = node;
			if (!(node == null)) {
				node.setParentNode(this);
			}
			return true;
		} else {
			return false;
		}
	}

	public boolean needTheDummy() {
		return this.needTheDummy;
	}

	public void commandize(CommandLine commandLine) {
		this.boolNode.commandize(commandLine);
		commandLine.addCommand(new Command(Token.IF));
		this.trueNode.commandize(commandLine);
		this.falseNode.commandize(commandLine);
	}
}

class FunctionNode extends OtherNode {
	private Node dummyArgNode, substanceNode, actualArgNode;
	private String key;
	private ArrayList<ArrayList<Double>> stock;

	public FunctionNode(String key) {
		this.key = key;
		this.stock = new ArrayList<ArrayList<Double>>();
	}

	public boolean addNode(Node node) {
		if (this.getParentNode() instanceof DefunNode) {
			if (this.dummyArgNode == null) {
				this.dummyArgNode = node;
				if (!(node == null)) {
					node.setParentNode(this);
				}
				return true;
			} else if (this.substanceNode == null) {
				this.substanceNode = node;
				if (!(node == null)) {
					node.setParentNode(this);
				}
				return true;
			} else {
				MapForFunction.getFunction(this.key).setCommandLine(
						this.dummyArgNode, this.substanceNode);
			}
		} else {
			if (this.actualArgNode == null) {
				this.actualArgNode = node;
				if (!(node == null)) {
					node.setParentNode(this);
				}
				return true;
			}
		}
		return false;
	}

	public String getKey() {
		return this.key;
	}

	public void commandize(CommandLine commandLine) {
		this.actualArgNode.commandize(commandLine);
		commandLine.addCommand(new Command(this.key));
	}

	public void setArgument() throws SyntaxException {
		if (this.dummyArgNode instanceof DummyNode) {
			for (int i = 0; i < this.argCount[0]; i++) {
				this.stock.get(i).add(
						((DummyNode) this.actualArgNode).getValue(i));
			}
			for (int i = 0; i < this.argCount[0]; i++) {
				((ArgumentNode) ((DummyNode) this.dummyArgNode).getChildNode()
						.get(i)).addValue(this.stock.get(i).get(
						(this.stock.get(i)).size() - 1));
				this.stock.get(i).remove((this.stock.get(i)).size() - 1);
			}
		} else {
			((ArgumentNode) this.dummyArgNode)
					.addValue(((DummyNode) this.actualArgNode).getValue(0));
		}
	}

	public void clearArgument() {
		if (this.dummyArgNode instanceof DummyNode) {
			for (int i = 0; i < ((DummyNode) this.dummyArgNode).getChildNode()
					.size(); i++) {
				((ArgumentNode) ((DummyNode) this.dummyArgNode).getChildNode()
						.get(i)).removeLastValue();
			}
		} else {
			((ArgumentNode) this.dummyArgNode).removeLastValue();
		}
	}
}