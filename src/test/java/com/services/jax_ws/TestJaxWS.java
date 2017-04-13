package com.services.jax_ws;

import org.junit.Ignore;
import org.junit.Test;

/**
 * @author Kot
 * @ created 12.04.2017
 * @ $Author$
 * @ $Revision$
 */
@Ignore //запускаем только в контексте сервака, в тесте пока что контекст не поднимается
public class TestJaxWS
{
	// Создаем jax_vs клиента и вызываем метод сервиса
	@Test
	public void entry1()
	{
		try
		{
			TemperatureBryanskService temperatureBryanskService = new TemperatureBryanskService();
			TemperatureBryansk temperatureBryansk = temperatureBryanskService.getTemperatureBryanskPort();
			String t = temperatureBryansk.getTemperature(2);
			Book book = temperatureBryansk.getLikeBook(new Book("Хорошая книга"));
			System.out.println(t);
		}
		catch (Throwable e)
		{
			int i = 0;
		}
	}
}
