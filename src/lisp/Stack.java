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
}

class Value {
	private double number;
	private boolean bool;
	private String key;

	public Value(double number) {
		this.number = number;
	}

	public Value(boolean bool) {
		this.bool = bool;
	}

	public Value(String key) {
		this.key = key;
	}

	public double getNumber() {
		if (this.key != null) {
			return IdList.getVariable(key);
		}
		return this.number;
	}

	public void setVariable(double value) {
		IdList.setVariable(key, value);
	}

	public boolean getBool() {
		return this.bool;
	}
}
