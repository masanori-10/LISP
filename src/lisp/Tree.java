package lisp;


public class Tree {
	private Tree processedNode, rootNode;

	public Tree() {
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

	public void add(Node token) {
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