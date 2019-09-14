package com.example.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
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

	@Override
	public void init() throws ServletException
	{
		message = "Hello World2";
	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<h1>" + message + "</h1>");
	}

	@Override
	public void destroy()
	{
		message = null;
	}
}
