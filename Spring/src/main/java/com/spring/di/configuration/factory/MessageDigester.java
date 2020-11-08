package com.spring.di.configuration.factory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.security.MessageDigest;

/**
 * Некий подписывальщик сообщений
 * @author Alexander Shumeyko
 * @ created 08.11.2020
 */
@Component
public class MessageDigester
{
	//Внедряется благодаря регистрации соответствующей фабрики
	//  Еще можно как то параметры задавать, надо разобраться
	@Autowired
	private MessageDigest messageDigest;

	public void digest(String msg)
	{
		messageDigest.reset(); //С многопоточностью проблемы?
		System.out.println(messageDigest.digest(msg.getBytes()));
	}
}
