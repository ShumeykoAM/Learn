// 4)

// Сессии (так называемый URL RE-writing)

package servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;


@WebServlet(urlPatterns = "/hw_session")
public class HelloWorldSession
  extends HttpServlet
{
  static class SomeData
  {
    public int count;
  }
  
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
  {
    //Запросить существующую сессию, если нету, то true означает, что ее надо создать
    HttpSession session = req.getSession(true);
  
    SomeData someData = (SomeData)session.getAttribute("SomeData"); //Попытаемся получить объект из сессии
    if(someData == null)
      someData = new SomeData();                   //  если его там нету, то создаем новый
    someData.count++;
    session.setAttribute("SomeData", someData);    //Помещаем (замещаем) объект в сессию как именованный атрибут
  
    if(someData.count == 6)
      session.invalidate();  //Сессию можно завершить
    
    resp.setContentType("text/html");
    PrintWriter pw = resp.getWriter();
    pw.println("HelloWorldSession " + someData.count);
    pw.close();
  }

}

//Есть еще способ хранить структурированную информацию на клиенте
//HTML5 supports Web Storage