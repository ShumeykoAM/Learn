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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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
	public Book welcomePage(@Valid @RequestBody Book p1, BindingResult result)
	{

		return new Book("dd", 34, Calendar.getInstance());
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
			return new Book("Ошибка параметра " + fieldName, 0, Calendar.getInstance());
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
		return new Book("Общий сбой!", 305, Calendar.getInstance());
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
		private Calendar date;

		public Book()
		{
		}

		public Book(String name, Integer code, Calendar date)
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

		public Calendar getDate()
		{
			return date;
		}

		public void setDate(Calendar date)
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
}
