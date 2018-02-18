package com.learn.rest.responsies;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.learn.rest.serialize.BooleanToStringSerialize;

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

	public JsonResponse(Boolean isSerializable)
	{
		this.isSerializable = isSerializable;
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
