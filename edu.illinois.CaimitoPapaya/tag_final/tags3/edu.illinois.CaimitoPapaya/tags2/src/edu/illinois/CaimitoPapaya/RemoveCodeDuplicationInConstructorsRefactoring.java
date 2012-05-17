package edu.illinois.CaimitoPapaya;


import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IField;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.dom.*;
import org.eclipse.jdt.core.refactoring.descriptors.JavaRefactoringDescriptor;
import org.eclipse.jdt.internal.corext.refactoring.changes.DynamicValidationRefactoringChange;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IDocument;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.Refactoring;
import org.eclipse.ltk.core.refactoring.RefactoringDescriptor;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.text.edits.MalformedTreeException;
import org.eclipse.text.edits.TextEdit;
import org.eclipse.text.edits.UndoEdit;

// Will be implemented later for type checks
/*enum type {
	PRIMITIVE, 
}*/


class ASTConstructorVisitor extends ASTVisitor {
    private LinkedList<MethodDeclaration> constructors;
    private TypeDeclaration classname;
	
    /**
     * The node visitor of the Abstract SYnta
     */
    public ASTConstructorVisitor() {
    	constructors = new LinkedList<MethodDeclaration>();
    	classname = null;
    }
    
    public boolean visit(TypeDeclaration node) {
    	MethodDeclaration[] methods = node.getMethods();
    	for(int i = 0; i < methods.length; i++) {
    		if(methods[i].isConstructor()) {
    			constructors.add(methods[i]);
    		}
    	}
    	classname = node;
		return super.visit(node);	
    }
    
    public TypeDeclaration getClassname() {
		return classname;
	}

	public void setClassname(TypeDeclaration classname) {
		this.classname = classname;
	}

	/*public boolean visit(TypeDeclaration node) {
    	classname = node;
    	return false;
    }*/

	public LinkedList<MethodDeclaration> getConstructors() {
		return constructors;
	}

	public void setConstructors(LinkedList<MethodDeclaration> constructors) {
		this.constructors = constructors;
	}
    
    
 }

public class RemoveCodeDuplicationInConstructorsRefactoring extends Refactoring {

