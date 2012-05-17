package edu.illinois.CaimitoPapaya.refactorings.tests;

import junit.framework.Test;
import junit.framework.TestSuite;

import edu.illinois.CaimitoPapaya.RemoveCodeDuplicationInConstructorsRefactoring;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Preferences;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IField;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.ui.tests.refactoring.infra.AbstractSelectionTestCase;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;

@SuppressWarnings("deprecation")
public class RemoveCodeDuplicationInConstructorsTests extends AbstractSelectionTestCase {

	private static RemoveCodeDuplicationInConstructorsTestSetup fgTestSetup;
	
	public RemoveCodeDuplicationInConstructorsTests(String name) {
		super(name);
	}
	
	public static Test suite() {
		fgTestSetup= new RemoveCodeDuplicationInConstructorsTestSetup(new TestSuite(RemoveCodeDuplicationInConstructorsTests.class));
		return fgTestSetup;
	}
	
	public static Test setUpTest(Test test) {
		fgTestSetup= new RemoveCodeDuplicationInConstructorsTestSetup(test);
		return fgTestSetup;
	}
	
	protected void setUp() throws Exception {
		super.setUp();
		fIsPreDeltaTest= true;
	}

	protected String getResourceLocation() {
		return "RemoveCodeDuplicationInConstructors/";
	}
	
	/**
	 * Capitalizes input string and appends ".java" to it
	 */
	protected String adaptName(String name) {
		return Character.toUpperCase(name.charAt(0)) + name.substring(1) + ".java";
	}	
	
	/**
	 * Performs the refactoring on the input object identified by the package 
	 * fragment it's in and the test case's name, then compares the output to 
	 * the corresponding file in the specified outputFolder
	 * 
	 * @param packageFragment - represents a package or a part of a package in 
	 *    the workspace where the input object is located
	 * @param id - name of actual test case
	 * @param outputFolder - name of folder where expected outputs are stored
	 * @param fieldName - name of input and output test object
	 * @throws Exception
	 */
	protected void performTest(IPackageFragment packageFragment, String id, String outputFolder, String fieldName) throws Exception {
		ICompilationUnit unit= createCU(packageFragment, id); //represents the input test object's source code; includes imports, class name and all its variables and methods
		IField field= getField(unit, fieldName); //represents the input test object's variables
		System.out.println(field.toString());

		assertNotNull(field); //check that class has variables
		
		initializePreferences();
		RemoveCodeDuplicationInConstructorsRefactoring refactoring= new RemoveCodeDuplicationInConstructorsRefactoring(field, unit);
		performTest(unit, refactoring, COMPARE_WITH_OUTPUT, getProofedContent(outputFolder, id), true);
	}

	/**
	 * Takes the invalid package name, the name of the test case, and the name 
	 *    of the test object and runs the refactoring after creating the 
	 *    representations of the source code 
	 * @param packageFragment - the package where the invalid test objects are 
	 *    located
	 * @param id - name of the test case
	 * @param fieldName - name of the test object
	 * @throws Exception
	 */
	protected void performInvalidTest(IPackageFragment packageFragment, String id, String fieldName) throws Exception {
		ICompilationUnit unit= createCU(packageFragment, id);
		IField field= getField(unit, fieldName);
		assertNotNull(field);

		initializePreferences();

		RemoveCodeDuplicationInConstructorsRefactoring refactoring= new RemoveCodeDuplicationInConstructorsRefactoring(field, unit);
		if (refactoring != null) {
			RefactoringStatus status= refactoring.checkAllConditions(new NullProgressMonitor());
			assertTrue(status.hasError());
			assertEquals(1, status.getEntries().length);
		}
	}	
	
	private void initializePreferences() {
		Preferences preferences= JavaCore.getPlugin().getPluginPreferences();
		preferences.setValue(JavaCore.CODEASSIST_FIELD_PREFIXES, "");
		preferences.setValue(JavaCore.CODEASSIST_STATIC_FIELD_PREFIXES, "");
		preferences.setValue(JavaCore.CODEASSIST_FIELD_SUFFIXES, "");
		preferences.setValue(JavaCore.CODEASSIST_STATIC_FIELD_SUFFIXES, "");
	}
	
	/**
	 * 
	 * @param unit
	 * @param fieldName
	 * @return
	 * @throws Exception
	 */
	
