package com.hellokoding.springboot;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
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
	public @ResponseBody Book welcomePage(@Valid @RequestBody Book p1, BindingResult result)
	{

		return new Book("dd", 34);
	}


	/*@RequestMapping("/hello")
	public String hello(Map<String, Object> model)
	{
		//model.addAttribute("name", name);
		return "hello";
	}*/

	public static class Book
	{
		@JsonProperty("n m")
		@NotNull
		private String name;

		@JsonProperty("c o d e")
		@JsonSerialize(using = IntToStringSerializer.class)
		@JsonDeserialize(using = StringToIntSerializer.class)
		private Integer code;

		public Book()
		{
		}

		public Book(String name, Integer code)
		{
			this.name = name;
			this.code = code;
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

		public static class StringToIntSerializer extends JsonDeserializer<Integer>
		{
			@Override
			public Integer deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException
			{
				return Integer.parseInt(p.getText());
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
