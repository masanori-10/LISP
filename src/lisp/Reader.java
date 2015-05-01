package lisp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Reader {
	private String inputLine;

	public boolean read(String[] args) throws IOException,
			ComandLineArgumentException, FileReadException {
		while (true) {
			if (args.length == 0) {
				return this.readDialog();
			} else if (args.length == 1) {
				this.readFile(args[0]);
				return false;
			} else {
				System.out.println("The number of arguments is 0 or 1.");
				throw new ComandLineArgumentException();
			}
		}
	}

	public boolean readDialog() throws IOException {
		System.out.println("Please input the next command.");
		InputStreamReader isr = new InputStreamReader(System.in);
		BufferedReader input = new BufferedReader(isr);
		this.inputLine = input.readLine() + "@";
		if (this.inputLine.equals("exit@")) {
			return false;
		}
		return true;
	}

	public void readFile(String arg) throws IOException, FileReadException {
		String path = "C:\\pleiades-e4.4-java_20150310\\pleiades\\workspace\\LISP\\lisp_code";
		File inputFile;
		inputFile = new File(path + "\\" + arg + ".txt");
		if (!inputFile.exists()) {
			System.out.println("File not found.");
			System.out.println("Please pass any of the following argument.");
			File dir = new File(path);
			String[] files = dir.list();
			for (int i = 0; i < files.length; i++) {
				String file = files[i].replace(path, "");
				file = file.replace(".txt", "");
				System.out.println(file);
			}
			throw new FileReadException();
		}
		FileReader fileReader = new FileReader(inputFile);
		int ch = fileReader.read();
		this.inputLine = String.valueOf((char) ch);
		ch = fileReader.read();
		while (ch != -1) {
			this.inputLine += (char) ch;
			ch = fileReader.read();
		}
		this.inputLine += "@";
		fileReader.close();
	}

	public String getInputLine() {
		return this.inputLine;
	}
}