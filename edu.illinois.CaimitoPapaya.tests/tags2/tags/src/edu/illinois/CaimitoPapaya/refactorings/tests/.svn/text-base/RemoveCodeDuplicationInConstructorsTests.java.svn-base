package edu.illinois.CaimitoPapaya.refactorings.tests;

import junit.framework.Test;
import junit.framework.TestSuite;

import edu.illinois.CaimitoPapaya.RemoveCodeDuplicationInConstructors;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Preferences;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IField;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.internal.corext.refactoring.Checks;
import org.eclipse.jdt.ui.tests.refactoring.infra.AbstractSelectionTestCase;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;

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
	
	protected String adaptName(String name) {
		return Character.toUpperCase(name.charAt(0)) + name.substring(1) + ".java";
	}	
	
	protected void performTest(IPackageFragment packageFragment, String id, String outputFolder, String fieldName) throws Exception {
		ICompilationUnit unit= createCU(packageFragment, id);
		IField field= getField(unit, fieldName);
		assertNotNull(field);
		
		initializePreferences();

		RemoveCodeDuplicationInConstructors refactoring= new RemoveCodeDuplicationInConstructors(field);
		performTest(unit, refactoring, COMPARE_WITH_OUTPUT, getProofedContent(outputFolder, id), true);
	}

	protected void performInvalidTest(IPackageFragment packageFragment, String id, String fieldName) throws Exception {
		ICompilationUnit unit= createCU(packageFragment, id);
		IField field= getField(unit, fieldName);
		assertNotNull(field);

		initializePreferences();

		RemoveCodeDuplicationInConstructors refactoring= new RemoveCodeDuplicationInConstructors(field);
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
	
	private static IField getField(ICompilationUnit unit, String fieldName) throws Exception {
		IField result= null;
		IType[] types= unit.getAllTypes();
		for (int i= 0; i < types.length; i++) {
			IType type= types[i];
			result= type.getField(fieldName);
			
			if (result != null && result.exists())
				break;
		}
		return result;
	}

	private void objectTest(String fieldName) throws Exception {
		performTest(fgTestSetup.getObjectPackage(), getName(), "object_out", fieldName);
	}
	
	
	private void invalidTest(String fieldName) throws Exception {
		performInvalidTest(fgTestSetup.getInvalidPackage(), getName(), fieldName);
	}
	
	//=====================================================================================
	// Basic Object Test: you should add your passing tests below
	//=====================================================================================
	
	public void testReadAccess() throws Exception {
		objectTest("myField");
	}
	
	
	//------------------------- Cases below do not meet preconditions - therefore refactoring should not proceed
	
	public void testCannotRefactorDueToXYZ() throws Exception {
		invalidTest("f");
	}
}
