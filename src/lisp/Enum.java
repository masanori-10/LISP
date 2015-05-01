package lisp;

public class Enum {
	public enum ReturnChecker {
		NULL, VOID, VALUE, BOOL, SETQ
	}

	public enum Token {
		T("T"), NIL("Nil"), SETQ("setq"), IF("if"), DEFUN("defun"), PLUS("+"), MINUS(
				"-"), MULT("*"), DIVIDE("/"), LESSEQUAL("<="), GREATEREQUAL(
				">="), NOTEQUAL("!="), EQUAL("="), LESS("<"), GREATER(">"), OPEN(
				"("), CLOSE(")");
		private String name;

		public String getName() {
			return this.name();
		}

		private Token(String name) {
			this.name = name;
		}

		public String toString() {
			return this.name;
		}

		public static Token getEnum(String str) {
			Token[] enumArray = Token.values();
			for (Token enumStr : enumArray) {
				if (str.equals(enumStr.name.toString())) {
					return enumStr;
				}
			}
			return null;
		}
	}
}
