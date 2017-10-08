package com.example.ejb_jms;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Remote;
import javax.ejb.Stateless;

/**
 * @Stateless - означает что объект ejb_jms существует вне контекста, не привязан к какому либо пользователю,
 *      класс такого ejb_jms не должен иметь поля, так как не известно какой пользователь будет использовать этот объект в следующий раз
 * @author Kot
 * @ created 06.10.2017
 * @ $Author$
 * @ $Revision$
 */
@Remote//Уровень доступа RMI (Remote Method Invocation), есть еще @Local, надо разбираться.
@Stateless
public class SimpleStateless
{
	//Какие ни будь методы не привязанные к какому либо контексту
	public String sayHelloWorld()
	{
		return "Hello Stateless ejb_jms";
	}

	/**
	 * Вызывается сразу после активации
	 * Что делать с этим методом не совсем понял, так как полей в классе нету, что инициализировать !?
	 */
	@PostConstruct
	private void postConstruct()
	{

	}

	/**
	 * Вызывается перед уничтожением
	 * Что делать с этим методом не совсем понял, так как полей в классе нету, что инициализировать !?
	 */
	@PreDestroy
	private void preDestroy()
	{

	}
}
