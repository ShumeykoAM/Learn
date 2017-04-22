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
@WebService
public interface ServiceTemperature
{
	@WebMethod
	public String getTemperature(int hour);
	@WebMethod
	public Book getLikeBook(Book book);
}
