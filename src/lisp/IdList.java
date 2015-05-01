package lisp;

import java.util.ArrayList;

public class IdList {
	private ArrayList<String> idName;
	private ArrayList<Double[]> idValue;
	private ArrayList<String> functionName;
	private ArrayList<FunctionNode> functionNode;
	private ArrayList<ArrayList<String>> idInFunctionName;
	private ArrayList<ArrayList<ArrayList<Double>>> idInFunctionValue;

	public IdList() {
		this.idName = new ArrayList<String>();
		this.idValue = new ArrayList<Double[]>();
		this.functionName = new ArrayList<String>();
		this.functionNode = new ArrayList<FunctionNode>();
		this.idInFunctionName = new ArrayList<ArrayList<String>>();
		this.idInFunctionValue = new ArrayList<ArrayList<ArrayList<Double>>>();
	}

	public Double[] getIdValue(String idName) {
		if (this.idName.indexOf(idName) == -1) {
			this.idName.add(idName);
			this.idValue.add(new Double[1]);
		}
		return this.idValue.get(this.idName.indexOf(idName));
	}

	public FunctionNode addFunctionNode(String functionName) {
		if (this.functionName.indexOf(functionName) == -1) {
			this.functionName.add(functionName);
			this.functionNode.add(new FunctionNode(this.functionName
					.indexOf(functionName)));
			this.idInFunctionName.add(new ArrayList<String>());
			this.idInFunctionValue.add(new ArrayList<ArrayList<Double>>());
		}
		return this.functionNode.get(this.functionName.indexOf(functionName));
	}

	public boolean checkFunctionName(String functionName) {
		return this.functionName.contains(functionName);
	}

	public FunctionNode getFunctionNode(String functionName) {
		FunctionNode ret = new FunctionNode(
				this.functionName.indexOf(functionName));
		ret.adjustFunction(this.functionNode.get(this.functionName
				.indexOf(functionName)));
		return ret;
	}

	public ArrayList<Double> getIdInFunctionValue(FunctionNode functionNode,
			String idInFunctionName) {
		if (this.idInFunctionName.get(functionNode.getFunctionNumber())
				.indexOf(idInFunctionName) == -1) {
			this.idInFunctionName.get(functionNode.getFunctionNumber()).add(
					idInFunctionName);
			this.idInFunctionValue.get(functionNode.getFunctionNumber()).add(
					new ArrayList<Double>());
			functionNode.addArgCount();
		}
		return this.idInFunctionValue.get(functionNode.getFunctionNumber())
				.get(this.idInFunctionName
						.get(functionNode.getFunctionNumber()).indexOf(
								idInFunctionName));
	}
}
