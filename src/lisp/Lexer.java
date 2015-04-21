package lisp;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Lexer{
	private Token[] token;
	private Pattern p;
	private Matcher m;
	private int tokenNumber;
	public Lexer(){
		this.tokenNumber = 0;
		this.token = new Token[100];
	}

	public void lineToTokens(String inputLine) throws SyntaxException{
		int lexemeBegin=0,forward=1;

		while(inputLine.length() > forward){
			this.p = Pattern.compile("^[^\\s\\(\\)a-zA-Z0-9<>=!\\+\\-\\*\\/\\.]$");
			m = p.matcher(inputLine.substring(forward-1,forward));
			if(m.find()){
				throw new SyntaxException();
			}
			this.p = Pattern.compile("^\\s$");
			m = p.matcher(inputLine.substring(lexemeBegin,forward));
			if(m.find()){
				lexemeBegin++;
				forward++;
			}
			this.p = Pattern.compile("^[\\(\\)]$");
			m = p.matcher(inputLine.substring(lexemeBegin,forward));
			if(m.find()){
				token[this.tokenNumber] = new Token("paren",inputLine.substring(lexemeBegin,forward),-1);
				this.tokenNumber++;
				lexemeBegin++;
				forward++;
			}
			this.p = Pattern.compile("^setq[^a-zA-Z0-9]$");
			m = p.matcher(inputLine.substring(lexemeBegin,forward));
			if(m.find()){
				token[this.tokenNumber] = new Token("command","setq",2);
				this.tokenNumber++;
				lexemeBegin = forward-1;
			}
			this.p = Pattern.compile("^(if|defun)[^a-zA-Z0-9]$");
			m = p.matcher(inputLine.substring(lexemeBegin,forward));
			if(m.find()){
				token[this.tokenNumber] = new Token("command",inputLine.substring(lexemeBegin,forward-1),3);
				this.tokenNumber++;
				lexemeBegin = forward-1;
			}
			this.p = Pattern.compile("^[a-zA-Z][a-zA-Z0-9]*[^a-zA-Z0-9]$");
			m = p.matcher(inputLine.substring(lexemeBegin,forward));
			if(m.find()){
				token[this.tokenNumber] = new Token("id",inputLine.substring(lexemeBegin,forward-1));
				this.tokenNumber++;
				lexemeBegin = forward - 1;
			}
			this.p = Pattern.compile("^-?[0-9]+(\\.[0-9]+)?(E[+\\-]?[0-9])?[^\\.E0-9]$");
			m = p.matcher(inputLine.substring(lexemeBegin,forward));
			if(m.find()){
				token[this.tokenNumber] = new Token("number",Double.parseDouble(inputLine.substring(lexemeBegin,forward-1)));
				this.tokenNumber++;
				lexemeBegin = forward - 1;
			}
			this.p = Pattern.compile("^[\\+\\*\\/]$");
			m = p.matcher(inputLine.substring(lexemeBegin,forward));
			if(m.find()){
				token[this.tokenNumber] = new Token("operator",inputLine.substring(lexemeBegin,forward),2);
				this.tokenNumber++;
				lexemeBegin = forward;
				forward++;
			}
			this.p = Pattern.compile("^-[^0-9]$");
			m = p.matcher(inputLine.substring(lexemeBegin,forward));
			if(m.find()){
				token[this.tokenNumber] = new Token("operator",inputLine.substring(lexemeBegin,forward-1),2);
				this.tokenNumber++;
				lexemeBegin = forward - 1;
			}
			this.p = Pattern.compile("^<=|>=|!=|=$");
			m = p.matcher(inputLine.substring(lexemeBegin,forward));
			if(m.find()){
				token[this.tokenNumber] = new Token("comparator",inputLine.substring(lexemeBegin,forward),2);
				this.tokenNumber++;
				lexemeBegin = forward;
				forward++;
			}
			this.p = Pattern.compile("^<[^=]|>[^=]$");
			m = p.matcher(inputLine.substring(lexemeBegin,forward));
			if(m.find()){
				token[this.tokenNumber] = new Token("comparator",inputLine.substring(lexemeBegin,forward-1),2);
				this.tokenNumber++;
				lexemeBegin = forward - 1;
			}
			this.p = Pattern.compile("^[a-zA-Z][a-zA-Z0-9]*|-?[0-9]+(\\.[0-9]+)?(E[+\\-]?[0-9])?|[<>!\\-]$");
			m = p.matcher(inputLine.substring(lexemeBegin,forward));
			if(m.find()){
				forward++;
			}
		}
		adjustId();
	}
	public void adjustId(){
		for(int i=0;i<this.tokenNumber;i++){
			if(token[i].getAttribute().equals("id")){
				for(int j=0;j<i;j++){
					if(token[i].getName().equals(token[j].getName())){
						token[i] = token[j];
					}
				}
			}
		}
	}

	public Token getToken(int i){
		return token[i];
	}
	public Token[] getToken(){
		return token;
	}
	public int getTokenNumber(){
		return this.tokenNumber;
	}
}
