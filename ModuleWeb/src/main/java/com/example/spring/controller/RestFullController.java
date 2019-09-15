package com.example.spring.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Контроллер rest запросв
 *
 * @ created 15.09.2019
 */
@Controller
public class RestFullController
{
	/**
	 * Rest сервис
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.POST, path = {"/rest/simpleJsonService",}, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> processAuthentication(@Validated @RequestBody SimpleJsonRequest requestBean, BindingResult bindingResult)
	{
		return new ResponseEntity<>("{}", HttpStatus.OK);
	}
}
