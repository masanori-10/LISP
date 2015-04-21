package lisp;

import java.io.IOException;

public class Main{
	public static void main(String[] args){

		Reader reader = new Reader();
		Lexer lexer = new Lexer();
		Parser parser = new Parser();
		Estimator estimator = new Estimator();

		System.out.println("Please select the input method.(Dialog->0,file->1)");
		try{
			reader.readDialog();
			lexer.lineToTokens(reader.getInputLine());

			for(int i=0;i<lexer.getTokenNumber();i++){
				System.out.println(lexer.getToken(i).getAttribute());
				System.out.println(lexer.getToken(i).getName());
				System.out.println(lexer.getToken(i).getValue());
			}

			parser.parser(lexer.getToken(),lexer.getTokenNumber());
			estimator.estimator(parser.getCompleteTree(),parser.getCompleteNumber());

			System.out.println(parser.getCompleteTree(0).getRootNode().getChild(1).getToken().getAttribute());
			System.out.println(parser.getCompleteTree(0).getRootNode().getChild(1).getToken().getName());
			System.out.println(parser.getCompleteTree(0).getRootNode().getChild(1).getToken().getValue());

		}catch(SyntaxException e){
			System.out.println(e);
		}catch(IOException e){
			System.out.println(e + "(Input is invalid.)");
		}catch(OtherException e){
			System.out.println(e);
		}
	}
}