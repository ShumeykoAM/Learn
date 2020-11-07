package com.spring.di.configuration.java;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * Вторая имплементация
 * @author Alexander Shumeyko
 * @ created 07.11.2020
 */
@Component
@Order(10)
@Qualifier("nameImpl2")
public class MoreImpl2 implements MoreImplementations
{
	@Override
	public String getName()
	{
		return this.getClass().getName();
	}
}
