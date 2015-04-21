package lisp;

public class Token{
	private String attribute,name;
	private int argumentCounter;
	private double value;
	public Token(String attribute){
		this.attribute = attribute;
	}
	public Token(String attribute,String name){
		this(attribute);
		this.name = name;
	}
	public Token(String attribute,double value){
		this(attribute);
		this.value = value;
	}
	public Token(String attribute,String name,int argumentCounter){
		this(attribute, name);
		this.argumentCounter = argumentCounter;
	}

	public String getAttribute(){
		return this.attribute;
	}
	public String getName(){
		return this.name;
	}
	public int getArgumentCounter(){
		return this.argumentCounter;
	}
	public double getValue(){
		return this.value;
	}
	public void setAttribute(String attribute){
		this.attribute = attribute;
	}
	public void setName(String name){
		this.name = name;
	}
	public void setArgumentCounter(int argumentCounter){
		this.argumentCounter = argumentCounter;
	}
	public void setValue(double value){
		this.value = value;
	}
}
