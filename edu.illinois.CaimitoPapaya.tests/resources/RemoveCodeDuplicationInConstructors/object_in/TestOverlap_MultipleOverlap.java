package object_in;

public class  TestOverlap_MultipleOverlap {
	private int testVar1;
	private int testVar2;
	private double testVar3;
	private double testVar4;

	public TestOverlap_MultipleOverlap(int inputVar1, int inputVar2, double inputVar4) {
		this.testVar1 = inputVar1;
		this.testVar2 = inputVar2;
		this.testVar4 = inputVar4;
	}

	public TestOverlap_MultipleOverlap(int inputVar2, double inputVar3, double inputVar4) {
		this.testVar2 = inputVar2;
		this.testVar3 = inputVar3;
		this.testVar4 = inputVar4;
	}
}