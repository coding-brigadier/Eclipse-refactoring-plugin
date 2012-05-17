package object_in;

public class TestDisjointMegatronCreation_testInt {
	private int testVar1;
	private int testVar2;
	private int testVar3;

	public TestDisjointMegatronCreation_testInt(int testVar1) {
		this(testVar1, 0, 0);
	}
	
	public TestDisjointMegatronCreation_testInt(int testVar2, int testVar3) {
		this(0, testVar2, testVar3);
	}

	public TestDisjointMegatronCreation_testInt(int testVar1, int testVar2, int testVar3) {
		this.testVar1 = testVar1;
		this.testVar2 = testVar2;
		this.testVar3 = testVar3;
	}
}