package com.hellokoding.springboot.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Валидатор корректности даты
 * @author sbt-shumeyko-am
 * @ created 02.02.2018
 * @ $Author$
 * @ $Revision$
 */
public class CorrectDateValidator implements ConstraintValidator<CorrectDate, String>
{
	private String dateFormat;

	@Override
	public void initialize(CorrectDate constraintAnnotation)
	{
		dateFormat = constraintAnnotation.dateFormat();
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context)
	{
		if (value != null)
		{
			return true; //Проверяем что value подходит под бизнеслогику
		}
		return false;
	}
}