package lisp;

import java.util.ArrayList;

public class IdList {
	private ArrayList<String> idName;
	private ArrayList<IdNode> idNode;
	private ArrayList<String> functionName;
	private ArrayList<FunctionNode> functionNode;

	public IdNode getIdNode(int idNodeNumber) {
		return this.idNode.get(idNodeNumber);
	}

	public void setIdNode() {
		this.idNode.add(new IdNode());
	}

	public int getIdNumber(String idName) {
		return this.idName.indexOf(idName);
	}

	public int setAndGetNewIdNumber(String idName) {
		this.idName.add(idName);
		return (this.idName.size() - 1);
	}

	public FunctionNode getFunctionNode(int functionNodeNumber) {
		return this.functionNode.get(functionNodeNumber);
	}

	public void setFunctionNode() {
		this.functionNode.add(new FunctionNode());
	}

	public int getFunctionNumber(String functionName) {
		return this.functionName.indexOf(functionName);
	}

	public int setAndGetNewFunctionNumber(String functionName) {
		this.functionName.add(functionName);
		return (this.functionName.size() - 1);
	}
}
