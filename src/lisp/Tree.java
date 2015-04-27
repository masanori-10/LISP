package lisp;

public class Tree {
	private Node processedNode, rootNode;

	public Tree() {
		this.processedNode = new DummyNode();
		this.rootNode = this.processedNode;
	}

	public void addAndMoveNode(Node node) throws SyntaxException {
		this.addNode(node);
		this.processedNode = node;
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

	public Node getProcessedNode() {
		return this.processedNode;
	}

	public Node getRootNode() {
		return this.rootNode;
	}
}