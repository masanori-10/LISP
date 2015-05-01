package lisp;

import java.util.ArrayList;

import lisp.Enum.ReturnChecker;

public abstract class VariableNode extends Node {
	public abstract ReturnChecker check() throws SyntaxException;
}

class DummyNode extends VariableNode {
	private ArrayList<Node> childNode;
	private int childLimit;
	private int processe;
	private Node processedNode;
	private boolean isRoot;

	public DummyNode() {
		this.childNode = new ArrayList<Node>();
		this.childLimit = 0;
		this.processe = 0;
		this.isRoot = false;
	}

	public DummyNode(boolean bool) {
		this();
		this.isRoot = true;
	}

	public DummyNode(int childLimit) {
		this();
		this.childLimit = childLimit;
	}

	public boolean addNode(Node node) {
		if ((this.childNode.size() < this.childLimit) || (this.childLimit <= 0)) {
			this.childNode.add(node);
			if (node == null) {
				this.childNode.remove(this.childNode.size() - 1);
			} else {
				node.setParentNode(this);
			}
			return true;
		} else {
			return false;
		}
	}

	public ArrayList<Node> getChildNode() {
		return this.childNode;
	}

	public void resetProcesse() {
		this.processe = 0;
	}

	public ReturnChecker check() throws SyntaxException {
		if (this.processe >= this.childNode.size()) {
			return ReturnChecker.NULL;
		} else {
			this.processedNode = this.childNode.get(processe);
			if (this.processedNode instanceof DefunNode) {
				if (this.isRoot) {
					this.processe++;
				}
				return ReturnChecker.VOID;
			} else if (this.processedNode instanceof ValueNode) {
				return ReturnChecker.VALUE;
			} else if (this.processedNode instanceof BooleanNode) {
				return ReturnChecker.BOOL;
			} else if (this.processedNode instanceof SetqNode) {
				return ReturnChecker.SETQ;
			} else {
				return ((VariableNode) this.processedNode).check();
			}
		}

	}

	public double getValue() throws SyntaxException {
		double ret;
		this.processedNode = this.childNode.get(processe);
		ret = this.processedNode.getValue();
		if (this.isRoot) {
			this.processe++;
		}
		return ret;
	}

	public double getValue(int i) throws SyntaxException {
		return this.childNode.get(i).getValue();
	}

	public boolean getBool() throws SyntaxException {
		boolean ret;
		this.processedNode = this.childNode.get(processe);
		ret = this.processedNode.getBool();
		if (this.isRoot) {
			this.processe++;
		}
		return ret;
	}

	public void setq() throws SyntaxException {
		if (this.isRoot) {
			this.processedNode = this.childNode.get(processe);
			this.processedNode.setq();
			this.processe++;
		} else {
			do {
				this.processedNode = this.childNode.get(processe);
				this.processedNode.setq();
				this.processe++;
			} while (this.processe < this.childNode.size());
			this.resetProcesse();
		}
	}

}

class IfNode extends VariableNode {
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

	public double getValue() throws SyntaxException {
		if (this.boolNode.getBool() == true) {
			return this.trueNode.getValue();
		} else {
			return this.falseNode.getValue();
		}
	}

	public boolean getBool() throws SyntaxException {
		if (this.boolNode.getBool() == true) {
			return this.trueNode.getBool();
		} else {
			return this.falseNode.getBool();
		}
	}

	public void setq() throws SyntaxException {
		if (this.boolNode.getBool() == true) {
			this.trueNode.setq();
		} else {
			this.trueNode.setq();
		}
	}

	public ReturnChecker check() throws SyntaxException {
		if (this.trueNode instanceof DefunNode) {
			return ReturnChecker.VOID;
		} else if (this.trueNode instanceof ValueNode) {
			return ReturnChecker.VALUE;
		} else if (this.trueNode instanceof BooleanNode) {
			return ReturnChecker.BOOL;
		} else if (this.trueNode instanceof SetqNode) {
			return ReturnChecker.SETQ;
		} else {
			return ((VariableNode) this.trueNode).check();
		}
	}
}

class FunctionNode extends VariableNode {
	private Node dummyArgNode, substanceNode, actualArgNode;
	private int functionNumber;
	private int[] argCount;
	private ArrayList<ArrayList<Double>> stock;

	public FunctionNode(int functionNumber) {
		this.functionNumber = functionNumber;
		this.argCount = new int[1];
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

	public void adjustFunction(FunctionNode functionNode) {
		this.dummyArgNode = functionNode.dummyArgNode;
		this.substanceNode = functionNode.substanceNode;
		this.argCount = functionNode.argCount;
		this.stock = functionNode.stock;
	}

	public void addArgCount() {
		this.argCount[0]++;
		this.stock.add(new ArrayList<Double>());
	}

	public int[] getArgCount() {
		return this.argCount;
	}

	public int getFunctionNumber() {
		return this.functionNumber;
	}

	public double getValue() throws SyntaxException {
		double ret;
		this.setArgument();
		ret = this.substanceNode.getValue();
		this.clearArgument();
		return ret;
	}

	public boolean getBool() throws SyntaxException {
		boolean ret;
		this.setArgument();
		ret = this.substanceNode.getBool();
		this.clearArgument();
		return ret;
	}

	public void setq() throws SyntaxException {
		this.setArgument();
		this.substanceNode.setq();
		this.clearArgument();
	}

	public void setArgument() throws SyntaxException {
		if (this.dummyArgNode instanceof DummyNode) {
			for (int i = 0; i < this.argCount[0]; i++) {
				this.stock.get(i).add(
						((DummyNode) this.actualArgNode).getValue(i));
			}
			for (int i = 0; i < this.argCount[0]; i++) {
				((IdInFunctionNode) ((DummyNode) this.dummyArgNode)
						.getChildNode().get(i)).addValue(this.stock.get(i).get(
						(this.stock.get(i)).size() - 1));
				this.stock.get(i).remove((this.stock.get(i)).size() - 1);
			}
		} else {
			((IdInFunctionNode) this.dummyArgNode)
					.addValue(((DummyNode) this.actualArgNode).getValue(0));
		}
	}

	public void clearArgument() {
		if (this.dummyArgNode instanceof DummyNode) {
			for (int i = 0; i < ((DummyNode) this.dummyArgNode).getChildNode()
					.size(); i++) {
				((IdInFunctionNode) ((DummyNode) this.dummyArgNode)
						.getChildNode().get(i)).removeLastValue();
			}
		} else {
			((IdInFunctionNode) this.dummyArgNode).removeLastValue();
		}
	}

	public ReturnChecker check() throws SyntaxException {
		if (this.substanceNode instanceof DefunNode) {
			return ReturnChecker.VOID;
		} else if (this.substanceNode instanceof ValueNode) {
			return ReturnChecker.VALUE;
		} else if (this.substanceNode instanceof BooleanNode) {
			return ReturnChecker.BOOL;
		} else if (this.substanceNode instanceof SetqNode) {
			return ReturnChecker.SETQ;
		} else {
			return ((VariableNode) this.substanceNode).check();
		}
	}

}