package com.spring.di.configuration.java;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.inject.Inject;

/**
 * Объявление бина с помощью аннотации
 * Так же присутствует объявление бина через xml
 * @author Alexander Shumeyko
 * @ created 05.11.2020
 */
@Service("messageRenderer")
public class StandardOutMessageRenderer implements MessageRenderer
{
	private MessageProvider provider;

	@Resource(name = "messageProvider")
	private MessageProvider provider2;

	@Inject
	private MessageProvider provider3;

	@Override
	public void render()
	{
		System.out.println(provider.getMessage());
	}

	@Override
	@Autowired
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
