package object_in;

public class TestAssignValUsingCalculationWithMultipleParams {
	private double calculateMe;
	private double testVar1;
	private String testVar2;
	private double testVar3;
	private double testVar4;
	
	public TestAssignValUsingCalculationWithMultipleParams(double inputVar1, String inputVar2, double calculateMe){
		this.testVar1 = inputVar1;
		this.testVar2 = inputVar2;
		this.calculateMe = calculateMe + inputVar1;
	}
	
	public TestAssignValUsingCalculationWithMultipleParams(double inputVar3, double inputVar4){
		this.testVar3 = inputVar3;
		this.testVar4 = inputVar4;
		calculateMe = inputVar3*inputVar4;
	}
}
