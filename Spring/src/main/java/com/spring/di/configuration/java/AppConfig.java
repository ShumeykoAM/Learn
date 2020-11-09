package com.spring.di.configuration.java;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;

import java.util.Properties;

/**
 * Конфигурация бинов аннотациями
 * @author Alexander Shumeyko
 * @ created 08.11.2020
 */

@Configuration
//@ImportResource see com.spring.di.configuration.xml.XmlConfiguration
@PropertySource("classpath:message.properties")
@Import(ConfigurationAnnotation.class) //позволяет разносить конфигурацию на несколько файлов, удобно, когда нужно сгруппировать
//@ComponentScan указываем пакет для сканирования

public class AppConfig
{
	private final Environment environment;//Позволяет извлекать свойства jvm, свойства системы, свойства определенные в приложении
	private final Properties someProperties2;
	private final String prop1;

	@Autowired(required = true)
	public AppConfig(Environment environment, Properties someProperties2,
	                 @Value("${com.example.message1}") String prop1)
	{
		this.environment = environment;
		this.someProperties2 = someProperties2;
		this.prop1 = prop1;
	}

	@Required
	@Bean
	@Lazy(true)
	public MessageProvider configurableMessageProvider()
	{
		return new ConfigurableMessageProvider(environment.getProperty("com.example.message2"));
	}

	@Bean(name = "configurableMessageRenderer")
	@Scope("prototype")
	@DependsOn("configurableMessageProvider")
	public MessageRenderer messageRenderer()
	{
		ConfigurableMessageRenderer messageRenderer = new ConfigurableMessageRenderer(someProperties2.getProperty("com.example.property.three") + "  " + prop1);
		messageRenderer.setMessageProvider(configurableMessageProvider());
		return messageRenderer;
	}
}
