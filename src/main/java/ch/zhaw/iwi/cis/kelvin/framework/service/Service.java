package ch.zhaw.iwi.cis.kelvin.framework.service;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention( RetentionPolicy.RUNTIME )
public @interface Service
{
	public String name() default "";
	public String description() default "";
}
