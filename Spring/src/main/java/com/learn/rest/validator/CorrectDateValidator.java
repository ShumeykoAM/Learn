package com.learn.rest.validator;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
			try
			{
				DateFormat df = new SimpleDateFormat(dateFormat);
				Date date = df.parse(value);
				//TODO тут надо из получившейся даты формат вынуть и сравнить или как то так
				return true;
			}
			catch (ParseException e)
			{
				return false;
			}
		}
		return true;
	}
}