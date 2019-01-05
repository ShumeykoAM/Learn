package com.learn.jsp;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Контроллер возвращающий jsp страницу
 * @author Kot
 * @ created 02.01.2019
 * @ $Author$
 * @ $Revision$
 */
@Controller
public class myFirstJspController
{
	/**
	 * @return Страница Jsp http://localhost:80/first
	 */
	@RequestMapping("/first")
	public String first(Model model)
	{
		model.addAttribute("message", "Hello world jsp!!");
		return "first";
	}
}
