package object_in;

public class TestAssignValUsingCalculationWithParams {
	private int calculateMe;
	
	private int testVar1;
	private String testVar2;
	private double testVar3;
	private double testVar4;
	
	public TestAssignValUsingCalculationWithParams(int inputVar1, String inputVar2){
		this(inputVar1,inputVar2,0.0,0.0,inputVar1 * inputVar2);
	}
	
	public TestAssignValUsingCalculationWithParams(double inputVar3, double inputVar4){
		this(0,0,testVar3,testVar4,(int) (inputVar4 + inputVar3));
	}
	
	public TestAssignValUsingCalculationWithParams(int inputVar1, String inputVar2, double inputVar3, double inputVar4, int calculateMe){
		this.testVar1 = testVar1;
		this.testVar2 = testVar2;
		this.testVar3 = testVar3;
		this.testVar4 = testVar4;
		this.calculateMe = 0;
	}
}
