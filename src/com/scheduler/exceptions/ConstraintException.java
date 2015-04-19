package com.scheduler.exceptions;
import java.util.logging.*;
import javassist.bytecode.stackmap.TypeData.ClassName;

public class ConstraintException {
	
	private static final Logger log = Logger.getLogger( ClassName.class.getName() );
	public ConstraintException(String studentID, String message){
		log.log(Level.SEVERE, "Constraint exception for "+studentID+" : "+message);
	}

}
