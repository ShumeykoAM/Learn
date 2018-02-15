package com.example.rest;

import com.example.rest.requests.JsonRequest;
import com.example.rest.responsies.JsonResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Примеры разнообразных Rest сервисов
 * @author Kot
 * @ created 14.02.2018
 * @ $Author$
 * @ $Revision$
 */
@RestController
public class MyRestController
{
	/**
	 * Rest сервис без параметров
	 * @return
	 */
	@RequestMapping(value = {"/rest/no/params"})
	@ResponseBody
	public ResponseEntity<String> welcomePage()
	{
		return new ResponseEntity<String>("Привет, я простой обработчик Rest запроса без параметров!", HttpStatus.OK);
	}

	@RequestMapping(value = {"/rest/json/post/request/response"}, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<JsonResponse> welcomePage(@Valid @RequestBody JsonRequest request, BindingResult result)
	{
		result.hasErrors();
		result.hasFieldErrors();

		return new ResponseEntity<JsonResponse>(new JsonResponse(false), HttpStatus.OK);
	}
}
