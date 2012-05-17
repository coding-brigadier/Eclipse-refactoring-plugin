package edu.illinois.CaimitoPapaya;


import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IField;
import org.eclipse.jdt.core.dom.*;
import org.eclipse.jdt.core.refactoring.descriptors.JavaRefactoringDescriptor;
import org.eclipse.jdt.internal.corext.refactoring.changes.DynamicValidationRefactoringChange;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.Document;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.Refactoring;
import org.eclipse.ltk.core.refactoring.RefactoringDescriptor;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ltk.core.refactoring.RefactoringStatusEntry;
import org.eclipse.text.edits.MalformedTreeException;
import org.eclipse.text.edits.TextEdit;




@SuppressWarnings("restriction")
public class RemoveCodeDuplicationInConstructorsRefactoring extends Refactoring {

	private AST ast;
	private final IField field;
	private ASTConstructorVisitor visitor;
	private Boolean hasSingleConstructor;
	
	/**
	 * Set up and run refactoring
	 * @param field
	 * @param unit
	 * @throws CoreException
	 * @throws MalformedTreeException
	 * @throws BadLocationException
	 */
	public RemoveCodeDuplicationInConstructorsRefactoring(IField field, ICompilationUnit unit) throws CoreException, MalformedTreeException, BadLocationException {
		this.field = field;
		hasSingleConstructor = false;		
		
		// creation of a Document
		String source = unit.getBuffer().getContents();
		Document document= new Document(source);
	
		// creation of DOM/AST from a ICompilationUnit
		ASTParser parser = ASTParser.newParser(AST.JLS3);
		parser.setSource(unit);
		CompilationUnit astCU = (CompilationUnit) parser.createAST(null);
		ast = astCU.getAST();
	
		// start record of the modifications
		astCU.recordModifications();
	
		visitor = new ASTConstructorVisitor();
		astCU.accept(visitor);
		
		// modify the AST
		refactor();
		
		// computation of the text edits
		TextEdit edits = astCU.rewrite(document, unit.getJavaProject().getOptions(true));
	
		// computation of the new source code
		edits.apply(document);
		String newSource = document.get();
	
		// update of the compilation unit
		unit.getBuffer().setContents(newSource);
	
	}

	/**
	 * The name of our refactoring project.
	 */
	@Override
	public String getName() {
		return "RemoveCodeDuplicationInConstructors";
	}

	/**
	 * Check the conditions BEFORE refactoring.
	 */
	@Override
	public RefactoringStatus checkInitialConditions(IProgressMonitor pm) {
		LinkedList<MethodDeclaration> constructors = visitor.getConstructors();
		if(constructors.size() < 2){
			hasSingleConstructor = true;
		}
		try {
			pm.beginTask("Remove Code Duplications in Constructors", 
					IProgressMonitor.UNKNOWN);
			if(hasSingleConstructor)
				return RefactoringStatus.createErrorStatus("Has less than 2 constructors");
		}
		catch(OperationCanceledException oce) {
			pm.done();
			RefactoringStatus.createErrorStatus(
					"The task has been cancelled");
		}
		return new RefactoringStatus();
	}

	/**
	 * Check the conditions AFTER refactoring.
	 */
	@Override
	public RefactoringStatus checkFinalConditions(IProgressMonitor pm)
			throws CoreException, OperationCanceledException {
		
		// I hardcoded the name of the field "f" to illustrate how you test for cases 
		// when the input file does not meet the refactoring preconditions
		if (field.getElementName().equals("f"))
			return RefactoringStatus.createStatus(RefactoringStatus.ERROR, "I don't like to refactor field " + field.getElementName(), null, null, RefactoringStatusEntry.NO_CODE, null);
		//else if(hasSingleConstructor)
		//	return RefactoringStatus.createErrorStatus("Has less than 2 constructors");
		return new RefactoringStatus();
	}

	
	@SuppressWarnings({ "rawtypes" })
	@Override
	public Change createChange(IProgressMonitor pm) throws CoreException,
			OperationCanceledException {
		final Map arguments= new HashMap();
		String description = "RemoveCodeDuplicationInConstructors";
		String comment = "RemoveCodeDuplicationInConstructors removes duplicated code from multiple constructors";
		int flags= JavaRefactoringDescriptor.JAR_MIGRATION | JavaRefactoringDescriptor.JAR_REFACTORING | RefactoringDescriptor.STRUCTURAL_CHANGE | RefactoringDescriptor.MULTI_CHANGE;
		
		final JavaRefactoringDescriptor descriptor= new JavaRefactoringDescriptor("ourID", field.getJavaProject().getElementName(), description, comment, arguments, flags) {};
	
		return new DynamicValidationRefactoringChange(descriptor, getName());
	}
	
