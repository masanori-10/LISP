package lisp;

import lisp.Enum.Token;

public class Eval {
	private CommandLine commandLine;
	private int index;
	private boolean isEOF;

	public Eval() {
		this.commandLine = new CommandLine();
		this.index = 0;
	}

	public void evaluate(Tree tree) throws SyntaxException {
		tree.getRootNode().makeCommand(this.commandLine);
		this.commandLine.addCommand(new Command(Token.EOF));
		do {
			this.isEOF = this.commandLine.getCommand(this.index).execution();
			this.index++;
		} while (!this.isEOF);
	}
}
