package lisp;

import java.util.ArrayList;

import lisp.Enum.Label;
import lisp.Enum.Token;

public class Eval {
	private ArrayList<CommandLine> commandLine;
	private Command currentCommand;
	private ArrayList<Integer> index;
	private int commandLineSize;
	private ArrayList<ArrayList<Value>> dummyArgStock;
	private Stack stack;
	private int count;
	private Value stock;

	public void evaluate(Tree tree) {
		this.commandLine = new ArrayList<CommandLine>();
		this.commandLine.add(new CommandLine());
		this.index = new ArrayList<Integer>();
		this.index.add(0);
		this.commandLineSize = 0;
		this.dummyArgStock = new ArrayList<ArrayList<Value>>();
		this.stack = new Stack();
		tree.getRootNode().makeCommand(
				this.commandLine.get(this.commandLineSize));
		this.commandLine.get(this.commandLineSize).addCommand(
				new Command(Token.EOF));
		while (true) {
			this.currentCommand = this.commandLine.get(this.commandLineSize)
					.getCommand(this.index.get(this.commandLineSize));
			switch (this.currentCommand.getCommndCode()) {
			case PUSH:
				this.stack.push(this.currentCommand.getValue());
				break;
			case PLUS:
				this.stack.push(new Value(this.stack.pop2nd().getNumber()
						+ this.stack.pop().getNumber()));
				break;
			case MINUS:
				this.stack.push(new Value(this.stack.pop2nd().getNumber()
						- this.stack.pop().getNumber()));
				break;
			case MULT:
				this.stack.push(new Value(this.stack.pop2nd().getNumber()
						* this.stack.pop().getNumber()));
				break;
			case DIVIDE:
				this.stack.push(new Value(this.stack.pop2nd().getNumber()
						/ this.stack.pop().getNumber()));
				break;
			case LESSEQUAL:
				this.stack.push(new Value(
						this.stack.pop2nd().getNumber() <= this.stack.pop()
								.getNumber()));
				break;
			case GREATEREQUAL:
				this.stack.push(new Value(
						this.stack.pop2nd().getNumber() >= this.stack.pop()
								.getNumber()));
				break;
			case NOTEQUAL:
				this.stack.push(new Value(
						this.stack.pop2nd().getNumber() != this.stack.pop()
								.getNumber()));
				break;
			case EQUAL:
				this.stack.push(new Value(
						this.stack.pop2nd().getNumber() == this.stack.pop()
								.getNumber()));
				break;
			case LESS:
				this.stack.push(new Value(
						this.stack.pop2nd().getNumber() < this.stack.pop()
								.getNumber()));
				break;
			case GREATER:
				this.stack.push(new Value(
						this.stack.pop2nd().getNumber() > this.stack.pop()
								.getNumber()));
				break;
			case SETQ:
				this.stack.pop2nd().setVariable(this.stack.pop().getNumber());
				break;
			case IF:
				if (!this.stack.pop().getBool()) {
					this.count = 1;
					while (this.count > 0) {
						this.index.set(this.commandLineSize,
								this.index.get(this.commandLineSize) + 1);
						this.currentCommand = this.commandLine.get(
								this.commandLineSize).getCommand(
								this.index.get(this.commandLineSize));
						if (this.currentCommand.getLabel() == Label.SOIF) {
							this.count++;
						} else if (this.currentCommand.getLabel() == Label.EOTRUE) {
							this.count--;
						}
					}
				}
				break;
			case FUNCTION:
				this.commandLine.add(this.currentCommand.getCommandLine());
				this.index.add(-1);
				this.dummyArgStock.add(new ArrayList<Value>());
				this.commandLineSize++;
				break;
			case SETARG:
				this.stock = this.stack.pop();
				while (!(this.stock == null)) {
					this.dummyArgStock.get(this.commandLineSize - 1).add(
							this.stock);
					this.stock = this.stack.pop();
				}
				for (int i = 0; i < this.dummyArgStock.get(
						this.commandLineSize - 1).size(); i++) {
					this.dummyArgStock.get(this.commandLineSize - 1).get(i)
							.setVariable(this.stack.pop().getNumber());
				}
				break;
			case RESETARG:
				for (int i = 0; i < this.dummyArgStock.get(
						this.commandLineSize - 1).size(); i++) {
					this.dummyArgStock.get(this.commandLineSize - 1).get(i)
							.resetVariable();
				}
				this.commandLine.remove(this.commandLineSize);
				this.index.remove(this.commandLineSize);
				this.dummyArgStock.remove(this.commandLineSize - 1);
				this.commandLineSize--;
				break;
			case JUMP:
				this.count = 1;
				while (this.count > 0) {
					this.index.set(this.commandLineSize,
							this.index.get(this.commandLineSize) + 1);
					this.currentCommand = this.commandLine.get(
							this.commandLineSize).getCommand(
							this.index.get(this.commandLineSize));
					if (this.currentCommand.getLabel() == Label.SOIF) {
						this.count++;
					} else if (this.currentCommand.getLabel() == Label.EOFALSE) {
						this.count--;
					}
				}
				break;
			case PUSHNULL:
				this.stack.push(null);
				break;
			case EOF:
				while (true) {
					this.stock = this.stack.getValue();
					if (this.stock == null) {
						return;
					} else if (this.stock.isNumber()) {
						System.out.println(this.stock.getNumber());
					} else {
						System.out.println(this.stock.getBool());
					}
				}
			default:
			}
			this.index.set(this.commandLineSize,
					this.index.get(this.commandLineSize) + 1);
		}
	}
}