	/**
	 * Return an intersection of two lists.
	 * @param parameters1 - a list of parameters.
	 * @param parameters2 - a list of parameters.
	 * @return an intersection of the two lists.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private List intersection(List parameters1, List parameters2) {
		SingleVariableDeclaration param1, param2;
		String param1Str, param2Str;
		List intersection = new LinkedList();

		int numParams1 = parameters1.size();
		int numParams2 = parameters2.size();
		for(int i = 0; i < numParams1; i++) {
			param1 = (SingleVariableDeclaration) parameters1.get(i);
			param1Str = param1.toString();
			for(int j = 0; j < numParams2; j++) {
				param2 = (SingleVariableDeclaration) parameters2.get(i);
				param2Str = param2.toString();
				if(param1Str.equals(param2Str)) {
					intersection.add(param1);
					break;
				}
					
			}
		}
		
		return intersection;
	}
	
	
	/**
	 * Get the union of two lists.
	 * @param parameters1 - a list of parameters.
	 * @param parameters2 - a list of parameters.
	 * @param ast - the representation of an AST tree. We will add the union as a
	 * new node for our new MethodDeclaration (or Constructor).
	 * @return the union of the two lists.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private List union(List parameters1, List parameters2) {
		SingleVariableDeclaration currVar, newVar;
		
		boolean exists = false;
		
		
		List unioned = new LinkedList();
		
		int numParams1 = parameters1.size();
		int numParams2 = parameters2.size();
		for(int i = 0; i < numParams1; i++) {
			currVar = 
					(SingleVariableDeclaration) parameters1.get(i);
			newVar = 
					(SingleVariableDeclaration) ASTNode.copySubtree(ast, currVar);
			unioned.add(newVar);
		}
		
		// We need to check that the parameters are not repetitive.
		for(int i = 0; i < numParams2; i++) {
			currVar = 
					(SingleVariableDeclaration) parameters2.get(i);
			String currVarStr = currVar.toString();
			
			exists = false;
			for(int j = 0; j < numParams1; j++) {
				String unionedParam = unioned.get(j).toString();
				if(unionedParam.equals(currVarStr)) {
					exists = true;
					break;
				}
			}
			
			if(!exists) {
				newVar = (SingleVariableDeclaration) ASTNode.copySubtree(ast, currVar);
				unioned.add(newVar);
			}
		}
		
		return unioned;
	}
	
	/**
	 * Create empty Megatron constructor
	 * @param td
	 * @return
	 */
	@SuppressWarnings( {"unchecked", "rawtypes" } )
	private MethodDeclaration createNewConstructor(TypeDeclaration td) {
		// Create our new constructor in the AST
		MethodDeclaration md = ast.newMethodDeclaration();
		md.setConstructor(true);
		
		SimpleName tdSimpleName = td.getName();
		String className = tdSimpleName.getFullyQualifiedName();
		SimpleName astSimpleName = ast.newSimpleName(className);
		
		// Name our constructor
		md.setName(astSimpleName);
		
		List modifiers = md.modifiers();
		Modifier newModifier = 
				ast.newModifier(Modifier.ModifierKeyword.PUBLIC_KEYWORD);
		modifiers.add(newModifier);
		Block emptyBody = ast.newBlock();
		md.setBody(emptyBody);
		return md;
	}
	

	/**
	 * Get the constructor statements.
	 * @param constructor
	 * @return - the constructor statements.
	 */
	@SuppressWarnings("rawtypes")
	private List getConstructorStatements(MethodDeclaration constructor) {
		Block body = constructor.getBody();
		return body.statements();
	}
	
