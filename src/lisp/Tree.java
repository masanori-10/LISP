package lisp;

import java.util.ArrayList;

public class Tree {
	private Token token;
	private Tree parent;
	private ArrayList<Tree> child;

	public Tree(Token token) {
		this();
		this.token = token;
	}

	public Tree() {
		this.child = new ArrayList<Tree>();
	}

	public Token getToken() {
		return this.token;
	}

	public Tree getParent() {
		return this.parent;
	}

	public Tree getChild(int i) {
		return this.child.get(i);
	}

	public void setToken(Token token) {
		this.token = token;
	}

	public void setParent(Tree parent) {
		this.parent = parent;
	}

	public void setChild(Tree child, int i) {
		this.child.set(i, child);
	}
}

class TreeNode {
	private Tree processedNode, rootNode;

	public TreeNode() {
		this.processedNode = new Tree();
		this.rootNode = this.processedNode;
	}

	public void moveToLastChild() throws SyntaxException {
		if (this.processedNode.getChild(0) == null) {
			throw new SyntaxException();
		}
		for (int i = 1; true; i++) {
			if (this.processedNode.getChild(i) == null) {
				this.processedNode = this.processedNode.getChild(i - 1);
			}
		}
	}

	public void add(Token token) {
		for (int i = 0; true; i++) {
			if (this.processedNode.getChild(i) == null) {
				this.processedNode.setChild(new Tree(token), i);
				this.processedNode.getChild(i).setParent(this.processedNode);
				return;
			}
		}
	}

	public void moveToParant() throws SyntaxException {
		if (this.processedNode == this.rootNode) {
			throw new SyntaxException();
		}
		this.processedNode = this.processedNode.getParent();
	}

	public Tree getProcessedNode() {
		return this.processedNode;
	}

	public Tree getRootNode() {
		return this.rootNode;
	}

	public void setProcessedNode(Tree processedNode) {
		this.processedNode = processedNode;
	}
}