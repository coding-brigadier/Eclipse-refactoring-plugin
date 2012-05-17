package object_in;

public class TestAssignValUsingCalculationWithClassVars {
	private int calculateMe;
	
	private int testVar1;
	private String testVar2;
	private double testVar3;
	private double testVar4;
	
	public TestAssignValUsingCalculationWithClassVars(int inputVar1, String inputVar2, int calculateMe){
		this.testVar1 = inputVar1;
		this.testVar2 = inputVar2;
		this.calculateMe = calculateMe + this.testVar1;
	}
	
	public TestAssignValUsingCalculationWithClassVars(double inputVar3, double inputVar4){
		this.testVar3 = inputVar3;
		this.testVar4 = inputVar4;
		calculateMe = (int) (testVar3*testVar4);
	}
}
