package com.example.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author sbt-shumeyko-am
 * @ created 14.09.2019
 * @ $Author$
 * @ $Revision$
 */

@WebServlet("/example/SimpleServlet")
public class SimpleServlet extends HttpServlet
{
	private String message;
	private static final Cookie[] COOKIES_EMPTY_ARRAY = new Cookie[0];

	@Override
	public void init() throws ServletException
	{
		message = "first";
	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		Integer count = 1;
		Cookie[] cookies = request.getCookies();
		for (Cookie cookie : (cookies != null ? cookies : COOKIES_EMPTY_ARRAY))
			if ("counter".equals(cookie.getName()))
				count = Integer.valueOf(cookie.getValue()) + 1;

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<h1>" + String.format("Hello, it is your %s visit.", count) + "</h1>");

		//Создаем куку
		Cookie cookie = new Cookie("counter", count.toString());
		//Максимальное время жизни куки
		cookie.setMaxAge(60);

		//Добавляем куку в ответ, браузер сохранит ее у себя
		response.addCookie(cookie);
	}

	@Override
	public void destroy()
	{
		message = null;
	}
}
