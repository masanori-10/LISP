package lisp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import lisp.Enum.Token;

class MapForVariable {
	private static Map<String, Double> mapForVariable;

	static {
		mapForVariable = new HashMap<String, Double>();
	}

	public static void setVariable(String key, double value) {
		mapForVariable.put(key, value);
	}

	public static double getVariable(String key) {
		return mapForVariable.get(key);
	}
}

class MapForArgument {
	private static Map<String, ArrayList<Double>> mapForArgument;

	static {
		mapForArgument = new HashMap<String, ArrayList<Double>>();
	}

	public static boolean existArgument(String key) {
		return mapForArgument.containsKey(key);
	}

	public static void setArgument(String key) {
		mapForArgument.put(key, new ArrayList<Double>());
	}

	public static ArrayList<Double> getArgument(String key) {
		return mapForArgument.get(key);
	}
}

class MapForFunction {
	private static Map<String, FunctionBody> mapForFanction;

	static {
		mapForFanction = new HashMap<String, FunctionBody>();
	}

	public static boolean existFunction(String key) {
		return mapForFanction.containsKey(key);
	}

	public static void setFunction(String key) {
		mapForFanction.put(key, new FunctionBody());
	}

	public static FunctionBody getFunction(String key) {
		return mapForFanction.get(key);
	}
}

class FunctionBody {
	private CommandLine commandLine;

	public FunctionBody() {
		this.commandLine = new CommandLine();
	}

	public void setCommandLine(Node dummyArgNode, Node substanceNode) {
		dummyArgNode.makeCommand(this.commandLine);
		commandLine.addCommand(new Command(Token.SETARG));
		substanceNode.makeCommand(this.commandLine);
		commandLine.addCommand(new Command(Token.RESETARG));
	}

	public CommandLine getCommandLine() {
		return this.commandLine;
	}
}