	private final IField field;
	private ICompilationUnit unit;
	private int[] ConstrIndex; // list of int where constr were found // TODO change to arraylist
	private ASTParser parser;
	private CompilationUnit astCU;
	private CompilationUnit oldAstCU;
	private ASTConstructorVisitor visitor;
	private Document doc;
	private Boolean hasSingleConstructor;
	public RemoveCodeDuplicationInConstructorsRefactoring(IField field, ICompilationUnit unit) throws CoreException, MalformedTreeException, BadLocationException {
		this.field = field;
		this.unit = unit;
		this.ConstrIndex = new int[4];
		hasSingleConstructor = false;		
		
		// creation of a Document
		   //ICompilationUnit cu = ... ; // content is "public class X {\n}"
		String source = unit.getBuffer().getContents();
		Document document= new Document(source);
	
		// creation of DOM/AST from a ICompilationUnit
		ASTParser parser = ASTParser.newParser(AST.JLS3);
		parser.setSource(unit);
		astCU = (CompilationUnit) parser.createAST(null);
	
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

	/*
	 * This function will be used as a reference and we might use it
	 * if there will be any need for it.
	 */
	/*private void refactorFoundConstructor(MethodDeclaration node) {
    	// Used to test function
		// TODO get following information from code
		String[] argTypeAry ={"int",	"String",	"double",	"double"};
		String[] argAry =	 {"age",	"name", 	"height",	"weight"};
		
		String thisStatement = "";

		String constructor = node.getBody().toString();
		System.out.println("constructor_before = " + constructor);
		
		for(int i=0; i < argAry.length; i++){
			if(argAry[i].length() == 0)
				continue;
			Pattern pattern = Pattern.compile("this."+argAry[i] + "\\s*=");
			Matcher matcher = pattern.matcher(constructor);
			boolean found = matcher.find();
			if (!found){
				pattern = Pattern.compile(argAry[i] + "\\s*=");
				matcher = pattern.matcher(constructor);
				found = matcher.find();
			}
			if (found){
				// find line to delete
				int lineStart = matcher.start();
				int lineEnd = constructor.indexOf(';', lineStart) + 1;
				String lineToDelete = constructor.substring(lineStart, lineEnd);
							
				// find variable used to initialize
				// add to this statement
				int matchEnd = matcher.end();
				String variable = constructor.substring(matchEnd, lineEnd-1);
				thisStatement += variable.trim();
				
				// delete line
				for(int i1=0; i1<node.getBody().statements().size(); i1++){
					if(node.getBody().statements().get(i1).toString().contains(lineToDelete)){
						node.getBody().statements().remove(i1);
						break;
					}
				}
			}
			else{
				if (argTypeAry[i] == "int")
					thisStatement += "0";
				else if (argTypeAry[i] == "double")
					thisStatement += "0.0";
				else
					thisStatement += "null";
			}
			
			if (argAry[i].length() > 1 && i <argAry.length-1){
				thisStatement += ", ";
			}
			
		}
		// TODO add this statement to constructor
		//node.getBody().statements().add(0, element);
		AST testnode = null;
		testnode.newConstructorInvocation();
		
		System.out.println("thisStatement = "+ "this("+thisStatement+");");
		System.out.println("constructor_after ="+node.getBody().toString());
		
	}*/
	
	@Override
	public String getName() {
		return "RemoveCodeDuplicationInConstructors";
	}

	@Override
	public RefactoringStatus checkInitialConditions(IProgressMonitor pm) {
		try {
			pm.beginTask("Remove Code Duplications in Constructors", 
					IProgressMonitor.UNKNOWN);
		}
		catch(OperationCanceledException oce) {
			pm.done();
			RefactoringStatus.createErrorStatus(
					"The task has been cancelled");
		}
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
		else if(hasSingleConstructor)
			return RefactoringStatus.createErrorStatus("Has less than 2 constructors");
		return new RefactoringStatus();
	}

	@SuppressWarnings("restriction")
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
	
	/**
	 * Return an intersection of two lists.
	 * @param parameters1 - a list of parameters.
	 * @param parameters2 - a list of parameters.
	 * @return an intersection of the two lists.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List intersection(List parameters1, List parameters2) {
		Object parameter;
		List intersection = new LinkedList();
		HashMap<Object, Boolean> found = new HashMap<Object, Boolean>();
		
		int size1 = parameters1.size();
		for(int i = 0; i < size1; i++) {
			parameter = parameters1.get(i);
			found.put(parameter, true);
		}
		
		int size2 = parameters2.size();
		for(int i = 0; i < size2; i++) {
			parameter = parameters2.get(i);
			if(found.containsKey(parameter))
				intersection.add(parameter);
		}
		
		return intersection;
	}
	
	/*
	 * A function that will be used for 
	 * type checking.
	 */
/*	public Code findType(String type) {
		if(type.equals("int"))
			return PrimitiveType.INT;
		
		else if(type.equals("boolean"))
			return PrimitiveType.BOOLEAN;
		
		else if(type.equals("byte"))
			return PrimitiveType.BYTE;
		
		else if(type.equals("char"))
			return PrimitiveType.CHAR;
		
		else if(type.equals("double"))
			return PrimitiveType.DOUBLE;
		
		else if(type.equals("float"))
			return PrimitiveType.FLOAT;
		
		else if(type.equals("long"))
			return PrimitiveType.LONG;
		
		else if(type.equals("short"))
			return PrimitiveType.SHORT;
		
	}*/
	
	/**
	 * Get the union of two lists.
	 * @param parameters1 - a list of parameters.
	 * @param parameters2 - a list of parameters.
	 * @param ast - the representation of an AST tree. We will add the union as a
	 * new node for our new MethodDeclaration (or Constructor).
	 * @return the union of the two lists.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List union(List parameters1, List parameters2, AST ast) {
		SingleVariableDeclaration currParam;
		List parameters = new LinkedList();
		
		// We keep two different loops, so we could maintain 
		// proper order of the union.
		for(int i = 0; i < parameters1.size(); i++) {
			currParam = (SingleVariableDeclaration) parameters1.get(i);
			SimpleName varName = currParam.getName();
			String varStr = varName.toString();
			SingleVariableDeclaration variableDeclaration = 
					ast.newSingleVariableDeclaration();
			
			SimpleName varSimpleName = ast.newSimpleName(varStr);
			variableDeclaration.setName(varSimpleName);
			
			// So far we only check for 'int's in our first constructor
			PrimitiveType type = ast.newPrimitiveType(PrimitiveType.INT);
			variableDeclaration.setType(type);
			parameters.add(variableDeclaration);
			
		}
		for(int i = 0; i < parameters2.size(); i++) {
			currParam = (SingleVariableDeclaration) parameters2.get(i);
			Type type = currParam.getType();
			String typeStr = type.toString();
			System.out.println(typeStr);
			SimpleName varName = currParam.getName();
			String varStr = varName.toString();
			SingleVariableDeclaration variableDeclaration = ast.newSingleVariableDeclaration();
			
			// So far we only check for doubles only in our second constructor.
			variableDeclaration.setType(ast.newPrimitiveType(PrimitiveType.DOUBLE));
			variableDeclaration.setName(ast.newSimpleName(varStr));
			parameters.add(variableDeclaration);
		}
		return parameters;
	}
	
	/**
	 * Create a Constructor Invocation from the right hand of the assignments
	 * in the constructor body.
	 * @param body - the constructor body.
	 * @param ast - the Abstract Syntax Tree.
	 * @return - the ConstructorInvocation.
	 */
	ConstructorInvocation createConstructorInvocation(Block body, AST ast) {
		ConstructorInvocation ci = ast.newConstructorInvocation();
		for(int i = 0; i < body.statements().size(); i++) {
			ExpressionStatement currExp = (ExpressionStatement) body.statements().get(i);
			Expression currExpression = currExp.getExpression();
			Assignment currAssignment = (Assignment) currExpression;
			Expression rightH = currAssignment.getRightHandSide();
			String rightHandedValue = rightH.toString();
			if(rightH.getNodeType() == Expression.SIMPLE_NAME) {
				Expression newExp = ast.newSimpleName(rightHandedValue);
				ci.arguments().add(newExp);
			}
			else {
				NumberLiteral nl = ast.newNumberLiteral(rightHandedValue);
				ci.arguments().add(nl);
			}
		}
		return ci;
	}
	
	/**
	 * Populate the MethodDeclaration with the statements inside the block
	 * and add them to the Abstract Syntax Tree.
	 * @param ast - the syntax tree that is to be updated.
	 * @param md - the MethodDeclaration that is to be updated.
	 * @param body - The Block that contains the statements.
	 * @return the updated MethodDeclaration.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public MethodDeclaration populateWithStatements(AST ast, MethodDeclaration md, Block body) {
		ThisExpression newThis;
		List statements = body.statements();
		int size = statements.size();
		for(int i = 0; i < size; i++) {
			ExpressionStatement expression = 
					(ExpressionStatement) statements.get(i);
			
			// For now our refactoring program handles assignments
			Assignment a = ast.newAssignment();
			a.setOperator(Assignment.Operator.ASSIGN);
			Assignment assignment = (Assignment) expression.getExpression();
			Expression leftH = assignment.getLeftHandSide();
			Expression rightH = assignment.getRightHandSide();
			
			String name = leftH.toString();
			
			// I'm pretty sure 'this' goes before '.', but to be safe,
			// I used indexOf
			int expValSeparator = name.indexOf(".");
			name = name.substring(expValSeparator + 1);
			
			String value = rightH.toString();
			
			// We assume that it's a field access for now
			FieldAccess old = (FieldAccess) leftH;
			
			FieldAccess statement = ast.newFieldAccess();
			String currThis = old.getExpression().toString();
			newThis = ast.newThisExpression();
			System.out.println(currThis);
			
			// Creating a this expression to make the left
			// hand side of the assignment to be this.var
			statement.setExpression(newThis);
			
			SimpleName newName = ast.newSimpleName(name);
			statement.setName(newName);
			String class_ = rightH.getClass().getSimpleName();
			if(class_.equals("SimpleName")) {
				SimpleName sn = ast.newSimpleName(value);
				a.setRightHandSide(sn);
				a.setLeftHandSide(statement);
				ExpressionStatement newStatement = ast.newExpressionStatement(a);
				md.getBody().statements().add(newStatement);
			}
			else {
				continue;
			}
		}
		return md;
	}
	
	/**
	 * The refactoring so far works only for 2 constructors. 
	 * @throws CoreException 
	 * @throws BadLocationException 
	 * @throws MalformedTreeException 
	 */
	@SuppressWarnings({ "rawtypes", "unchecked", "deprecation" })
	public void refactor() throws CoreException, MalformedTreeException, BadLocationException {
		ThisExpression newThis;
		AST ast = astCU.getAST();
		LinkedList<MethodDeclaration> constructors = visitor.getConstructors();
		if(constructors.size() < 2){
			hasSingleConstructor = true;
			return;
		}
		else{
			MethodDeclaration constructor1 = 
					constructors.get(0);
			MethodDeclaration constructor2 =
					constructors.get(1);
			List parameters1 = constructor1.parameters();
			List parameters2 = constructor2.parameters();
			
			List intersection = intersection(parameters1, parameters2);
			
			// There is a possibility of no shared parameters.
			if(intersection.size() == 0) {
				TypeDeclaration td = visitor.getClassname();
				MethodDeclaration md = ast.newMethodDeclaration();
				md.setConstructor(true);
				SimpleName tdSimpleName = td.getName();
				String className = tdSimpleName.getFullyQualifiedName();
				SimpleName astSimpleName = ast.newSimpleName(className);
				md.setName(astSimpleName);
				List modifiers = md.modifiers();
				Modifier newModifier = 
						ast.newModifier(Modifier.ModifierKeyword.PUBLIC_KEYWORD);
				modifiers.add(newModifier);
				List allParams = union(parameters1, parameters2, ast);
				
				for(int i = 0; i < allParams.size(); i++)
					md.parameters().add(allParams.get(i));
				
				Block body1 = constructor1.getBody();
				Block body2 = constructor2.getBody();
				md.setBody(ast.newBlock());
				
				md = populateWithStatements(ast, md, body1);
				md = populateWithStatements(ast, md, body2);
				td.bodyDeclarations().add(md);
				
		
				// We populate the other constructor with a constructor invocation.
				ConstructorInvocation ci = createConstructorInvocation(body1, ast);
				
				// Will not always clear. Will change!
				// This will change!
				constructor1.getBody().statements().clear();
				constructor1.getBody().statements().add(ci);
				
			
				// We populate the other constructor with a constructor invocation.
				ci = createConstructorInvocation(body2, ast);
				
				// We might not want to clear it.
				// This will certainly change!
				constructor2.getBody().statements().clear();
				constructor2.getBody().statements().add(ci);
				
			}
		}
	}

	public IField getField() {
		return field;
	}

	public ICompilationUnit getUnit() {
		return unit;
	}

	public int[] getConstrIndex() {
		return ConstrIndex;
	}

	public ASTParser getParser() {
		return parser;
	}

	public CompilationUnit getAstCU() {
		return astCU;
	}

	public CompilationUnit getOldAstCU() {
		return oldAstCU;
	}

	public ASTConstructorVisitor getVisitor() {
		return visitor;
	}

	public Document getDoc() {
		return doc;
	}


}
