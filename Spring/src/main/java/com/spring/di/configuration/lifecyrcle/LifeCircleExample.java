package com.spring.di.configuration.lifecyrcle;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * Демонстрация жизненного цикла бина
 * @author Alexander Shumeyko
 * @ created 07.11.2020
 */
@Component
public class LifeCircleExample implements
	BeanNameAware, BeanClassLoaderAware, ApplicationContextAware, InitializingBean,
	DisposableBean
{
	private String name;
	private ClassLoader classLoader;
	private ApplicationContext applicationContext;
	@Autowired //Вместо реализации интерфейса можно заинжектить
	private ApplicationContext autowiredApplicationContext;

	//Методы вызываемые при конструировании бина --------------------------------------------------

	@Override
	public void setBeanName(String name)
	{
		this.name = name;
	}

	@Override
	public void setBeanClassLoader(ClassLoader classLoader)
	{
		this.classLoader = classLoader;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException
	{
		this.applicationContext = applicationContext;

		//чуть ниже написано зачем это нужно
		if (applicationContext instanceof GenericApplicationContext)
			((GenericApplicationContext)applicationContext).registerShutdownHook();
	}

	@PostConstruct
	public void postConstruct()
	{
		//вызовется после полного конструирования бина
	}

	@Override
	public void afterPropertiesSet() throws Exception
	{
		//вызовется после полного конструирования бина
	}

	//Так же можно указать метод в xml конфигурации, который вызовется после полного конструирования бина

	//
	//Методы вызываемые при разрушении бина, кроме бинов с областью действия Prototype ----------------
	//  что бы сработали нужно зарегистрировать registerShutdownHook() для контекста или вызвать метод destroy контекста

	@PreDestroy
	public void predestroy()
	{
		//Вызывается перед разрйшением бина, кроме Prototype
	}

	@Override
	public void destroy() throws Exception
	{
		//Вызывается перед разрйшением бина, кроме Prototype
	}

	//Так же можно указать метод в xml конфигурации, который вызывается перед разрйшением бина, кроме Prototype
}