package lisp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Reader{
	private String inputLine;

	public void read() throws IOException{
		while(true){
			System.out.println("Please select the input method.\n(Dialog->0,file->1)");
			InputStreamReader isr = new InputStreamReader(System.in);
			BufferedReader br = new BufferedReader(isr);
			String buf = br.readLine();
			int judge = Integer.parseInt(buf);
			if(judge == 0){
				this.readDialog();
				return;
			}else if(judge == 1){
				this.readFile();
				return;
			}else{
				System.out.println("Please enter a 0 or 1.");
			}
		}
	}
	public void readDialog() throws IOException{
		System.out.println("Please input the command.");
		InputStreamReader isr = new InputStreamReader(System.in);
		BufferedReader input = new BufferedReader(isr);
		this.inputLine = input.readLine() + "@";
	}
	public void readFile() throws IOException{
		System.out.println("Display the available code.\nPlease enter the code to use.");
		String path = "C:\\pleiades-e4.4-java_20150310\\pleiades\\workspace\\LISP\\lisp_code";
		File dir = new File(path);
		String[] fileNames = dir.list();
		for(int i=0;i<fileNames.length;i++){
			String fileName = fileNames[i].replace(path + "\\","");
			fileName = fileName.replace(".txt","");
			System.out.println(fileName);
		}
		InputStreamReader isr = new InputStreamReader(System.in);
		BufferedReader br = new BufferedReader(isr);
		String buf = br.readLine();
		File inputFile = new File(path + "\\" + buf + ".txt");
		FileReader fileReader = new FileReader(inputFile);
		int ch = fileReader.read();
		this.inputLine = String.valueOf((char)ch);
		ch = fileReader.read();
		while(ch != -1){
			this.inputLine += (char)ch;
			ch = fileReader.read();
		}
		this.inputLine += "@";
		fileReader.close();
	}

	public String getInputLine(){
		return this.inputLine;
	}
}