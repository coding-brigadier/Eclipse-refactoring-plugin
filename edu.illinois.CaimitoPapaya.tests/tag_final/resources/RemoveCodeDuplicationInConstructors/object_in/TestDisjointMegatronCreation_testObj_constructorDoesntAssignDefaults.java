package object_in;

public class TestDisjointMegatronCreation_testObj_constructorDoesntAssignDefaults {
	private String testVar1;
	private String testVar2;
	private String testVar3;


	public TestDisjointMegatronCreation_testObj_constructorDoesntAssignDefaults(String testVar1) {
		this.testVar1 = testVar1;
	}

	public TestDisjointMegatronCreation_testObj_constructorDoesntAssignDefaults(String testVar2, String testVar3) {
		this.testVar2 = testVar2;
		this.testVar3 = testVar3;
	}
}