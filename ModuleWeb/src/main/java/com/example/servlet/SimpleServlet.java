package com.example.servlet;

import com.example.MyObject;
import com.example.ejb_jms.SimpleSingleton;
import com.example.ejb_jms.SimpleStateful_Jms;
import com.example.ejb_jms.SimpleStateless;

import java.io.IOException;
import java.io.PrintWriter;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author sbt-shumeyko-am
 * @ created 30.08.2017
 * @ $Author$
 * @ $Revision$
 */

@WebServlet("/example/SimpleServlet")
public class SimpleServlet extends HttpServlet
{
	private String message;

	@EJB
	private SimpleSingleton simpleSingleton;
	@EJB
	private SimpleStateless simpleStateless;
	@EJB
	private SimpleStateful_Jms simpleStateful;

	@Override
	public void init() throws ServletException
	{
		message = "Hello World";
	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		//Для доступа к объектам из другого модуля надо добавить зависимости в текущем модуле в файле .gradle
		//  после сборки war ника jar ник из модуля от которого зависим попал в наш war ник (тут надо разобраться что если у нас 2 war ника)
		MyObject myObject = new MyObject();
		myObject.i = 9;
		myObject.getI();
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<h1>" + message + " " + getStringForHelloWorld() + "</h1>");
	}

	public String getStringForHelloWorld()
	{
		String stringForHW = simpleSingleton.getCommonField() + " " + simpleStateless.sayHelloWorld() + " " + simpleStateful.sayHelloWorld();
		if(simpleStateful.getCount() > 5)
			simpleStateful.endSession();
		return stringForHW;
	}

	@Override
	public void destroy()
	{

	}
}
