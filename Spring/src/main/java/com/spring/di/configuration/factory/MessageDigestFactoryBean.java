package com.spring.di.configuration.factory;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.annotation.PostConstruct;

/**
 * Фабрика бинов MessageDigest
 * @author Alexander Shumeyko
 * @ created 08.11.2020
 */
@Component
public class MessageDigestFactoryBean implements FactoryBean<MessageDigest>
{
	private MessageDigest messageDigest;
	private final String algorithmName = "MD5";

	@Override
	public MessageDigest getObject() throws Exception
	{
		return messageDigest;
	}

	@Override
	public Class<?> getObjectType()
	{
		return MessageDigest.class;
	}

	@Override
	public boolean isSingleton()
	{
		return true;
	}

	@PostConstruct
	public void initialize() throws NoSuchAlgorithmException
	{
		messageDigest = MessageDigest.getInstance(algorithmName);
	}
}
