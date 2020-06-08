package de.tafelwischenSecure;

import de.tafelwischenSecure.command.Command;
import java.lang.reflect.Field;

import org.junit.jupiter.api.Test;

class ConstantsTest {
	
	@Test
	void testAnnotations() throws NoSuchFieldException, SecurityException {
		
		Field[] declaredFields = Constants.class.getDeclaredFields();
		
		for (Field field : declaredFields) {
			Command commandAnnotation = field.getAnnotation(Command.class);
			if (commandAnnotation != null) {
				System.out.println(field.getName() + ": " + commandAnnotation.spezify());
			}
		}
		
		
	}
	
}
