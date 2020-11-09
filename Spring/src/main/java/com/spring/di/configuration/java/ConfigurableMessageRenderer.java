package com.spring.di.configuration.java;

/**
 * Некая реализация
 * @author Alexander Shumeyko
 * @ created 09.11.2020
 */
public class ConfigurableMessageRenderer implements MessageRenderer
{
	private MessageProvider provider;
	private final String sField;

	public ConfigurableMessageRenderer(String sField)
	{
		this.sField = sField;
	}

	@Override
	public void render()
	{
		System.out.println(provider.getMessage() + " " + sField);
	}

	@Override
	public void setMessageProvider(MessageProvider provider)
	{
		this.provider = provider;
	}

	@Override
	public MessageProvider getMessageProvider()
	{
		return provider;
	}
}
