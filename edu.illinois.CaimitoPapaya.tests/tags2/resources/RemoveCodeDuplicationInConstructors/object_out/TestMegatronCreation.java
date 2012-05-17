package object_in;

public class TestMegatronCreation {
	private int testVar1;
	private int testVar2;
	private double testVar3;
	private double testVar4;
	
	public TestMegatronCreation(int testVar1, int testVar2){
		this(testVar1, testVar2, 0.0, 0.0);
	}
	
	public TestMegatronCreation(double testVar3, double testVar4){
		this(0, 0, testVar3, testVar4);
	}

	public TestMegatronCreation(int testVar1, int testVar2, double testVar3, double testVar4) {
		this.testVar1 = testVar1;
		this.testVar2 = testVar2;
		this.testVar3 = testVar3;
		this.testVar4 = testVar4;
	}
}
