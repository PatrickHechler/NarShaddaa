package de.tafelwischenSecure.command;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This is a full Command or a part of an Command
 */
@Retention(RetentionPolicy.RUNTIME)  // make the annotation visible at runtime 
@Target(ElementType.FIELD)  // this annotation can only be used for fields
public @interface Command {
	
	/**
	 * Specifies the art of this command
	 */
	public CommandEnum spezify();
	
}
