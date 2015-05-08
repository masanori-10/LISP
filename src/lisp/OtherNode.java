package lisp;

import java.util.ArrayList;

import lisp.Enum.Label;
import lisp.Enum.Token;

public abstract class OtherNode extends Node {
}

class DummyNode extends OtherNode {
	private ArrayList<Node> childNode;
	private int index;

	public DummyNode() {
		this.childNode = new ArrayList<Node>();
		this.index = 0;
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

	public void makeCommand(CommandLine commandLine) {
		while (index < this.childNode.size()) {
			this.childNode.get(index).makeCommand(commandLine);
			index++;
		}
	}

	public int getChildNodeCount() {
		return this.childNode.size();
	}
}

class IfNode extends OtherNode {
	private Node boolNode;
	private Node trueNode, falseNode;

	public boolean addNode(Node node) {
		if (this.boolNode == null) {
			this.boolNode = node;
			if (!(node == null)) {
				node.setParentNode(this);
			}
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

	public void makeCommand(CommandLine commandLine) {
		this.boolNode.makeCommand(commandLine);
		commandLine.addCommand(new Command(Token.IF, Label.SOIF));
		this.trueNode.makeCommand(commandLine);
		commandLine.addCommand(new Command(Token.JUMP, Label.EOTRUE));
		this.falseNode.makeCommand(commandLine);
		commandLine.addCommand(new Command(Label.EOFALSE));
	}
}

class FunctionNode extends OtherNode {
	private Node dummyArgNode, substanceNode;
	private ArrayList<Node> actualArgNode;
	private int maxArg;
	private String key;
	private boolean isSeted;

	public FunctionNode(String key) {
		this.key = key;
		this.isSeted = false;
		if (MapForFunction.existFunction(key)) {
			this.actualArgNode = new ArrayList<Node>();
			this.maxArg = MapForFunction.getFunction(key).getMaxArg();
		}
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
				if (this.dummyArgNode instanceof DummyNode) {
					MapForFunction.getFunction(this.key)
							.setMaxArg(
									((DummyNode) this.dummyArgNode)
											.getChildNodeCount());
				} else {
					MapForFunction.getFunction(this.key).setMaxArg(1);
				}
				this.substanceNode = node;
				if (!(node == null)) {
					node.setParentNode(this);
				}
				return true;
			} else if (!this.isSeted) {
				MapForFunction.getFunction(this.key).setCommandLine(
						this.dummyArgNode, this.substanceNode);
				this.isSeted = true;
			}
		} else {
			if (this.actualArgNode.size() < this.maxArg) {
				this.actualArgNode.add(node);

				if (node == null) {
					this.actualArgNode.remove(this.actualArgNode.size() - 1);
				} else {
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

	public void makeCommand(CommandLine commandLine) {
		for (int i = 0; i < this.maxArg; i++) {
			this.actualArgNode.get(i).makeCommand(commandLine);
		}
		commandLine.addCommand(new Command(this.key));
	}
}