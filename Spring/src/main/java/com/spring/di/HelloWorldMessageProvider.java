package com.spring.di;

import org.springframework.stereotype.Service;

/**
 * Объявление бина с помощью аннотации
 * Так же присутствует объявление бина через xml
 * @author Alexander Shumeyko
 * @ created 05.11.2020
 */
@Service("messageProvider")
public class HelloWorldMessageProvider implements MessageProvider
{
	@Override
	public String getMessage()
	{
		return "HelloWorld";
	}
}
