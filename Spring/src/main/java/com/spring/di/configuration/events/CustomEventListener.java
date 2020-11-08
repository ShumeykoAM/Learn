package com.spring.di.configuration.events;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * Слушатель собыий
 * @author Alexander Shumeyko
 * @ created 08.11.2020
 */
@Component
//Spring автоматически регистрирует компоненты реализующие ApplicationListener как слушателей
public class CustomEventListener implements ApplicationListener<CustomEvent>
{
	@Override
	public void onApplicationEvent(CustomEvent event)
	{
		System.out.println(event.getMessage());
	}
}