	/**
	 * Check is an ExpressionStatement is found in a constructor. 
	 * @param constructor - the constructor in which we are looking.
	 * @param expressionStatement - the ExpressionStatement we are looking for.
	 * @return - true or false, depending of the statement was found or not.
	 */
	private boolean statementFound(MethodDeclaration constructor,
			ExpressionStatement expressionStatement) {
		int statementsSize =
				constructor.getBody().statements().size();
		
		String newExpStStr = 
				expressionStatement.toString();
		for(int k = 0; k < statementsSize; k++) {
			ExpressionStatement currStatDuplicate =
					(ExpressionStatement) 
					constructor.getBody().statements().
					get(k);
			String currStatDuplicateStr = currStatDuplicate.toString();
			if(newExpStStr.equals(currStatDuplicateStr)) {
				return true;
			}
		}
		return false;
	}

	
	/**
	 * Add new assignment to the constructor statements
	 * @param assignment - the assignment to be added.
	 * @param constructor - the constructor to which the assignment is being added.
	 */
	@SuppressWarnings("unchecked")
	private void addNewAssignment(Assignment assignment, 
			MethodDeclaration constructor) {
		int nodeType;
		Expression LHS = assignment.getLeftHandSide();
		Assignment newExp = 
				(Assignment) ASTNode.copySubtree(ast, assignment);
		
		
		nodeType = LHS.getNodeType();
		if(nodeType == ASTNode.FIELD_ACCESS) {
			FieldAccess newFA = 
					(FieldAccess) ASTNode.copySubtree(ast, LHS);
			newExp.setLeftHandSide(newFA);
			
		}
		
		ExpressionStatement newExpSt =
				ast.newExpressionStatement(newExp);
		
		if(!statementFound(constructor, newExpSt)) {
			constructor.getBody().
				statements().add(newExpSt);
		}
	}
	

	/**
	 * Goes through constructor body and takes out statements and moves into megatron
	 * @param newConstructor - destination constructor (Megatron)
	 * @param oldConstructor - source constructor
	 * @param ast - ast tree
	 */
	@SuppressWarnings({ "rawtypes" })
	private void copyParamsAndMoveExpressions(
			MethodDeclaration newConstructor, 
			MethodDeclaration oldConstructor) {
		int nodeType;
		
		List constructorStatements = getConstructorStatements(oldConstructor);
		List parameters = oldConstructor.parameters();
		
		int paramSize = parameters.size();
		int numStatements = 
				constructorStatements.size();
		
		// Type 'ExpressionStatement' might change
		// to cover loops, conditional so on
		for(int i = 0; i < paramSize; i++) {
			VariableDeclaration currParam = 
					(VariableDeclaration) parameters.get(i);
			SimpleName paramName = currParam.getName();
			for(int j = 0; j < numStatements; j++) {
				ExpressionStatement currStat =
						(ExpressionStatement)constructorStatements.get(j);
				nodeType = currStat.getExpression().getNodeType();
				
				// Our expression in the constructor
				if(nodeType == ASTNode.ASSIGNMENT) {

					Assignment currExp = (Assignment) currStat.getExpression();
					Expression RHS = currExp.getRightHandSide();
					nodeType = RHS.getNodeType();
					
					
					// The Right Hand Side Expression
					if(nodeType == ASTNode.SIMPLE_NAME) {
						
						String strRHS = RHS.toString();
						String strParam = paramName.toString();
						
						// WE FOUND A MATCH!
						if(strRHS.equals(strParam)) {
							addNewAssignment(currExp, newConstructor);
							oldConstructor.getBody().
								statements().remove(currStat);
							numStatements--;
							j--;
						}
					} // end if
					else if(nodeType == ASTNode.NUMBER_LITERAL) {
						String RHSVal = RHS.toString();
						if(RHSVal.equals("0") ||
								RHSVal.equals("0.0") ||
								RHSVal.equals("null")) {
							oldConstructor.getBody().statements().remove(currStat);
							numStatements--;
							j--;
						}
					} // end else if
				}
			} // end for
		}	
	}
	
	/**
	 * refactors two constructors that contain Distinct Parameter sets
	 * @param constructor1 - first constructor to refactor
	 * @param constructor2 - second constructor to refactor
	 * @param ast - ast tree
	 */
	@SuppressWarnings({ "unused", "unchecked" })
	private void refactorDistinctAndOverlappedParams(MethodDeclaration constructor1, 
			MethodDeclaration constructor2) {
		int nodeType;
		
		TypeDeclaration td = visitor.getClassname();
		
		MethodDeclaration md = createNewConstructor(td);

		copyParamsAndMoveExpressions(md, constructor1);
		copyParamsAndMoveExpressions(md, constructor2);
		
		List<SingleVariableDeclaration> parameters1 = constructor1.parameters();
		List<SingleVariableDeclaration> parameters2 = constructor2.parameters();
		
		List<SingleVariableDeclaration> allParams = union(parameters1, parameters2);
		int totalNumParams = allParams.size();
		
		FieldDeclaration[] globals = td.getFields();
		
		ConstructorInvocation conInvoc1 = 
				createDisjointConstructor1Invocation(parameters1, allParams);
		constructor1.getBody().statements().add(0,conInvoc1);

		ConstructorInvocation conInvoc2 = 
				createDisjointOrOvelappedConstructor2Invocation(parameters2, allParams);
		
		constructor2.getBody().statements().add(0, conInvoc2);
		
		// Populate the parameter list of the
		// "Megatron" constructor.
		for(int i = 0; i < allParams.size(); i++)
			md.parameters().add(allParams.get(i));
			
		td.bodyDeclarations().add(md);
	}
	
