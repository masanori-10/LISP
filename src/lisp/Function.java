package lisp;

public class Function{
	private int functionNumber;
	private String[] name;
	private Node[][] argument;
	private Tree[] substance;
	public Function(){
		this.functionNumber = 0;
		this.name = new String[10];
		this.argument = new Node[10][10];
		this.substance = new Tree[10];
	}

	public void setFunction(String name,Node[] argument,Tree substance){
		this.name[functionNumber] = name;
		this.argument[functionNumber] = argument;
		this.substance[functionNumber] = substance;
		this.functionNumber++;
	}
	public Tree getFunction(String name,double[] argumentValue) throws SyntaxException{
		for(int functionNumber=0;this.name[functionNumber] != null;functionNumber++){
			if(this.name[functionNumber].equals(name)){
				for(int i=0;this.argument[functionNumber][i] != null;i++){
					this.argument[functionNumber][i].setValue(argumentValue[i]);
				}
				return this.substance[functionNumber];
			}
		}
		throw new SyntaxException();
	}

	public double[] getArgument(String name) throws SyntaxException{
		double[] argumentValue = new double[10];
		for(int functionNumber=0;this.name[functionNumber] != null;functionNumber++){
			if(this.name[functionNumber].equals(name)){
				for(int i=0;this.argument[functionNumber][i] != null;i++){
					argumentValue[i] = this.argument[functionNumber][i].getValue();
				}
				return argumentValue;
			}
		}
		throw new SyntaxException();
	}
	public void setArgument(String name,double[] argumentValue) throws SyntaxException{
		for(int functionNumber=0;this.name[functionNumber] != null;functionNumber++){
			if(this.name[functionNumber].equals(name)){
				for(int i=0;this.argument[functionNumber][i] != null;i++){
					this.argument[functionNumber][i].setValue(argumentValue[i]);
				}
				return;
			}
		}
		throw new SyntaxException();
	}
}
