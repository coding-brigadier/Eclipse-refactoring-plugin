package object_in;

public class TestDisjointParamSetSameInputNames {
	private int testVar1;
	private int testVar2;
	private double testVar3;
	private double testVar4;
	
	public TestDisjointParamSetSameInputNames(int testVar1, int testVar2){
		this.testVar1 = testVar1;
		this.testVar2 = testVar2;
	}
	public TestDisjointParamSetSameInputNames(double testVar3, double testVar4){
		this.testVar3 = testVar3;
		this.testVar4 = testVar4;
	}
}
