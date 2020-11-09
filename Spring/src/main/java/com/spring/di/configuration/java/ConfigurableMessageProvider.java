package com.spring.di.configuration.java;

/**
 * Некая реализация
 * @author Alexander Shumeyko
 * @ created 09.11.2020
 */
public class ConfigurableMessageProvider implements MessageProvider
{
	private final String message;

	public ConfigurableMessageProvider(String message)
	{
		this.message = message;
	}

	@Override
	public String getMessage()
	{
		return message;
	}
}
