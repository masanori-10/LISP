package lisp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Reader{
	private String inputLine;

	public void readDialog() throws IOException{
		System.out.println("Please input the command.");
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
		this.inputLine = input.readLine() + "@";
	}

	public String getInputLine(){
		return this.inputLine;
	}
}