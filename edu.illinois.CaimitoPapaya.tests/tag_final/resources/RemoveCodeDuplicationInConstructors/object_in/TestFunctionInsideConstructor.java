package object_in;

public class TestFunctionInsideConstructor {
	private int testVar1;
	private String testVar2;
	private double testVar3;
	private double testVar4;

	public int testFunc(){ return 0; }

	public TestFunctionInsideConstructor(int testVar1, String testVar2) {
		this.testVar1 = testVar1;
		this.testVar2 = testVar2;
		testFunc();
	}

	public TestFunctionInsideConstructor(double testVar3, double testVar4) {
		this.testVar3 = testVar3;
		this.testVar4 = testVar4;
	}
}
