package com.example.rest.serialize;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

/**
 * Сериализатор Boolean в строку
 * @author Kot
 * @ created 14.02.2018
 * @ $Author$
 * @ $Revision$
 */
public class BooleanToStringSerialize extends JsonSerializer<Boolean>
{

	@Override public void serialize(Boolean value, JsonGenerator gen, SerializerProvider serializers) throws IOException, JsonProcessingException
	{
		gen.writeString(value.toString());
	}
}
