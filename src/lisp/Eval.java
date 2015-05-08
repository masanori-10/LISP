package lisp;

import java.util.ArrayList;

import lisp.Enum.Attribute;
import lisp.Enum.Label;
import lisp.Enum.Token;

public class Eval {
	private ArrayList<CommandLine> commandLine;
	private Command currentCommand;
	private ArrayList<Integer> index;
	private int commandLineSize;
	private ArrayList<ArrayList<String>> dummyArgStock;
	private Stack stack;
	private int count;
	private String stock;

	public void evaluate(Tree tree) throws SyntaxException {
		this.commandLine = new ArrayList<CommandLine>();
		this.commandLine.add(new CommandLine());
		this.index = new ArrayList<Integer>();
		this.index.add(0);
		this.commandLineSize = 0;
		this.dummyArgStock = new ArrayList<ArrayList<String>>();
		this.stack = new Stack();
		tree.getRootNode().makeCommand(
				this.commandLine.get(this.commandLineSize));
		this.commandLine.get(this.commandLineSize).addCommand(
				new Command(Token.EOF));
		while (true) {
			this.currentCommand = this.commandLine.get(this.commandLineSize)
					.getCommand(this.index.get(this.commandLineSize));
			switch (this.currentCommand.getCommndCode()) {
			case PUSHNUMBER:
				this.stack.push(this.currentCommand.getValue().getNumber());
				break;
			case PUSHBOOL:
				this.stack.push(this.currentCommand.getValue().getBool());
				break;
			case PUSHKEY:
				this.stack.push(this.currentCommand.getValue().getKey());
				break;
			case PLUS:
				this.stack.push(this.stack.pop2nd() + this.stack.pop());
				break;
			case MINUS:
				this.stack.push(this.stack.pop2nd() - this.stack.pop());
				break;
			case MULT:
				this.stack.push(this.stack.pop2nd() * this.stack.pop());
				break;
			case DIVIDE:
				this.stack.push(this.stack.pop2nd() / this.stack.pop());
				break;
			case LESSEQUAL:
				this.stack.push(this.stack.pop2nd() <= this.stack.pop());
				break;
			case GREATEREQUAL:
				this.stack.push(this.stack.pop2nd() >= this.stack.pop());
				break;
			case NOTEQUAL:
				this.stack.push(this.stack.pop2nd() != this.stack.pop());
				break;
			case EQUAL:
				this.stack.push(this.stack.pop2nd() == this.stack.pop());
				break;
			case LESS:
				this.stack.push(this.stack.pop2nd() < this.stack.pop());
				break;
			case GREATER:
				this.stack.push(this.stack.pop2nd() > this.stack.pop());
				break;
			case SETQ:
				MapForVariable.setVariable(this.stack.popKey2nd(),
						this.stack.pop());
				break;
			case IF:
				if (!(this.stack.readAttribute() == Attribute.BOOL)) {
					throw new SyntaxException();
				}
				if (this.stack.pop() == 1.0) {
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
				this.dummyArgStock.add(new ArrayList<String>());
				this.commandLineSize++;
				break;
			case SETARG:
				this.stock = this.stack.popKey();
				while (!(this.stock == null)) {
					this.dummyArgStock.get(this.commandLineSize - 1).add(
							this.stock);
					this.stock = this.stack.popKey();
				}
				for (int i = 0; i < this.dummyArgStock.get(
						this.commandLineSize - 1).size(); i++) {
					MapForArgument.getArgument(
							this.dummyArgStock.get(this.commandLineSize - 1)
									.get(i)).add(this.stack.pop());
				}
				break;
			case RESETARG:
				for (int i = 0; i < this.dummyArgStock.get(
						this.commandLineSize - 1).size(); i++) {
					MapForArgument.getArgument(
							this.dummyArgStock.get(this.commandLineSize - 1)
									.get(i)).remove(
							MapForArgument.getArgument(
									this.dummyArgStock.get(
											this.commandLineSize - 1).get(i))
									.size() - 1);
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
					if (this.stack.getAttribute() == Attribute.NULL) {
						return;
					} else if (this.stack.getAttribute() == Attribute.NUMBER) {
						System.out.println(this.stack.getValue());
					} else {
						if (this.stack.getValue() == 0.0) {
							System.out.println("T");
						} else {
							System.out.println("Nil");
						}
					}
				}
			default:
			}
			this.index.set(this.commandLineSize,
					this.index.get(this.commandLineSize) + 1);
		}
	}
}
