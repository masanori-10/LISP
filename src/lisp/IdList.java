package lisp;

import java.util.ArrayList;

public class IdList {
	private ArrayList<String> idName;
	private ArrayList<IdNode> idNode;
	private ArrayList<String> functionName;
	private ArrayList<FunctionNode> functionNode;
	private ArrayList<ArrayList<String>> idInFunctionName;
	private ArrayList<ArrayList<IdNode>> idInFunctionNode;

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

	public IdNode getIdInFunctionNode(int functionNumber,
			int idInFunctionNodeNumber) {
		return this.idInFunctionNode.get(functionNumber).get(
				idInFunctionNodeNumber);
	}

	public void setIdInFunctionNode(int functionNumber) {
		this.idInFunctionNode.get(functionNumber).add(new IdNode());
	}

	public int getIdInFunctionNumber(int functionNumber, String idInFunctionName) {
		return this.idInFunctionName.get(functionNumber).indexOf(
				idInFunctionName);
	}

	public int setAndGetNewIdInFunctionNumber(int functionNumber,
			String idInFunctionName) {
		this.idInFunctionName.get(functionNumber).add(idInFunctionName);
		return (this.idInFunctionName.get(functionNumber).size() - 1);
	}
}
