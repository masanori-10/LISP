package lisp;

public class Estimator{
	public void estimator(TreeNode completeTree[],int completeNumber) throws OtherException{
		for(int i=0;i<completeNumber;i++){
			this.estimator(completeTree[i].getProcessedNode().getChild(1));
		}
	}
	public void estimator(Tree processedNode) throws OtherException{
		double operandX,operandY;
		switch (processedNode.getToken().getAttribute()){
		case "operator":
			if(!(processedNode.getChild(1).getToken().getAttribute().equals("number")||processedNode.getChild(1).getToken().getAttribute().equals("id"))){
				estimator(processedNode.getChild(1));
			}
			operandX = processedNode.getChild(1).getToken().getValue();
			if(!(processedNode.getChild(2).getToken().getAttribute().equals("number")||processedNode.getChild(2).getToken().getAttribute().equals("id"))){
				estimator(processedNode.getChild(2));
			}
			operandY = processedNode.getChild(2).getToken().getValue();
			switch (processedNode.getToken().getName()) {
			case "+":
				processedNode.getToken().setValue(operandX+operandY);
				break;
			case "-":
				processedNode.getToken().setValue(operandX-operandY);
				break;
			case "*":
				processedNode.getToken().setValue(operandX*operandY);
				break;
			case "/":
				processedNode.getToken().setValue(operandX/operandY);
			}
			processedNode.getToken().setAttribute("number");
			processedNode.getToken().setName(null);
			break;
		case "comparator":
			String bool;
			if(!(processedNode.getChild(1).getToken().getAttribute().equals("number")||processedNode.getChild(1).getToken().getAttribute().equals("id"))){
				estimator(processedNode.getChild(1));
			}
			operandX = processedNode.getChild(1).getToken().getValue();
			if(!(processedNode.getChild(2).getToken().getAttribute().equals("number")||processedNode.getChild(2).getToken().getAttribute().equals("id"))){
				estimator(processedNode.getChild(2));
			}
			operandY = processedNode.getChild(2).getToken().getValue();
			switch (processedNode.getToken().getName()) {
			case "<=":
				if(operandX <= operandY){
					bool = "T";
				}else{
					bool = "Nil";
				}
				break;
			case ">=":
				if(operandX >= operandY){
					bool = "T";
				}else{
					bool = "Nil";
				}
				break;
			case "!=":
				if(operandX != operandY){
					bool = "T";
				}else{
					bool = "Nil";
				}
				break;
			case "=":
				if(operandX == operandY){
					bool = "T";
				}else{
					bool = "Nil";
				}
				break;
			case "<":
				if(operandX < operandY){
					bool = "T";
				}else{
					bool = "Nil";
				}
				break;
			case ">":
				if(operandX > operandY){
					bool = "T";
				}else{
					bool = "Nil";
				}
				break;
				default:
					throw new OtherException();
			}
			processedNode.getToken().setName(bool);
			processedNode.getToken().setAttribute("bool");
			break;
		case "command":
			switch(processedNode.getToken().getName()){
			case "setq":
				processedNode.getChild(1).getToken().setValue(processedNode.getChild(2).getToken().getValue());
				break;
			case "if":
				if(processedNode.getChild(1).getToken().getName().equals("T")){
					if(!(processedNode.getChild(2).getToken().getAttribute().equals("number")||processedNode.getChild(2).getToken().getAttribute().equals("id"))){
						estimator(processedNode.getChild(2));
					}
					processedNode.getChild(2).setParent(processedNode.getParent());
					processedNode = processedNode.getChild(2);
				}else{
					if(!(processedNode.getChild(3).getToken().getAttribute().equals("number")||processedNode.getChild(3).getToken().getAttribute().equals("id"))){
						estimator(processedNode.getChild(3));
					}
					processedNode.getChild(3).setParent(processedNode.getParent());
					processedNode = processedNode.getChild(3);
				}
				break;
			case "defun":
				processedNode.getChild(1).getToken().setAttribute("defunedId");
				if(processedNode.getChild(2).getToken().getAttribute().equals("id")){
					processedNode.getChild(1).getToken().setArgumentCounter(1);
				}else if(processedNode.getChild(2).getToken().getAttribute().equals("paren")){
					int i = 1;
					while(processedNode.getChild(1).getChild(i) != null){
						estimator(processedNode.getChild(i));
						i++;
					}
				}
				break;
			}
		case "paren":
			int i = 1;
			while(processedNode.getChild(i) != null){
				estimator(processedNode.getChild(i));
				i++;
			}
			if(i == 2){
				processedNode.getChild(1).setParent(processedNode.getParent());
				processedNode = processedNode.getChild(1);
			}
		}
		processedNode.getToken().setAttribute("answer");
	}
}
