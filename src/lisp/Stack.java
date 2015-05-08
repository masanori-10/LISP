package lisp;

import java.util.ArrayList;

public class Stack {
	private ArrayList<Value> stack;

	public Stack() {
		this.stack = new ArrayList<Value>();
	}

	public void push(Value value) {
		this.stack.add(value);
	}

	public Value pop() {
		Value ret;
		ret = this.stack.get(this.stack.size() - 1);
		this.stack.remove(this.stack.size() - 1);
		return ret;
	}

	public Value pop2nd() {
		Value ret;
		ret = this.stack.get(this.stack.size() - 2);
		this.stack.remove(this.stack.size() - 2);
		return ret;
	}

	public Value getValue() {
		Value ret;
		if (this.stack.isEmpty()) {
			return null;
		}
		ret = this.stack.get(0);
		this.stack.remove(0);
		return ret;
	}
}

class Value {
	private double number;
	private boolean bool;
	private String key;
	private boolean isNumber;

	public Value(double number) {
		this.number = number;
		this.isNumber = true;
	}

	public Value(boolean bool) {
		this.bool = bool;
		this.isNumber = false;
	}

	public Value(String key) {
		this.key = key;
		this.isNumber = true;
	}

	public double getNumber() {
		if (this.key != null) {
			if (MapForArgument.existArgument(this.key)) {
				return MapForArgument.getArgument(this.key).get(
						MapForArgument.getArgument(this.key).size() - 1);
			}
			return MapForVariable.getVariable(this.key);
		}
		return this.number;
	}

	public void setVariable(double value) {
		if (MapForArgument.existArgument(this.key)) {
			MapForArgument.getArgument(this.key).add(value);
		}
		MapForVariable.setVariable(this.key, value);
	}

	public void resetVariable() {
		MapForArgument.getArgument(this.key).remove(
				MapForArgument.getArgument(this.key).size() - 1);
	}

	public boolean getBool() {
		return this.bool;
	}

	public boolean isNumber() {
		return this.isNumber;
	}
}