	/**
	 * Creates default values for Constructor Invocation
	 * @param currType - Current parameter type
	 * @param conInvoc - Constructor invocation to edit
	 * @return - updated invocation
	 */
	@SuppressWarnings("unchecked")
	private ConstructorInvocation getconstructorInvocationDefaults(
			Type currType, 
			ConstructorInvocation conInvoc){
		
		// Check if the type is a primitive
		if(currType.isPrimitiveType()) {
			PrimitiveType primType = (PrimitiveType) currType;
			
			// Check if the type is boolean
			if(primType.getPrimitiveTypeCode() == 
					PrimitiveType.BOOLEAN) {
				BooleanLiteral newBool = ast.newBooleanLiteral(false);
				conInvoc.arguments().add(newBool);
			}
			
			// Check if it's one of the number literals.
			else if(primType.getPrimitiveTypeCode() ==
					PrimitiveType.BYTE ||
					primType.getPrimitiveTypeCode() ==
					PrimitiveType.INT ||
					primType.getPrimitiveTypeCode() ==
					PrimitiveType.LONG ||
					primType.getPrimitiveTypeCode() ==
					PrimitiveType.CHAR ||
					primType.getPrimitiveTypeCode() ==
					PrimitiveType.SHORT) {
				NumberLiteral newInt = ast.newNumberLiteral(Integer.toString(0));
				conInvoc.arguments().add(newInt);
			}
			
			// Check if it's one of the decimal number literals.
			else if(primType.getPrimitiveTypeCode() ==
					PrimitiveType.DOUBLE ||
					primType.getPrimitiveTypeCode() ==
					PrimitiveType.FLOAT) {
				NumberLiteral newDecimal = ast.newNumberLiteral(Double.toString(0.0));
				conInvoc.arguments().add(newDecimal);
			}
		}
		
		// Otherwise
		else {
			NullLiteral newNull = ast.newNullLiteral();
			conInvoc.arguments().add(newNull);
		}
		
		return conInvoc;
	}
	
	/**
	 * Creates constructor invocation for constructor 1. 
	 * The first n parameters are non-default, where n is the number of parameters
	 * the first constructor has.
	 * @param params - constructor 1 params
	 * @param allParams - megatron constructor params
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private ConstructorInvocation createDisjointConstructor1Invocation(List<SingleVariableDeclaration> params, 
			List<SingleVariableDeclaration> allParams){
		
		int numParams = params.size();
		int totalNumParams = allParams.size(); 
		ConstructorInvocation conInvoc = ast.newConstructorInvocation();
		
		for(int i = 0; i < totalNumParams; i++) {
			
			if(i < numParams) {
				SingleVariableDeclaration currParam =
						(SingleVariableDeclaration) params.get(i);
				
				SimpleName name = currParam.getName();
				
				SimpleName deepCopyName = 
						(SimpleName) ASTNode.copySubtree(ast, name);

				conInvoc.arguments().add(deepCopyName);
			}
			else {
				SingleVariableDeclaration currMegatronParam =
						(SingleVariableDeclaration) allParams.get(i);
				Type currType = 
						currMegatronParam.getType();
				conInvoc = getconstructorInvocationDefaults(currType, conInvoc);
			}
			
		}
		return conInvoc;
		
	}
	
	/**
	 * Creates constructor invocation for constructor 2. This invocation
	 * might have defaults in the middle to handle any parameter overlaps.
	 * @param params - constructor 2 params
	 * @param allParams - megatron constructor params
	 * @return
	 */
	@SuppressWarnings({ "unchecked" })
	private ConstructorInvocation createDisjointOrOvelappedConstructor2Invocation
			(List<SingleVariableDeclaration> params, 
			 List<SingleVariableDeclaration> allParams){
		
		int numParams = params.size();
		int totalNumParams = allParams.size(); 
		ConstructorInvocation conInvoc = ast.newConstructorInvocation();
		boolean found = false;	
		
		for(int i = 0; i < totalNumParams; i++) {
			found = false;
			SingleVariableDeclaration totalParam =
					(SingleVariableDeclaration) allParams.get(i);
			String totalParamStr = totalParam.toString();
			for(int j = 0; j < numParams; j++) {
				
				SingleVariableDeclaration param2 =
						(SingleVariableDeclaration) params.get(j);
			
				String param2Str = param2.toString();
			
				if(totalParamStr.equals(param2Str)) {
					SimpleName newParam = param2.getName();
					SimpleName newParamCopy = 
							(SimpleName) ASTNode.copySubtree(ast, newParam);
					conInvoc.arguments().add(newParamCopy);
					found = true;
					break;
				}
			}
			
			if(!found) {
				Type currType = 
						totalParam.getType();
				conInvoc = getconstructorInvocationDefaults(currType, conInvoc);
			}
		}
		return conInvoc;
	}
	
