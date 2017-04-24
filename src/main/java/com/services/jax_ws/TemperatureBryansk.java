package com.services.jax_ws;

import com.services.restful.Book;
import javax.jws.WebMethod;
import javax.jws.WebService;

/**
 * @author Kot
 * @ created 12.04.2017
 * @ $Author$
 * @ $Revision$
 */
@WebService(endpointInterface = "com.services.jax_ws.TemperatureBryansk")
public class TemperatureBryansk
	implements ServiceTemperature
{
	@Override
	@WebMethod
	public String getTemperature(int hour)
	{
		return new Integer(20 + hour).toString();
	}

	@Override
	@WebMethod
	public Book getLikeBook(Book book)
	{
		return new Book("Похожая книга");
	}
}