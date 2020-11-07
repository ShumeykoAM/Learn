package com.spring.di.configuration.java;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * Первая имплементация
 * @author Alexander Shumeyko
 * @ created 07.11.2020
 */
@Component("castomIdForImpl1") //Идентификатор применится при внедрении компонента в map
@Order(20)
public class MoreImpl1 implements MoreImplementations
{
	@Override
	public String getName()
	{
		return this.getClass().getName();
	}
}
