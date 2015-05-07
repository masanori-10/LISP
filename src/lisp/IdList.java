package lisp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import lisp.Enum.Token;

public class IdList {
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
	private Map<String, ArrayList<Double>> mapForArgument;

	public MapForArgument() {
		this.mapForArgument = new HashMap<String, ArrayList<Double>>();
	}

	public void setArgument(String key) {
		this.mapForArgument.put(key, new ArrayList<Double>());
	}

	public ArrayList<Double> getArgument(String key) {
		return this.mapForArgument.get(key);
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
	private MapForArgument mapForArgument;

	public FunctionBody() {
		this.commandLine = new CommandLine();
	}

	public void setCommandLine(Node dummyArgNode, Node substanceNode) {
		dummyArgNode.commandize(this.commandLine);
		commandLine.addCommand(new Command(Token.SETARG));
		substanceNode.commandize(this.commandLine);
		commandLine.addCommand(new Command(Token.RESETARG));
	}

	public CommandLine getCommandLine() {
		return this.commandLine;
	}

	public MapForArgument getMapForArgument() {
		return this.mapForArgument;
	}
}
