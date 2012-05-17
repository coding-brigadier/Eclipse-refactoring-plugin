package object_in;

public class TestOverlap_OneOverlap {
	private int testVar1;
	private String testVar2;
	private double testVar3;
	private double testVar4;

	public TestOverlap_OneOverlap(int inputVar1, String inputVar2) {
		this.testVar1 = inputVar1;
		this.testVar2 = inputVar2;
	}

	public TestOverlap_OneOverlap(int inputVar1, double inputVar3, double inputVar4) {
		this.testVar1 = inputVar1;
		this.testVar3 = inputVar3;
		this.testVar4 = inputVar4;
	}
}