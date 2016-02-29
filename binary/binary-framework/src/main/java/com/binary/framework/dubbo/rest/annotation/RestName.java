package com.binary.framework.dubbo.rest.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;



/**
 * @author wanwb
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RestName {
	
	
	
	String value() default "";
	
	
	
	
	
	

	
	
	
	
	
	
	
}
