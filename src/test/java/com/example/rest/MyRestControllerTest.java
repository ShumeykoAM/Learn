package com.example.rest;

import com.example.rest.requests.JsonRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

/**
 * TODO Description
 * @author Kot
 * @ created 15.02.2018
 * @ $Author$
 * @ $Revision$
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class, loader = AnnotationConfigContextLoader.class)
public class MyRestControllerTest
{
	@Test
	public void userNotFound() throws Exception
	{
		JsonRequest request = new JsonRequest();
		request.setDateIn("22.11.2018");
		request.setDateOut("22.11.2018");
		request.setExpression("11");

		Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
		Set<ConstraintViolation<JsonRequest>> violations = validator.validate(request);

		for (ConstraintViolation<JsonRequest> violation : violations)
		{
			String fieldName = violation.getPropertyPath().toString();
			String message = violation.getMessage();
			int f = 0;
		}
	}
}
