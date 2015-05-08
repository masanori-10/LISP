package lisp;

import java.util.ArrayList;

import lisp.Enum.Label;
import lisp.Enum.Token;

public class Command {
	private Token commandCode;
	private Label label;
	private Value value;
	private CommandLine commandLine;

	public Command(double value) {
		this.commandCode = Token.PUSH;
		this.value = new Value(value);
	}

	public Command(boolean value) {
		this.commandCode = Token.PUSH;
		this.value = new Value(value);
	}

	public Command(String key) {
		if (MapForFunction.existFunction(key)) {
			this.commandCode = Token.FUNCTION;
			this.commandLine = MapForFunction.getFunction(key).getCommandLine();
		} else {
			this.commandCode = Token.PUSH;
			this.value = new Value(key);
		}
	}

	public Command(Token commandCode) {
		this.commandCode = commandCode;
	}

	public Command(Label label) {
		this.commandCode = Token.LABEL;
		this.label = label;
	}

	public Command(Token commandCode, Label label) {
		this.commandCode = commandCode;
		this.label = label;
	}

	public Token getCommndCode() {
		return this.commandCode;
	}

	public Value getValue() {
		return this.value;
	}

	public Label getLabel() {
		return this.label;
	}

	public CommandLine getCommandLine() {
		return this.commandLine;
	}
}

class CommandLine {
	private ArrayList<Command> commandLine;

	public CommandLine() {
		this.commandLine = new ArrayList<Command>();
	}

	public void addCommand(Command command) {
		this.commandLine.add(command);
	}

	public Command getCommand(int index) {
		return this.commandLine.get(index);
	}
}