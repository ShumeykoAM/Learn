package com.learn;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Пример конечной точки Привет Мир
 * @author Kot
 * @ created 18.02.2018
 * @ $Author$
 * @ $Revision$
 */
@Controller
public class HelloController
{
	/**
	 * @return Домашняя страница http://localhost:8080
	 */
	@RequestMapping("/")
	@ResponseBody
	String home()
	{
		return "Hello World SpringBoot!";
	}
}
