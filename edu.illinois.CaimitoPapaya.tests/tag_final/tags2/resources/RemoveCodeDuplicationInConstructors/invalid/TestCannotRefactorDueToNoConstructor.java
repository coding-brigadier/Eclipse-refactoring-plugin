package invalid;

public class TestCannotRefactorDueToNoConstructor {
	
	private String variable1;
	private int variable2;
	
	String getVariable1() {
		return variable1;
	}
	
	void setVariable1(String variable1){
		this.variable1 = variable1;
	}

}