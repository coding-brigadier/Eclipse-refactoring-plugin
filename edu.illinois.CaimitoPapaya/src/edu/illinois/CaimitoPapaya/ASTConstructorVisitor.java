package edu.illinois.CaimitoPapaya;

import java.util.LinkedList;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;

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

	public LinkedList<MethodDeclaration> getConstructors() {
		return constructors;
	}

	public void setConstructors(LinkedList<MethodDeclaration> constructors) {
		this.constructors = constructors;
	}
    
    
 }