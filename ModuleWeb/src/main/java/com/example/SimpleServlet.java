package com.example;

import com.example.ejb.SimpleSingleton;

import java.io.IOException;
import java.io.PrintWriter;
import javax.ejb.EJB;
import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
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
	SimpleSingleton simpleSingleton;
	

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

		//https://www.journaldev.com/9906/jms1-1-with-eclipse-and-jboss6-example
		try
		{
			Context context = new InitialContext();
			
			//Когда запущен сервак glassfish нужно выполнить команды, что бы добавить фабрику и очередь на сервак, потом их можно лукапить и использовать
			//asadmin create-jms-resource --restype javax.jms.ConnectionFactory com.example.jms.ConnectionFactory
			ConnectionFactory factory = (ConnectionFactory) context.lookup("com.example.jms.ConnectionFactory");
			//asadmin create-jms-resource --restype javax.jms.Queue com.example.jms.MyQueue
			Destination queue = (Destination)context.lookup("com.example.jms.MyQueue");

			Connection connection = factory.createConnection();
			connection.start();
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			MessageProducer producer = session.createProducer(queue);
			Message message = session.createMessage();
			message.setStringProperty("param1", "value_for_param");
			producer.send(message);
			int fff = 0;
		}
		catch (NamingException e)
		{
			e.printStackTrace();
		}
		catch (JMSException e)
		{
			e.printStackTrace();
		}
	}

	public void destroy()
	{

	}
}
