package servlets;

import ejb.StatefulEJB;
import ejb.StatelessEJB;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author Kot
 * @ created 24.02.2017
 * @ $Author$
 * @ $Revision$
 */
//Добавляем аннотацию помечающую класс как сервлет, то же самое можно далать через web.xml
@WebServlet(urlPatterns = "/my_servlet/annotation")
public class MySimpleServlet
	extends HttpServlet
{
    @EJB StatelessEJB myLessBean;
    @EJB StatefulEJB myFulBean;
    
	static class SomeData //Класс данных для хранения в сессиях
	{
		public int count;
	}
	
	@Override protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		//TODO Cookie
		
		Cookie myCookie = null;
		for(Cookie cookie : req.getCookies())  //Получаем массив cookie от броузера и ищем там нужное мне cookie
			if(cookie.getName().equals("count"))
			{
				myCookie = cookie;                 //Нашли сохраненное раньше cookie
				break;
			}
		if(myCookie == null)
			myCookie = new Cookie("count", "0"); //    иначе не нашли, создаем новую

		//Задаем (меняем) значение, которое хранится в cookie
		myCookie.setValue( ((Integer)(Integer.parseInt(myCookie.getValue()) + 1)).toString() );
		myCookie.setMaxAge(5);     //Время жизни cookie в секундах, после которого оно умирает
		resp.addCookie(myCookie);  //Добавляем в ответ cookie, это cookie будет хранить браузер

		resp.setContentType("text/html; charset=UTF-8");
		PrintWriter pw = resp.getWriter();
		req.setCharacterEncoding("utf-8");
		pw.println("Запрошено " + req.getParameter("RequestText"));
		pw.println("Значение в Cookie = " + myCookie.getValue());
        pw.println(myLessBean.sayHelloWorld());
        pw.println(myFulBean.sayHelloWorld());
		pw.close();
	}

	@Override protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
	    myFulBean.endSession();
	    
		//TODO Сессии (так называемый URL RE-writing)

		//Запросить существующую сессию, если нету, то true означает, что ее надо создать
		HttpSession session = req.getSession(true);

		SomeData someData = (SomeData)session.getAttribute("SomeData"); //Попытаемся получить объект из сессии
		if(someData == null)
			someData = new SomeData();                   //  если его там нету, то создаем новый
		someData.count++;
		session.setAttribute("SomeData", someData);    //Помещаем (замещаем) объект в сессию как именованный атрибут

		if(someData.count == 6)
			session.invalidate();  //Сессию можно завершить

		resp.setContentType("text/html; charset=UTF-8");
		PrintWriter pw = resp.getWriter();
		req.setCharacterEncoding("utf-8");
		pw.println("Запрошено  " + req.getParameter("RequestText"));
		pw.println("Значение в session = " + someData.count);
		pw.close();
	}


	//Есть еще способ хранить структурированную информацию на клиенте
	//HTML5 supports Web Storage
}
