// 3)

//Cookie

package servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = "/hw_cookie")
public class HelloWorldCookie
  extends HttpServlet
{
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
  {
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
    
    resp.setContentType("text/html");
    PrintWriter pw = resp.getWriter();
    pw.println("HelloWorldCookie " + myCookie.getValue());
    pw.close();
  }
}
