package com.learn.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.learn.rest.requests.JsonRequest;
import com.learn.rest.responsies.JsonResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.TreeMap;
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
	 * @return ответ
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

		manualSerializeToJson();

		return new ResponseEntity<JsonResponse>(new JsonResponse(false), HttpStatus.OK);
	}

	/**
	 * Пример ручной конвертации из Pojo в Json
	 */
	private void manualSerializeToJson()
	{
		Request r = new Request();
		Request.Value value = new Request.Value();
		Map map = new TreeMap<String, String>(){{put("RefId", "Identif");}};
		value.setValueResult(new Map[]{map});
		r.setValue(value);

		ObjectMapper mapper = new ObjectMapper();
		try
		{
			String m = mapper.writeValueAsString(r);
			m = null;
		}
		catch (JsonProcessingException e)
		{
			e.printStackTrace();
		}
	}
}

class Request
{
	private String Result;

	private Value Value;

	public String getResult ()
	{
		return Result;
	}

	public void setResult (String Result)
	{
		this.Result = Result;
	}

	public Value getValue ()
	{
		return Value;
	}

	public void setValue (Value Value)
	{
		this.Value = Value;
	}

	public static class Value
	{
		private Map<String, String>[] ValueResult;

		public Map<String, String>[] getValueResult()
		{
			return ValueResult;
		}

		public void setValueResult(Map<String, String>[] valueResult)
		{
			ValueResult = valueResult;
		}
	}
}