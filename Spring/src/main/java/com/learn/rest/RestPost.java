package com.learn.rest;

import com.google.common.base.Joiner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * Сохранитель post запросов, для дальнейшего их изучения
 * @author Kot
 * @ created 09.03.2018
 * @ $Author$
 * @ $Revision$
 */
@RestController
public class RestPost
{
	private List<String> requests = new ArrayList<String>();

	/**
	 * Сюда можно слать любые POST запросы
	 * @param request
	 * @param httpRequest
	 * @return
	 */
	@RequestMapping(value = {"/rest/post", "/rest/post/service/dss/v1/DSSResultOfConfirmOperation"}, method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> restPost(@Valid @RequestBody String request, HttpServletRequest httpRequest)
	{
		request += "\n\n\n POST ";
		calcFullRequest(request, httpRequest);
		return new ResponseEntity<String>("", HttpStatus.OK);
	}

	/**
	 * Сюда можно слать любые POST запросы
	 * @param request
	 * @param httpRequest
	 * @return
	 */
	@RequestMapping(value = {"/rest/put"}, method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<String> restGet(@Valid @RequestBody String request, HttpServletRequest httpRequest)
	{
		request += "\n\n\n PUT ";
		calcFullRequest(request, httpRequest);
		return new ResponseEntity<String>("", HttpStatus.OK);
	}

	String calcFullRequest(String request, HttpServletRequest httpRequest)
	{
		List<String> headers = new ArrayList<>();
		Enumeration<String> headerNames = httpRequest.getHeaderNames();
		do
		{
			String headerName = headerNames.nextElement();
			headers.add("name:     " + headerName + "\n  value:  " + httpRequest.getHeader(headerName));
		}
		while (headerNames.hasMoreElements());
		request += "\n\n\n" + Joiner.on('\n').join(headers);
		requests.add(request);
		return request;
	}

	/**
	 * Можно получить количество полученных запросов выше
	 * @return
	 */
	@RequestMapping(value = {"/rest/count"}, method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<String> restPostCount()
	{
		return new ResponseEntity<String>(new Integer(requests.size()).toString(), HttpStatus.OK);
	}

	/**
	 * Можно получить запрос полученный ранее в обработчике restPost, указав индекс (начинается с 0)
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/rest/pull/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<String> getFoosBySimplePathWithPathVariable(@PathVariable("id") int request)
	{
		String s = "Наверное запросов еще не было, или какая то другая ошибка.";
		try
		{
			if (request == -1)
				request = requests.size() - 1;
			s = requests.get(request);
		}
		catch (Throwable th)
		{		}
		return new ResponseEntity<String>(s, HttpStatus.OK);
	}
}
