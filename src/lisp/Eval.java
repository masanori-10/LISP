package lisp;

import lisp.Enum.ReturnChecker;

public class Eval {
	private ReturnChecker returnChecker;

	public void evaluate(Tree tree) throws SyntaxException {
		while (true) {
			this.returnChecker = ((VariableNode) tree.getRootNode()).check();
			switch (this.returnChecker) {
			case NULL:
				return;
			case VOID:
				break;
			case VALUE:
				System.out.println(tree.getRootNode().getValue());
				break;
			case BOOL:
				if (tree.getRootNode().getBool()) {
					System.out.println("T");
				} else {
					System.out.println("Nil");
				}
				break;
			case SETQ:
				tree.getRootNode().setq();
			}
		}
	}
}