	/**
	 * constructor1 parameters is a subset of constructor2 parameters.
	 * @param constructor1
	 * @param constructor2
	 */
	@SuppressWarnings("unchecked")
	private void refactorSubsetParams(
			MethodDeclaration constructor1,
			MethodDeclaration constructor2) {
		
		SingleVariableDeclaration currParam1, currParam2;
		String currParam1Str, currParam2Str;
		
		boolean found = false;
		
		List<SingleVariableDeclaration> parameters1 = constructor1.parameters();
		List<SingleVariableDeclaration> parameters2 = constructor2.parameters();
		
		// We are assuming the "bigger" constructor will do
		// everything the "smaller" does
		constructor1.getBody().statements().clear();
		
		ConstructorInvocation newConInvoc =
				ast.newConstructorInvocation();
		
		int numParams1 = parameters1.size();
		int numParams2 = parameters2.size();
			
		for(int i = 0; i < numParams2; i++) {
			found = false;
			currParam2 = (SingleVariableDeclaration) parameters2.get(i);
			currParam2Str = currParam2.toString();
			for(int j = 0; j < numParams1; j++) {
				currParam1 = (SingleVariableDeclaration) parameters1.get(j);
				currParam1Str = currParam1.toString();
				
				if(currParam2Str.equals(currParam1Str)) {
					found = true;
					SimpleName paramName = currParam1.getName();
					SimpleName paramNameCopy = 
							(SimpleName) ASTNode.copySubtree(ast, paramName);
					newConInvoc.arguments().add(paramNameCopy);
					break;
				}	
			}
			
			if(!found) {
				Type paramType = currParam2.getType();
				newConInvoc = getconstructorInvocationDefaults(paramType, newConInvoc);
				
			}
		}
		constructor1.getBody().statements().add(0, newConInvoc);
	}
	 
	/**
	 * The refactoring so far works only for 2 constructors. 
	 * @throws CoreException 
	 * @throws BadLocationException 
	 * @throws MalformedTreeException 
	 */
	@SuppressWarnings({ "rawtypes" })
	private void refactor() throws CoreException, MalformedTreeException, BadLocationException {
		// In initial check we can't assign to global variable.
		LinkedList<MethodDeclaration> constructors = visitor.getConstructors();
		if(constructors.size() < 2) {
			return;
		}
		else {
			
			MethodDeclaration constructor1 = 
					constructors.get(0);
			MethodDeclaration constructor2 =
					constructors.get(1);
			List parameters1 = constructor1.parameters();
			List parameters2 = constructor2.parameters();
			
			int numParams1 = parameters1.size();
			int numParams2 = parameters2.size();
			
			List intersection = intersection(parameters1, parameters2);
			
			// There is a possibility of no shared parameters.
			if(intersection.size() == 0)
				refactorDistinctAndOverlappedParams(constructor1, constructor2);
			
			// parameters1 is a subset of parameters2
			else if(intersection.size() == numParams1) {
				refactorSubsetParams(constructor1, constructor2);
			} // end else
			
			else if(intersection.size() == numParams2) {
				refactorSubsetParams(constructor2, constructor1);
			} // end else
			
			else {
				refactorDistinctAndOverlappedParams(constructor1, constructor2);
			}
		}
	}

	// GETTERS AND SETTERS
	public AST getAst() {
		return ast;
	}

	public void setAst(AST ast) {
		this.ast = ast;
	}

	public ASTConstructorVisitor getVisitor() {
		return visitor;
	}

	public void setVisitor(ASTConstructorVisitor visitor) {
		this.visitor = visitor;
	}

	public Boolean getHasSingleConstructor() {
		return hasSingleConstructor;
	}

	public void setHasSingleConstructor(Boolean hasSingleConstructor) {
		this.hasSingleConstructor = hasSingleConstructor;
	}

	public IField getField() {
		return field;
	}	
}
