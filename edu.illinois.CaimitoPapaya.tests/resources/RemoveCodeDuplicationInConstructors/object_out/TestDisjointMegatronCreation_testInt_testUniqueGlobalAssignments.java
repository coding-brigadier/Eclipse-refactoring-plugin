package object_in;

public class TestDisjointMegatronCreation_testInt_testUniqueGlobalAssignments {
	private int testVar1;
	private int testVar2;
	private int testVar3;

	
	public TestDisjointMegatronCreation_testInt_testUniqueGlobalAssignments(int Var1) {
		this(Var1, 0, 0);
	}
	
	public TestDisjointMegatronCreation_testInt_testUniqueGlobalAssignments(int Var2, int Var3) {
		this(0, Var2, Var3);
	}

	public TestDisjointMegatronCreation_testInt_testUniqueGlobalAssignments(int Var1, int Var2, int Var3) {
		this.testVar1 = Var1;
		this.testVar2 = Var2;
		this.testVar3 = Var3;
	}
}