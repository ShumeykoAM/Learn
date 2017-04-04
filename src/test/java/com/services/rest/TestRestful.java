package com.services.rest;

import com.services.restful.Book;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import org.junit.Ignore;
import org.junit.Test;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriBuilder;

/**
 * @author sbt-shumeyko-am
 * @ created 04.04.2017
 * @ $Author$
 * @ $Revision$
 */

@Ignore //запускаем только в контексте сервака, в тесте пока что контекст не поднимается
public class TestRestful
{
	// передаем параметры в пост запрос
	@Test
	public void entry1()
	{
		try
		{
			ClientConfig config = new DefaultClientConfig();
			Client client = Client.create(config);
			WebResource webResource = client.resource(UriBuilder.fromUri("http://localhost:8080/myserver/rf_resources/book").build());
			MultivaluedMap formData = new MultivaluedMapImpl();
			formData.add("par", "Название книги");
			ClientResponse response = webResource.type(MediaType.APPLICATION_FORM_URLENCODED_TYPE).post(ClientResponse.class, formData);
			System.out.println("Response " + response.getEntity(String.class));
		}
		catch (Throwable e)
		{
			int i = 0;
		}
	}

	
	// передаем POJO в пост запрос
	@Test
	public void entry2()
	{
		try
		{
			ClientConfig config = new DefaultClientConfig();
			//config.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, true);
			Client client = Client.create(config);

			WebResource resource = client.resource("http://localhost:8080/myserver/rf_resources/book/add.book");

			//Делаем json запрос
			ClientResponse response = resource.post(ClientResponse.class, new Book("Книга 1 из теста"));
			//Надо разобраться как сделать так что бы json парсился в Book, xml парсится, тогда и запрос можно будет в json делать : resource.type(MediaType.APPLICATION_JSON+" ;charset=UTF-8").post(.....
			Book book1 = response.getEntity(Book.class);
			System.out.println("Книга под названием " + book1.getName());

			//или
			Book book2 = resource.post(Book.class, new Book("Книга 2 из теста"));
			System.out.println("Книга под названием " + book2.getName());
		}
		catch (Throwable e)
		{
			int i = 0;
		}
	}

}
