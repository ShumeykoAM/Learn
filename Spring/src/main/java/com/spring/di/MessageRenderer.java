package com.spring.di;

/**
 * Интерфейс бина
 * @author Alexander Shumeyko
 * @ created 05.11.2020
 */
public interface MessageRenderer
{
	void render();

	void setMessageProvider(MessageProvider provider);

	MessageProvider getMessageProvider();
}
