package lisp;

public class Enum {
	public enum Label {
		EOTRUE, EOFALSE, SOIF;
	}

	public enum Attribute {
		NUMBER, BOOL, KEY, NULL;
	}

	public enum Token {
		T("T"), NIL("Nil"), SETQ("setq"), IF("if"), DEFUN("defun"), PLUS("+"), MINUS(
				"-"), MULT("*"), DIVIDE("/"), LESSEQUAL("<="), GREATEREQUAL(
				">="), NOTEQUAL("!="), EQUAL("="), LESS("<"), GREATER(">"), OPEN(
				"("), CLOSE(")"), PUSHNUMBER, PUSHBOOL, PUSHKEY, FUNCTION, SETARG, RESETARG, JUMP, PUSHNULL, EOF, LABEL;
		private String name;

		private Token() {
			this.name = null;
		}

		private Token(String name) {
			this.name = name;
		}

		public String getName() {
			return this.name();
		}

		public String toString() {
			return this.name;
		}

		public static Token getEnum(String str) {
			Token[] enumArray = Token.values();
			for (Token enumStr : enumArray) {
				if (enumStr.name == null) {
					return null;
				}
				if (str.equals(enumStr.name.toString())) {
					return enumStr;
				}
			}
			return null;
		}
	}
}
