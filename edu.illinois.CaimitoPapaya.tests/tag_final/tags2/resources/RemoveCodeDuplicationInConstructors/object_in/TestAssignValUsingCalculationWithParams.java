package object_in;

public class TestAssignValUsingCalculationWithParams {
	private int calculateMe;
	
	private int testVar1;
	private String testVar2;
	private double testVar3;
	private double testVar4;
	
	public TestAssignValUsingCalculationWithParams(int inputVar1, String inputVar2){
		this.testVar1 = inputVar1;
		this.testVar2 = inputVar2;
		calculateMe = inputVar1 * inputVar2;
	}
	
	public TestAssignValUsingCalculationWithParams(double inputVar3, double inputVar4){
		this.testVar3 = inputVar3;
		this.testVar4 = inputVar4;
		calculateMe = (int) (inputVar4 + inputVar3);
	}
}
