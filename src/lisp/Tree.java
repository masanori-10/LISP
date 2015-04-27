package lisp;

public class Tree {
	private Node processedNode, rootNode;

	public Tree() {
		this.processedNode = new DummyNode();
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

	public void addNode(Node node) throws SyntaxException {
		while (true) {
			if (this.processedNode.addNode(node) == false) {
				this.moveToParant();
			} else {
				return;
			}
		}
	}

	public void moveToParant() throws SyntaxException {
		if (this.processedNode == this.rootNode) {
			throw new SyntaxException();
		}
		this.processedNode = this.processedNode.getParentNode();
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