package com.spring.di;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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
		SpringApplication.run(DependencyInjectionApplication.class, args);
	}
}