	private static IField getField(ICompilationUnit unit, String fieldName) throws Exception {
		IField result= null;
		IType[] types= unit.getAllTypes();
		for (int i= 0; i < types.length; i++) {
			IType type= types[i];
			result= type.getField(fieldName);
			System.out.println("DEBUG1: " + result.toString());
			//System.out.println("DEBUG2: " + types[i].toString());
			
			if (result != null && result.exists())
				break;
		}
		return result;
	}

	/**
	 * Takes the name of the input and output test object and performs the 
	 * refactoring on the input object, and then compares the result with the 
	 * expected output object
	 * 
	 * Wrapper function for performTest
	 * 
	 * @param fieldName - name of the input and output test object
	 * @throws Exception
	 */
	private void objectTest(String fieldName) throws Exception {
		performTest(fgTestSetup.getObjectPackage(), getName(), "object_out", fieldName);
	}
	
	/**
	 * takes the name of the input/output test object and calls performInvalidTest
	 * @param fieldName - the name of the test object
	 * @throws Exception
	 */
	private void invalidTest(String fieldName) throws Exception {
		performInvalidTest(fgTestSetup.getInvalidPackage(), getName(), fieldName);
	}
	
	//=====================================================================================
	// Basic Object Test: you should add your passing tests below
	//=====================================================================================
	
	/** Preliminary Cases **/	
	public void testSingleConstructor() throws Exception {
		objectTest("TestSingleConstructor");
	}
	
	
	/** Disjoint Cases **/
	public void testDisjointMegatronCreation_testInt() throws Exception {
		objectTest("TestDisjointMegatronCreation_testInt");
	}
	
	public void testDisjointMegatronCreation_testInt_testUniqueGlobalAssignments() throws Exception {
		objectTest("TestDisjointMegatronCreation_testInt_testUniqueGlobalAssignments");
	}
	
	public void testDisjointMegatronCreation_testInt_testUniqueGlobalAssignments_constructorDoesntAssignDefaults() throws Exception {
		objectTest("TestDisjointMegatronCreation_testInt_testUniqueGlobalAssignments_constructorDoesntAssignDefaults");
	}

	public void testDisjointMegatronCreation_testInt_constructorDoesntAssignDefaults() throws Exception {
		objectTest("TestDisjointMegatronCreation_testInt_constructorDoesntAssignDefaults");
	}
	
	public void testDisjointMegatronCreation_testDouble_constructorDoesntAssignDefaults() throws Exception {
		objectTest("TestDisjointMegatronCreation_testDouble_constructorDoesntAssignDefaults");
	}
	
	public void testDisjointMegatronCreation_testObj_constructorDoesntAssignDefaults() throws Exception {
		objectTest("TestDisjointMegatronCreation_testObj_constructorDoesntAssignDefaults");
	}
	
	public void testDisjointMegatronCreation_testMixedParamTypes() throws Exception {
		objectTest("TestDisjointMegatronCreation_testMixedParamTypes");
	}
	

	/** Test Overlap **/
	public void testOverlap_MultipleOverlap() throws Exception {
		objectTest("TestOverlap_MultipleOverlap");
	}
	
	public void testOverlap_OneOverlap() throws Exception {
		objectTest("TestOverlap_OneOverlap");
	}
	
	public void testSubset_OneOverlap() throws Exception {
		objectTest("TestSubset_OneOverlap");
	}
	
	
//	/** Test Assign **/
//	public void testAssignValUsingCalculationWithMultipleParams() throws Exception {
//		objectTest("TestAssignValUsingCalculationWithMultipleParams");
//	}
//	
//	public void testAssignValUsingCalculationWithSingleParams() throws Exception {
//		objectTest("TestAssignValUsingCalculationWithSingleParams");
//	}
//	
//	
	/** Test other **/
	public void testFunctionInsideConstructor() throws Exception {
		objectTest("TestFunctionInsideConstructor");
	}
	
	
	//------------------------- Cases below do not meet preconditions - therefore refactoring should not proceed
	
	public void testCannotRefactorDueToSingleConstructor() throws Exception {
		invalidTest("TestCannotRefactorDueToSingleConstructor");
	}
	
	public void testCannotRefactorDueToNoConstructor() throws Exception {
		invalidTest("TestCannotRefactorDueToNoConstructor");
	}
	
//	public void testCannotRefactorDueToExistingMegatronConstructor() throws Exception {
//		invalidTest("TestCannotRefactorDueToExistingMegatronConstructor");
//	}
}
