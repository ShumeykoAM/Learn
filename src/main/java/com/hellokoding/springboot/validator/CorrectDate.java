package com.hellokoding.springboot.validator;

import java.lang.annotation.*;
import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * Аннотация валидатора даты
 * @author sbt-shumeyko-am
 * @ created 02.02.2018
 * @ $Author$
 * @ $Revision$
 */
@Documented
@Constraint(validatedBy = CorrectDateValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CorrectDate
{
	String message() default "{CorrectDate}";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

	/**
	 * @return формат даты
	 */
	String dateFormat();
}