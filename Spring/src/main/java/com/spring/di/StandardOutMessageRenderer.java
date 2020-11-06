package com.spring.di;

import org.springframework.stereotype.Service;

/**
 * Объявление бина с помощью аннотации
 * Так же присутствует объявление бина через xml
 * @author Alexander Shumeyko
 * @ created 05.11.2020
 */
@Service("messageRenderer")
public class StandardOutMessageRenderer implements MessageRenderer
{
	@Override
	public void render()
	{

	}

	@Override
	public void setMessageProvider(MessageProvider provider)
	{

	}

	@Override
	public MessageProvider getMessageProvider()
	{
		return null;
	}
}
