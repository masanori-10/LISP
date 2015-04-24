package lisp;

public class Tree{
	private Token token;
	private Tree parent;
	private Tree[] child;
	public Tree(Token token){
		this();
		this.token = token;
	}
	public Tree(){
		this.child = new Tree[10];
	}

	public Token getToken(){
		return this.token;
	}
	public Tree getParent(){
		return this.parent;
	}
	public Tree getChild(int i){
		return this.child[i];
	}
	public void setToken(Token token){
		this.token = token;
	}
	public void setParent(Tree parent){
		this.parent = parent;
	}
	public void setChild(Tree child,int i){
		this.child[i] = child;
	}
}

class TreeNode{
	private Tree processedNode,parentNode,rootNode;
	public TreeNode(){
		this.processedNode = new Tree();
		this.rootNode = this.processedNode;
	}

	public void open(){
		this.processedNode.setChild(new Tree(),0);
		this.processedNode.getChild(0).setParent(this.processedNode);
		this.parentNode = this.processedNode;
		this.processedNode = this.parentNode.getChild(0);
	}
	public void add(Token token) throws SyntaxException{
		if(this.processedNode == this.rootNode){
			throw new SyntaxException();
		}
		for(int i=0;true;i++){
			if(this.parentNode.getChild(i) == this.processedNode){
				this.parentNode.setChild(new Tree(token),i+1);
				this.parentNode.getChild(i+1).setParent(this.parentNode);
				this.processedNode = this.parentNode.getChild(i+1);
				if(this.parentNode.getToken() != null){
					this.parentNode.getToken().setArgumentCounter(this.parentNode.getToken().getArgumentCounter()-1);
				}
				return;
			}
		}
	}
	public void close() throws SyntaxException{
		if(this.processedNode == this.rootNode){
			throw new SyntaxException();
		}
		this.processedNode = this.parentNode;
		this.parentNode = this.parentNode.getParent();
	}

	public Tree getProcessedNode(){
		return this.processedNode;
	}
	public Tree getParentNode(){
		return this.parentNode;
	}
	public Tree getRootNode(){
		return this.rootNode;
	}
	public void setProcessedNode(Tree processedNode){
		this.processedNode = processedNode;
	}
}