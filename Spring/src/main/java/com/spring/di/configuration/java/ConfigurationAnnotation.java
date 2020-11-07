package com.spring.di.configuration.java;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * Объявление бинов с помощью аннотации @Bean
 * @author Alexander Shumeyko
 * @ created 07.11.2020
 */
@Configuration
public class ConfigurationAnnotation
{
	@Bean("customIdProperties")
	public Properties someProperties()
	{
		Properties properties = new Properties();
		properties.setProperty("com.example.property.one", "valueOne");
		properties.setProperty("com.example.property.two", "valueTwo");
		return properties;
	}

	@Bean
	public Properties someProperties2()
	{
		Properties properties = new Properties();
		properties.setProperty("com.example.property.three", "valueThree");
		properties.setProperty("com.example.property.four", "valueFour");
		return properties;
	}
}
