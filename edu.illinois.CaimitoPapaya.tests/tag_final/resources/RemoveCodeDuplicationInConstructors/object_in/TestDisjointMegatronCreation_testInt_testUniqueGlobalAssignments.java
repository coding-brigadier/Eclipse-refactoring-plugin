package object_in;

public class TestDisjointMegatronCreation_testInt_testUniqueGlobalAssignments {
	private int testVar1;
	private int testVar2;
	private int testVar3;

	
	public TestDisjointMegatronCreation_testInt_testUniqueGlobalAssignments(int Var1) {
		this.testVar1 = Var1;
		this.testVar2 = 0;
		this.testVar3 = 0;
	}
	
	public TestDisjointMegatronCreation_testInt_testUniqueGlobalAssignments(int Var2, int Var3) {
		this.testVar1 = 0;
		this.testVar2 = Var2;
		this.testVar3 = Var3;
	}
}