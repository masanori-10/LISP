package lisp;

import java.util.ArrayList;

public class Stack {
	private ArrayList<Value> stack;

	public Stack() {
		this.stack = new ArrayList<Value>();
	}

	public void push(double value) {
		this.stack.add(new Value(value));
	}

	public void push(boolean value) {
		this.stack.add(new Value(value));
	}

	public double popNumber() {
		double ret;
		ret = this.stack.get(this.stack.size() - 1).getNumber();
		this.stack.remove(this.stack.size() - 1);
		return ret;
	}

	public double popNumber2nd() {
		double ret;
		ret = this.stack.get(this.stack.size() - 2).getNumber();
		this.stack.remove(this.stack.size() - 2);
		return ret;
	}

	public boolean popBool() {
		boolean ret;
		ret = this.stack.get(this.stack.size() - 1).getBool();
		this.stack.remove(this.stack.size() - 1);
		return ret;
	}

	public boolean popBool2nd() {
		boolean ret;
		ret = this.stack.get(this.stack.size() - 2).getBool();
		this.stack.remove(this.stack.size() - 2);
		return ret;
	}
}

class Value {
	private double number;
	private boolean bool;

	public Value(double number) {
		this.number = number;
	}

	public Value(boolean bool) {
		this.bool = bool;
	}

	public double getNumber() {
		return this.number;
	}

	public boolean getBool() {
		return this.bool;
	}
}
