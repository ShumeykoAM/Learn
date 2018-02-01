package com.hellokoding.springboot;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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

		return new Book("dd");
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

		public Book()
		{
		}

		public Book(String name)
		{
			this.name = name;
		}

		public String getName()
		{
			return name;
		}

		public void setName(String name)
		{
			this.name = name;
		}
	}
}
