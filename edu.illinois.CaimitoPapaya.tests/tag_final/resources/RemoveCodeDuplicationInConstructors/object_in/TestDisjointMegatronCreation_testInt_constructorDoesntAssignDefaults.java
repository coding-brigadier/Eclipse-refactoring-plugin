package object_in;

public class TestDisjointMegatronCreation_testInt_constructorDoesntAssignDefaults {
	private int testVar1;
	private int testVar2;
	private int testVar3;


	public TestDisjointMegatronCreation_testInt_constructorDoesntAssignDefaults(int testVar1) {
		this.testVar1 = testVar1;
	}

	public TestDisjointMegatronCreation_testInt_constructorDoesntAssignDefaults(int testVar2, int testVar3) {
		this.testVar2 = testVar2;
		this.testVar3 = testVar3;
	}
}