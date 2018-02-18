package com.learn.rest.deserialize;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

/**
 * Десериализатор и строки в boolean
 * @author Kot
 * @ created 14.02.2018
 * @ $Author$
 * @ $Revision$
 */
public class StringToBooleanDeserialize extends JsonDeserializer<Boolean>
{

	@Override
	public Boolean deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException
	{
		Boolean result = null;
		if (!Boolean.TRUE.toString().equals(p.getText()) && !Boolean.FALSE.toString().equals(p.getText()))
			ctxt.reportMappingException("Тут брозается исключение дальше и перехват произойдет в @ExceptionHandler!");
		else
			result = Boolean.parseBoolean(p.getText());

		return result;
	}
}
