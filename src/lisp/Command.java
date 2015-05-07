package lisp;

import java.util.ArrayList;

import lisp.Enum.Token;

public class Command {
	private Token commandCode;
	private double value;
	private static Stack stack;

	public Command(double value) {
		this.commandCode = Token.PUSH;
		this.value = value;
	}

	public Command(Token commandCode) {
		this.commandCode = commandCode;
	}

	public void evaluate() {
		switch (this.commandCode) {
		case PUSH:
			stack.push(this.value);
			break;
		case PLUS:
			stack.push(stack.popNumber2nd() + stack.popNumber());
			break;
		case MINUS:
			stack.push(stack.popNumber2nd() - stack.popNumber());
			break;
		case MULT:
			stack.push(stack.popNumber2nd() * stack.popNumber());
			break;
		case DIVIDE:
			stack.push(stack.popNumber2nd() / stack.popNumber());
			break;
		case LESSEQUAL:
			stack.push(stack.popNumber2nd() <= stack.popNumber());
			break;
		case GREATEREQUAL:
			stack.push(stack.popNumber2nd() >= stack.popNumber());
			break;
		case NOTEQUAL:
			stack.push(stack.popNumber2nd() != stack.popNumber());
			break;
		case EQUAL:
			stack.push(stack.popNumber2nd() == stack.popNumber());
			break;
		case LESS:
			stack.push(stack.popNumber2nd() < stack.popNumber());
			break;
		case GREATER:
			stack.push(stack.popNumber2nd() > stack.popNumber());
			break;
		default:
			break;
		}
	}
}

class CommandLine {
	private static ArrayList<Command> commandLine;

	public static void addCommand(Command command) {
		commandLine.add(command);
	}
}