package object_in;

public class TestDisjointParamSetDiffInputNames {
	private int testVar1;
	private int testVar2;
	private double workingVar1;
	private double workingVar2;
	
	public TestDisjointParamSetDiffInputNames(int inputVar1, int inputVar2){
		this.testVar1 = inputVar1;
		this.testVar2 = inputVar2;
		this.workingVar1 = 0.0;
		this.workingVar2 = 0.0;
	
	}
	
	public TestDisjointParamSetDiffInputNames(double workingInputVar1, double workingInputVar2){
		this.testVar1 = 0;
		this.testVar2 = 0;
		this.workingVar1 = workingInputVar1;
		this.workingVar2 = workingInputVar2;
	}
}
