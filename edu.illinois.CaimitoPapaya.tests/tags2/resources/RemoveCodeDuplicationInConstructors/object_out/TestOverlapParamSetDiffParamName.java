package object_in;

public class TestOverlapParamSetSameInputNames {
	private int testVar1;
	private String testVar2;
	private double testVar3;
	private double testVar4;
	
	public TestOverlapParamSetSameInputNames(int inputVar1, String inputVar2){
		this(inputVar1,inputVar2,0.0,0.0);
	}
	
	public TestOverlapParamSetSameInputNames(int testVar1, double inputVar3, double inputVar4){
		this(testVar1,null,inputVar3,inputVar4);
	}
	
	public TestOverlapParamSetSameInputNames(int inputVar1, String inputVar2, double inputVar3, double inputVar4){
		this.testVar1 = inputVar1;
		this.testVar2 = inputVar2;
		this.testVar3 = testVar3;
		this.testVar4 = inputVar4;
	}
}