package lisp;

public class Tree {
	private Node currentNode, rootNode;

	public Tree() {
		this.currentNode = new DummyNode();
		this.rootNode = this.currentNode;
	}

	public void addAndMoveNode(Node node) throws SyntaxException {
		this.addNode(node);
		this.currentNode = node;
	}

	public void addNode(Node node) throws SyntaxException {
		while (true) {
			if (this.currentNode.addNode(node) == false) {
				this.moveToParant();
			} else {
				return;
			}
		}
	}

	public void moveToParant() throws SyntaxException {
		if (this.currentNode == this.rootNode) {
			throw new SyntaxException();
		}
		this.currentNode = this.currentNode.getParentNode();
	}

	public Node getCurrentNode() {
		return this.currentNode;
	}

	public Node getRootNode() {
		return this.rootNode;
	}
}