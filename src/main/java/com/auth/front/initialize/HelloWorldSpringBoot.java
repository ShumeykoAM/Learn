package com.auth.front.initialize;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
//@EnableAutoConfiguration
@SpringBootApplication
public class HelloWorldSpringBoot
{
	@RequestMapping("/") //http://localhost:8080, порт можно изменить в src/main/resources/application.properties или в параметрах args
	@ResponseBody
	String home()
	{
		return "Hello World SpringBoot!";
	}

	public static void main(String[] args) throws Exception
	{
		SpringApplication.run(HelloWorldSpringBoot.class, args);
	}
}