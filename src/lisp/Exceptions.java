package lisp;

class SyntaxException extends Exception{
	public SyntaxException(){
		super("Syntax error.");
	}
}

class OtherException extends Exception{
	public OtherException(){
		super("OtherException.");
	}
}
