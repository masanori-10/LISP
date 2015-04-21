package lisp;


public class Parser{
	private TreeNode treeNode;
	private TreeNode[] completeTree;
	private int parenStack,completeNumber;
	public Parser(){
		this.treeNode = new TreeNode();
		this.completeTree = new TreeNode[10];
		this.parenStack = 0;
		this.completeNumber = 0;
	}

	public void parser(Token[] token,int tokenNumber) throws SyntaxException{
		for(int i=0;i<tokenNumber;i++){
			switch (token[i].getAttribute()) {
			case "paren":
				if(token[i].getName().equals("(")){
					if(parenStack != 0){
						this.treeNode.add(token[i]);
					}
					this.treeNode.open();
					this.parenStack++;
				}else{
					this.treeNode.close();
					this.parenStack--;
					if (parenStack == 0){
						if(this.treeNode.getProcessedNode() != this.treeNode.getRootNode()){
							throw new SyntaxException();
						}
						this.completeTree[this.completeNumber] = this.treeNode;
						this.treeNode = new TreeNode();
						this.completeNumber++;
					}else{
						while(this.treeNode.getParentNode().getToken() != null){
							if(this.treeNode.getParentNode().getToken().getArgumentCounter() == 0){
								this.treeNode.close();
							}else{
								break;
							}
						}
					}
				}
				break;
			case "command":
			case "operator":
			case "comparator":
				this.treeNode.add(token[i]);
				this.treeNode.open();
				break;
			case "number":
			case "id":
				this.treeNode.add(token[i]);
				while(this.treeNode.getParentNode().getToken() != null){
					if(this.treeNode.getParentNode().getToken().getArgumentCounter() == 0){
						this.treeNode.close();
					}else{
						break;
					}
				}
				break;
			}
		}
	}

	public TreeNode getCompleteTree(int i){
		return this.completeTree[i];
	}
	public TreeNode[] getCompleteTree(){
		return this.completeTree;
	}
	public int getCompleteNumber(){
		return this.completeNumber;
	}
}
