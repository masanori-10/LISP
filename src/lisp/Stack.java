package lisp;

import java.util.ArrayList;

import lisp.Enum.Attribute;

public class Stack {
	private ArrayList<Double> value;
	private ArrayList<Attribute> attribute;
	private ArrayList<String> key;

	public Stack() {
		this.value = new ArrayList<Double>();
		this.attribute = new ArrayList<Attribute>();
		this.key = new ArrayList<String>();
	}

	public void push(double value) {
		this.value.add(value);
		this.attribute.add(Attribute.NUMBER);
		this.key.add(null);
	}

	public void push(boolean value) {
		this.attribute.add(Attribute.BOOL);
		this.key.add(null);
		if (value) {
			this.value.add(0.0);
		} else {
			this.value.add(1.0);
		}
	}

	public void push(String key) {
		this.key.add(key);
		this.value.add(0.0);
		this.attribute.add(Attribute.KEY);
	}

	public double pop() {
		double ret;
		ret = this.value.get(this.value.size() - 1);
		this.value.remove(this.value.size() - 1);
		this.attribute.remove(this.attribute.size() - 1);
		this.key.remove(this.key.size() - 1);
		return ret;
	}

	public double pop2nd() {
		double ret;
		ret = this.value.get(this.value.size() - 2);
		this.value.remove(this.value.size() - 2);
		this.attribute.remove(this.attribute.size() - 2);
		this.key.remove(this.key.size() - 2);
		return ret;
	}

	public String popKey() {
		String ret;
		ret = this.key.get(this.key.size() - 1);
		this.value.remove(this.value.size() - 1);
		this.attribute.remove(this.attribute.size() - 1);
		this.key.remove(this.key.size() - 1);
		return ret;
	}

	public String popKey2nd() {
		String ret;
		ret = this.key.get(this.key.size() - 2);
		this.value.remove(this.value.size() - 2);
		this.attribute.remove(this.attribute.size() - 2);
		this.key.remove(this.key.size() - 2);
		return ret;
	}

	public double getValue() {
		double ret;
		ret = this.value.get(0);
		this.value.remove(0);
		this.attribute.remove(0);
		this.key.remove(0);
		return ret;
	}

	public Attribute getAttribute() {
		if (this.attribute.isEmpty()) {
			return Attribute.NULL;
		}
		return this.attribute.get(0);
	}

	public Attribute readAttribute() {
		return this.attribute.get(this.attribute.size() - 1);
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

	public boolean getBool() {
		return this.bool;
	}

	public String getKey() {
		return this.key;
	}

	public boolean isNumber() {
		return this.isNumber;
	}
}
