package com.example;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author sbt-shumeyko-am
 * @ created 30.08.2017
 * @ $Author$
 * @ $Revision$
 */

public class SimpleServlet extends HttpServlet
{
	private String message;

	public void init() throws ServletException
	{
		message = "Hello World";
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		//Для доступа к объектам из другого модуля надо добавить зависимости в текущем модуле в файле .gradle
		//  после сборки war ника jar ник из модуля от которого зависим попал в наш war ник (тут надо разобраться что если у нас 2 war ника)
		MyObject myObject = new MyObject();
		myObject.i = 9;
		myObject.getI();
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<h1>" + message + "</h1>");
	}

	public void destroy()
	{

	}
}
