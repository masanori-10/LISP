package lisp;

import java.util.ArrayList;

import lisp.Enum.Token;

public class Command {
	private Token commandCode;
	private Value value;
	private CommandLine commandLine;
	private int index;
	private boolean isEOF;
	private static Stack stack;

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
			this.index = 0;
		}
		this.commandCode = Token.PUSH;
		this.value = new Value(key);
	}

	public Command(Token commandCode) {
		this.commandCode = commandCode;
	}

	public boolean execution() {
		switch (this.commandCode) {
		case PUSH:
			stack.push(this.value);
			break;
		case PLUS:
			stack.push(new Value(stack.pop2nd().getNumber()
					+ stack.pop().getNumber()));
			break;
		case MINUS:
			stack.push(new Value(stack.pop2nd().getNumber()
					- stack.pop().getNumber()));
			break;
		case MULT:
			stack.push(new Value(stack.pop2nd().getNumber()
					* stack.pop().getNumber()));
			break;
		case DIVIDE:
			stack.push(new Value(stack.pop2nd().getNumber()
					/ stack.pop().getNumber()));
			break;
		case LESSEQUAL:
			stack.push(new Value(stack.pop2nd().getNumber() <= stack.pop()
					.getNumber()));
			break;
		case GREATEREQUAL:
			stack.push(new Value(stack.pop2nd().getNumber() >= stack.pop()
					.getNumber()));
			break;
		case NOTEQUAL:
			stack.push(new Value(stack.pop2nd().getNumber() != stack.pop()
					.getNumber()));
			break;
		case EQUAL:
			stack.push(new Value(stack.pop2nd().getNumber() == stack.pop()
					.getNumber()));
			break;
		case LESS:
			stack.push(new Value(stack.pop2nd().getNumber() < stack.pop()
					.getNumber()));
			break;
		case GREATER:
			stack.push(new Value(stack.pop2nd().getNumber() > stack.pop()
					.getNumber()));
			break;
		case SETQ:
			stack.pop2nd().setVariable(stack.pop().getNumber());
			break;
		case IF:
			if (stack.pop().getBool()) {

			} else {

			}
			break;
		case FUNCTION:
			do {
				this.isEOF = this.commandLine.getCommand(this.index)
						.execution();
				this.index++;
			} while (!this.isEOF);
			break;
		case SETARG:
			break;
		case RESETARG:
		case EOF:
			return true;
		default:
			break;
		}
		return false;
	}
}

class CommandLine {
	private ArrayList<Command> commandLine;

	public void addCommand(Command command) {
		this.commandLine.add(command);
	}

	public Command getCommand(int index) {
		return this.commandLine.get(index);
	}
}