package invalid;

public class TestCannotRefactorDueToExistingMegatronConstructor {
	private int testVar1;
	private String testVar2;
	private double testVar3;
	private double testVar4;
	
	public TestCannotRefactorDueToExistingMegatronConstructor(int inputVar1, String inputVar2, double inputVar3, double inputVar4){
		testVar1 = 0;
		testVar2 = null;
		testVar3 = 0.0;
		testVar4 = 0.0;
	}
	
	public TestCannotRefactorDueToExistingMegatronConstructor(int inputVar1, String inputVar2){
		testVar1 = 0;
		testVar2 = null;	
	}
}
