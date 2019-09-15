package com.example.spring.controller;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * Тело rest запроса
 *
 * @ created 15.09.2019
 */
public class SimpleJsonRequest
{
	@NotNull
	@NotEmpty
	private String stringParam;

	public String getStringParam()
	{
		return stringParam;
	}

	public void setStringParam(String stringParam)
	{
		this.stringParam = stringParam;
	}
}
