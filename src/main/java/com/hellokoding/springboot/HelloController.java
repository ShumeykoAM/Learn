package com.hellokoding.springboot;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.hellokoding.springboot.validator.CorrectDate;
import org.springframework.core.annotation.Order;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Calendar;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
public class HelloController
{
/*	@InitBinder
	protected void init(WebDataBinder webDataBinder)
	{

	}*/

	@RequestMapping(value = {"/", "/hell"}, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<Book> welcomePage(@Valid @RequestBody Book p1, BindingResult result)
	{
		//return new ResponseEntity<Book>(new Book("dd", 34, Calendar.getInstance().toString()), HttpStatus.OK);
		return new ResponseEntity<Book>(new Book("dd", 34, Calendar.getInstance().toString()), HttpStatus.BAD_REQUEST);
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(HttpMessageNotReadableException.class)
	@Order
	@ResponseBody
	public Book workEx(HttpMessageNotReadableException ex)
	{
		try
		{
			JsonMappingException mappingException = (JsonMappingException) ex.getCause();
			String fieldName = mappingException.getPath().get(0).getFieldName();
			return new Book("Ошибка параметра " + fieldName, 0, Calendar.getInstance().toString());
		}
		catch (Exception e)
		{
			return getCommonFailure();
		}
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(Exception.class)
	@Order
	@ResponseBody
	public Book workEx(Exception ex)
	{
		return getCommonFailure();
	}

	private Book getCommonFailure()
	{
		return new Book("Общий сбой!", 305, Calendar.getInstance().toString());
	}

	public static class Book
	{
		@JsonProperty("n m")
		@NotNull
		private String name;

		@JsonProperty("c o d e")
		@JsonSerialize(using = IntToStringSerializer.class)
		@JsonDeserialize(using = StringToIntSerializer.class)
		private Integer code;

		@CorrectDate(dateFormat = "dd.MM.YYYY")
		private String date;

		public Book()
		{
		}

		public Book(String name, Integer code, String date)
		{
			this.name = name;
			this.code = code;
			this.date = date;
		}

		public String getName()
		{
			return name;
		}

		public void setName(String name)
		{
			this.name = name;
		}

		public Integer getCode()
		{
			return code;
		}

		public void setCode(Integer code)
		{
			this.code = code;
		}

		public String getDate()
		{
			return date;
		}

		public void setDate(String date)
		{
			this.date = date;
		}

		public static class StringToIntSerializer extends JsonDeserializer<Integer>
		{
			@Override
			public Integer deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException
			{
				Integer result = null;
				try
				{
					result = Integer.parseInt(p.getText());
				}
				catch (NumberFormatException ex)
				{
					ctxt.reportMappingException("Тут брозается исключение дальше и перехват произойдет в @ExceptionHandler!");
				}
				return result;
			}
		}

		public static class IntToStringSerializer extends JsonSerializer<Integer>
		{
			@Override
			public void serialize(Integer value, JsonGenerator gen, SerializerProvider serializers) throws IOException, JsonProcessingException
			{
				gen.writeString(value.toString());
			}
		}
	}

	public static void main(String args[])
	{
		RestTemplate restTemplate = new RestTemplate();
		try
		{
			Book book = new Book();
			book.setName("имя");
			book.setCode(12345);
			book.setDate(Calendar.getInstance().toString());

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<Book> entity = new HttpEntity<Book>(book, headers);
			ResponseEntity<Book> response = restTemplate.postForEntity("http://localhost:8080/hell", entity, Book.class);
			//Можно и так ResponseEntity<Book> response = restTemplate.exchange("http://localhost:8080/hell", HttpMethod.POST, entity, Book.class);
			book = response.getBody();
			response = null;
		}
		catch (HttpClientErrorException ex)
		{
			try
			{
				ObjectMapper mapper = new ObjectMapper();
				Book book = null;
				book = mapper.readValue(ex.getResponseBodyAsString(), Book.class);
				book = null;
			}
			catch (IOException e)
			{
				int ffff = 0;
			}

		}
	}

}
