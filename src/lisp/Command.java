package lisp;

import java.util.ArrayList;

import lisp.Enum.Token;

public class Command {
	private Token commandCode;
	private Value value;
	private CommandLine commandLine;
	private int index;
	private boolean isEOF;
	private ArrayList<Value> dummyArg;
	private ArrayList<Value> actualArg;
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
			this.dummyArg = new ArrayList<Value>();
			this.actualArg = new ArrayList<Value>();
		} else {
			this.commandCode = Token.PUSH;
			this.value = new Value(key);
		}
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
			stack.pop();
			do {
				this.dummyArg.add(stack.pop());
			} while (!stack.read().isNull());
			for (int i = 0; i < this.dummyArg.size(); i++) {
				this.actualArg.add(stack.pop());
			}
			for (int i = 0; i < this.dummyArg.size(); i++) {
				this.dummyArg.get(i).setVariable(
						this.actualArg.get(i).getNumber());
			}
			break;
		case RESETARG:
			for (int i = 0; i < this.dummyArg.size(); i++) {
				this.dummyArg.get(i).resetVariable();
			}
		case EOF:
			return true;
		case DUMMY:
			stack.push(new Value());
			break;
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