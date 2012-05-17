package edu.illinois.CaimitoPapaya;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jdt.core.IClassFile;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaModel;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.ITypeRoot;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.internal.corext.refactoring.RefactoringExecutionStarter;
import org.eclipse.jdt.internal.corext.refactoring.util.JavaElementUtil;
import org.eclipse.jdt.internal.corext.refactoring.util.RefactoringASTParser;
import org.eclipse.jdt.internal.ui.actions.ActionUtil;
import org.eclipse.jdt.internal.ui.actions.SelectionConverter;
import org.eclipse.jdt.internal.ui.javaeditor.JavaEditor;
import org.eclipse.jdt.internal.ui.refactoring.RefactoringMessages;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IBlockTextSelection;
import org.eclipse.jface.text.IMarkSelection;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.text.edits.MalformedTreeException;
import org.eclipse.ui.ISelectionService;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.jface.dialogs.MessageDialog;

/**
 * Our sample action implements workbench action delegate.
 * The action proxy will be created by the workbench and
 * shown in the UI. When the user tries to use the action,
 * this delegate will be created and execution will be 
 * delegated to it.
 * @see IWorkbenchWindowActionDelegate
 */
public class RemoveCodeDuplicationInConstructorsRefactoringAction implements IWorkbenchWindowActionDelegate {
	private IWorkbenchWindow window;
	private JavaEditor fEditor;
	private ITextSelection selection;
	
	/**
	 * The constructor.
	 */
	public RemoveCodeDuplicationInConstructorsRefactoringAction() {
		super();
	}
	
	public RemoveCodeDuplicationInConstructorsRefactoringAction(JavaEditor editor) {
		super();
		fEditor = editor;
	}

	/**
	 * The action has been activated. The argument of the
	 * method represents the 'real' action sitting
	 * in the workbench UI. 
	 * @see IWorkbenchWindowActionDelegate#run
	 */
	@Override
	public void run(IAction action) {
		ISelectionService service = window.getActivePage().getWorkbenchWindow().getSelectionService();
		
		IStructuredSelection structured = 
				(IStructuredSelection) service.getSelection("org.eclipse.jdt.ui.PackageExplorer");
		
		// Check selection is a Compilation Unit
		String PESelection = structured.getFirstElement().getClass().getName();
		if (PESelection.equals("org.eclipse.jdt.internal.core.CompilationUnit")){
			ICompilationUnit cu = (ICompilationUnit) structured.getFirstElement();
			String print = "";
			try {
				print = cu.getSource();
			} catch (JavaModelException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				RemoveCodeDuplicationInConstructorsRefactoring refactoring = new RemoveCodeDuplicationInConstructorsRefactoring(null, cu);
			} catch (MalformedTreeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (CoreException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (BadLocationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else{
			MessageDialog.openInformation(
					window.getShell(),
					"Error: Package Explorer",
					"Please select a Compilation Unit in the Package Explorer");
		}
	}
	
	/**
	 * Selection in the workbench has been changed. We 
	 * can change the state of the 'real' action here
	 * if we want, but this can only happen after 
	 * the delegate has been created.
	 * @see IWorkbenchWindowActionDelegate#selectionChanged
	 */
	public void selectionChanged(IAction action, ISelection selection) {
		
	}

	/**
	 * We can use this method to dispose of any system
	 * resources we previously allocated.
	 * @see IWorkbenchWindowActionDelegate#dispose
	 */
	public void dispose() {
	}

	/**
	 * We will cache window object in order to
	 * be able to provide parent shell for the message dialog.
	 * @see IWorkbenchWindowActionDelegate#init
	 */
	public void init(IWorkbenchWindow window) {
		this.window = window;
	}
}