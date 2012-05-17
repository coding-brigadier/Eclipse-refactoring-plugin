package object_in;

public class TestDisjointMegatronCreation_testMixedParamTypes {
	private int testVar1;
	private int testVar2;
	private double workingVar1;
	private String workingVar2;

	public TestDisjointMegatronCreation_testMixedParamTypes(int inputVar1, int inputVar2) {
		this.testVar1 = inputVar1;
		this.testVar2 = inputVar2;
	}

	public TestDisjointMegatronCreation_testMixedParamTypes(double workingInputVar1, String workingInputVar2) {
		this.workingVar1 = workingInputVar1;
		this.workingVar2 = workingInputVar2;
	}
}