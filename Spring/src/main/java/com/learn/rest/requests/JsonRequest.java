package com.learn.rest.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.learn.rest.deserialize.StringToBooleanDeserialize;
import com.learn.rest.validator.CorrectDate;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * Бин запроса в Json формате
 * @author Kot
 * @ created 14.02.2018
 * @ $Author$
 * @ $Revision$
 */
public class JsonRequest
{
	@JsonProperty("name")
	@NotEmpty(message = "Нужен обязательный параметр")
	private String name;

	@JsonDeserialize(using = StringToBooleanDeserialize.class)
	private Boolean isDeserializable;

	@NotNull(message = "Нужен обязательный параметр")
	@Pattern(regexp = "^35$|^44$", message = "Параметр задан неверно")
	private String expression;

	@NotNull(message = "Нужен обязательный параметр")
	@CorrectDate(dateFormat = "dd.MM.YYYY", message = "Параметр задан неверно")
	private String dateIn;

	@NotNull(message = "Нужен обязательный параметр")
	@CorrectDate(dateFormat = "YYYY.MM.dd", message = "Параметр задан неверно")
	private String dateOut;

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public Boolean getDeserializable()
	{
		return isDeserializable;
	}

	public void setDeserializable(Boolean deserializable)
	{
		isDeserializable = deserializable;
	}

	public String getExpression()
	{
		return expression;
	}

	public void setExpression(String expression)
	{
		this.expression = expression;
	}

	public String getDateIn()
	{
		return dateIn;
	}

	public void setDateIn(String dateIn)
	{
		this.dateIn = dateIn;
	}

	public String getDateOut()
	{
		return dateOut;
	}

	public void setDateOut(String dateOut)
	{
		this.dateOut = dateOut;
	}
}
