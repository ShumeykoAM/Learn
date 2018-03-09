package com.learn.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;

/**
 * TODO Description
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
	 * @param result
	 * @return
	 */
	@RequestMapping(value = {"/rest/post"}, method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> restPost(@Valid @RequestBody String request, BindingResult result)
	{
		requests.add(request);
		return new ResponseEntity<String>("", HttpStatus.OK);
	}

	/**
	 * Можно получить количество полученных запросов выше
	 * @return
	 */
	@RequestMapping(value = {"/rest/post/count"}, method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> restPostCount()
	{
		return new ResponseEntity<String>(new Integer(requests.size()).toString(), HttpStatus.OK);
	}

	/**
	 * Можно получить запрос полученный ранее в обработчике restPost, указав индекс (начинается с 0)
	 * @param request
	 * @param result
	 * @return
	 */
	@RequestMapping(value = {"/rest/post/pull"}, method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> restPostPull(@Valid @RequestBody Integer request, BindingResult result)
	{
		String s = "Наверное запросов еще не было, или какая то другая ошибка.";
		try
		{
			if(request == -1)
				request = requests.size()-1;
			s = requests.get(request);
		}
		catch (Throwable th)
		{		}
		return new ResponseEntity<String>(s, HttpStatus.OK);
	}

}
