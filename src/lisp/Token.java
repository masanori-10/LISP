package lisp;

public class Token {
	private String attribute, name;
	private double value;

	public Token() {
	}

	public Token(String attribute) {
		this.attribute = attribute;
	}

	public Token(String attribute, String name) {
		this(attribute);
		this.name = name;
	}

	public Token(String attribute, double value) {
		this(attribute);
		this.value = value;
	}

	public String getAttribute() {
		return this.attribute;
	}

	public String getName() {
		return this.name;
	}

	public double getValue() {
		return this.value;
	}

	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setValue(double value) {
		this.value = value;
	}
}
