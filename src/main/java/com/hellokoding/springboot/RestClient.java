package com.hellokoding.springboot;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.TreeMap;
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
		CreateTransactionRequest request = new CreateTransactionRequest();
		request.setOperationCode("opcode");
		request.setDocument("doc".getBytes());
		request.setParameters(new TreeMap<String, String>(){{put("p1", "v1"); put("p2", "v2");}});
		ObjectMapper mapper = new ObjectMapper();
		try
		{
			String m = mapper.writeValueAsString(request);
			m = null;
		}
		catch (JsonProcessingException e)
		{
			e.printStackTrace();
		}


		String url = "http://localhost:8080/mj";
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

class CreateTransactionRequest
{
	@JsonProperty("OperationCode")
	private String operationCode;

	@JsonProperty("Document")
	private byte[] document;

	@JsonProperty("Parameters")
	private Map<String, String> parameters;

	public String getOperationCode()
	{
		return operationCode;
	}

	public void setOperationCode(String operationCode)
	{
		this.operationCode = operationCode;
	}

	public byte[] getDocument()
	{
		return document;
	}

	public void setDocument(byte[] document)
	{
		this.document = document;
	}

	public Map<String, String> getParameters()
	{
		return parameters;
	}

	public void setParameters(Map<String, String> parameters)
	{
		this.parameters = parameters;
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