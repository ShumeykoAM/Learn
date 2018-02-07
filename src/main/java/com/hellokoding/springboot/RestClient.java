package com.hellokoding.springboot;

import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;

/**
 * @author sbt-shumeyko-am
 * @ created 07.02.2018
 * @ $Author$
 * @ $Revision$
 */
@RestController
public class RestClient
{

	@RequestMapping(value = {"/client"}, method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,produces = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	@ResponseBody
	public ResponseEntity<String> welcomePage(@Valid Par p1, BindingResult result)
	{
		return new ResponseEntity<String>("Все плохо", HttpStatus.BAD_REQUEST);
	}

	
	public static void main(String args[])
	{
		String url = "http://localhost:8080/client";
		RestTemplate template = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
		map.add("name", "Имя");

		HttpEntity<MultiValueMap<String, String>> requestEntity= new HttpEntity<MultiValueMap<String, String>>(map, headers);
		String token = "";
		try{
			String response = template.postForObject(url, requestEntity,  String.class);
			response = null;
		}
		catch(Exception e){
			token = e.getMessage();
		}
	}

}

class Par
{
	private String name;

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}
}