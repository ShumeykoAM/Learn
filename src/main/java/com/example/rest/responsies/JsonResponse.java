package com.example.rest.responsies;

import com.example.rest.serialize.BooleanToStringSerialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * Бин ответа в Json формате
 * @author Kot
 * @ created 14.02.2018
 * @ $Author$
 * @ $Revision$
 */
public class JsonResponse
{
	@JsonSerialize(using = BooleanToStringSerialize.class)
	private Boolean isSerializable;

	public JsonResponse() { }

	public JsonResponse(Boolean isDeserializable)
	{
		this.isSerializable = isDeserializable;
	}

	public Boolean getIsSerializable()
	{
		return isSerializable;
	}

	public void setIsSerializable(Boolean isSerializable)
	{
		this.isSerializable = isSerializable;
	}
}
