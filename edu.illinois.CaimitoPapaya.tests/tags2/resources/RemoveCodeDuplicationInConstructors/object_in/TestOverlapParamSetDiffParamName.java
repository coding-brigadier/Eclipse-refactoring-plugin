package object_in;

public class TestOverlapParamSetSameInputNames {
	private int testVar1;
	private String testVar2;
	private double testVar3;
	private double testVar4;
	
	public TestOverlapParamSetSameInputNames(int inputVar1, String inputVar2){
		this.testVar1 = inputVar1;
		this.testVar2 = inputVar2;
		this.testVar3 = 0.0;
		this.testVar4 = 0.0;
	}
	public TestOverlapParamSetSameInputNames(int testVar1, double inputVar3, double inputVar4){
		this.testVar1 = testVar1;
		this.testVar2 = null;
		this.testVar3 = inputVar3;
		this.testVar4 = inputVar4;
	}
}
