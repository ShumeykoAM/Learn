package com.spring.di;

import com.spring.di.configuration.events.CustomEvent;
import com.spring.di.configuration.factory.MessageDigester;
import com.spring.di.configuration.java.MessageRenderer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

/**
 * Приложение SpringBoot
 * @author Alexander Shumeyko
 * @ created 05.11.2020
 */
@SpringBootApplication
public class DependencyInjectionApplication
{
	public static void main(String[] args)
	{
		ApplicationContext context = SpringApplication.run(DependencyInjectionApplication.class, args);

		MessageRenderer renderer = context.getBean("messageRenderer", MessageRenderer.class);
		renderer.render();
		MessageDigester digester = context.getBean("messageDigester", MessageDigester.class);
		digester.digest("Hello World!");

		//Регистрируем событие, вызовутся все обработчики
		context.publishEvent(new CustomEvent("", "some message"));

		MessageRenderer renderer1 = context.getBean("configurableMessageRenderer", MessageRenderer.class);
		renderer1.render();
		renderer1 = context.getBean("configurableMessageRenderer", MessageRenderer.class);
		renderer1.render();

	}
}
