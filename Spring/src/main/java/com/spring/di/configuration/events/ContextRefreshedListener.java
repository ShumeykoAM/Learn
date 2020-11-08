package com.spring.di.configuration.events;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * Слушатель события загрузки контекста спринга, стандартное спринговое событие
 * @author Alexander Shumeyko
 * @ created 08.11.2020
 */
@Component
public class ContextRefreshedListener implements ApplicationListener<ContextRefreshedEvent>
{
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event)
	{
		System.out.println("Контекст загружен");
	}
}
