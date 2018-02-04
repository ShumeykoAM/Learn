package com.hellokoding.springboot;

import com.fasterxml.jackson.core.JsonProcessingException;

public class JsonDeserializeException extends JsonProcessingException
{

	protected JsonDeserializeException(String msg)
	{
		super(msg);
	}
}