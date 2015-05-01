package lisp;

class SyntaxException extends Exception {
	public SyntaxException() {
		super("Syntax error.");
	}
}

class ComandLineArgumentException extends Exception {
	public ComandLineArgumentException() {
		super("Comand line argument error.");
	}
}

class FileReadException extends Exception {
	public FileReadException() {
		super("File read error.");
	}
}
