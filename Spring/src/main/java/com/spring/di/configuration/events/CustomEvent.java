package com.spring.di.configuration.events;

import org.springframework.context.ApplicationEvent;

/**
 * Некое событие
 * @author Alexander Shumeyko
 * @ created 08.11.2020
 */
public class CustomEvent extends ApplicationEvent
{
	private final String message;

	/**
	 * @param source объект инициализировавший событие, видимо для логирования
	 */
	public CustomEvent(Object source, String message)
	{
		super(source);
		this.message = message;
	}

	/**
	 * @return что то полезное
	 */
	public String getMessage()
	{
		return message;
	}
}
