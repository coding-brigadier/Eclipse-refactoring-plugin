package edu.illinois.CaimitoPapaya;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.jdt.core.IField;
import org.eclipse.jdt.core.refactoring.IJavaRefactorings;
import org.eclipse.jdt.core.refactoring.descriptors.JavaRefactoringDescriptor;
import org.eclipse.jdt.internal.corext.refactoring.changes.DynamicValidationRefactoringChange;
import org.eclipse.jdt.internal.corext.refactoring.changes.DynamicValidationStateChange;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.Refactoring;
import org.eclipse.ltk.core.refactoring.RefactoringDescriptor;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;


public class RemoveCodeDuplicationInConstructors extends Refactoring {

	private final IField field;

	public RemoveCodeDuplicationInConstructors(IField field) {
		this.field = field;
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "RemoveCodeDuplicationInConstructors";
	}

	@Override
	public RefactoringStatus checkInitialConditions(IProgressMonitor pm)
			throws CoreException, OperationCanceledException {
		// TODO Auto-generated method stub
		return new RefactoringStatus();
	}

	@Override
	public RefactoringStatus checkFinalConditions(IProgressMonitor pm)
			throws CoreException, OperationCanceledException {
		// TODO Auto-generated method stub
		
		// I hardcoded the name of the field "f" to illustrate how you test for cases 
		// when the input file does not meet the refactoring preconditions
		if (field.getElementName().equals("f"))
			return RefactoringStatus.createErrorStatus("I don't like to refactor field " + field.getElementName());
		
		return new RefactoringStatus();
	}

	@Override
	public Change createChange(IProgressMonitor pm) throws CoreException,
			OperationCanceledException {
		// TODO Auto-generated method stub
		final Map arguments= new HashMap();
		String description = "RemoveCodeDuplicationInConstructors";
		String comment = "RemoveCodeDuplicationInConstructors removes duplicated code from multiple constructors";
		int flags= JavaRefactoringDescriptor.JAR_MIGRATION | JavaRefactoringDescriptor.JAR_REFACTORING | RefactoringDescriptor.STRUCTURAL_CHANGE | RefactoringDescriptor.MULTI_CHANGE;
		
		final JavaRefactoringDescriptor descriptor= new JavaRefactoringDescriptor("ourID", field.getJavaProject().getElementName(), description, comment, arguments, flags) {};
	
		return new DynamicValidationRefactoringChange(descriptor, getName());
	}

}
