package com.spring.di.configuration.java;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.inject.Inject;

/**
 * Объявление бина с помощью аннотации
 * Так же присутствует объявление бина через xml
 * @author Alexander Shumeyko
 * @ created 05.11.2020
 */
@Service("messageRenderer")
public class StandardOutMessageRenderer implements MessageRenderer
{
	private MessageProvider provider;
	private final String stringField;
	@Value("45")
	private int intField;
	@Value("#{messageProvider.message}") //use SpEL
	private String stringField2;

	@Autowired
	@Qualifier("messageProvider")
	private MessageProvider provider1;

	@Resource(name = "messageProvider")
	private MessageProvider provider2;

	@Inject
	//@Qualifier("messageProvider")
	private MessageProvider provider3;

	private final MessageProvider provider4;

	@Autowired
	ContainCollections collections;

	@Autowired
	public StandardOutMessageRenderer(MessageProvider provider, @Value("string value") String stringField)
	{
		this.provider4 = provider;
		this.stringField = stringField;
	}

	@Override
	public void render()
	{
		System.out.println(provider.getMessage() + stringField + intField + stringField2);
	}

	@Override
	@Autowired
	public void setMessageProvider(MessageProvider provider)
	{
		this.provider = provider;
	}

	@Override
	public MessageProvider getMessageProvider()
	{
		return provider;
	}
}
