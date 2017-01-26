// 2) Сервлета

package servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

//Описываем класс сервлет - наследник от javax.servlet.http.HttpServlet

//Если не использовать аннотации, тогда нужно добавлять информацию о сервлетах в web.xml в каиалоге WEB-INF
// urlPatterns - это список относительных путей в броузере к данной странице сервлета (пути относительно корневого пути)
@WebServlet(urlPatterns = {"/FindBooks/FindMethodPost", "/FindBooks/FindMethodGet"})
public class FindBooks
  extends HttpServlet
{
  //Данный метод вызывается один раз за время жизни сервлета при создании сервлета
  @Override
  public void init() throws ServletException
  {
    //Здесь можно выделить необходимые ресурсы
  }
  //Данный метод вызывается один раз за время жизни сервлета при уничтожении сервлета
  @Override
  public void destroy()
  {
    //Здесь надо освободить ресурсы
  }
  
  //Обрабатываем запрос использующий метод GET
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
  {
    //Описание др. возможностей находится ниже в теле метода doPost
    
    PrintWriter pw = resp.getWriter();
    pw.println("Use method get");
    pw.close();
  }
  //Обрабатываем запрос использующий метод POST
  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
  {
    //исключение IOException могут бросить методы
    //PrintWriter pw = resp.getWriter();
    //pw.close();
  
    //исключение ServletException можно бросать, если, например, невозможно обработать запрос (не передали параметры)
    if(req.getParameterMap().size() < 1)
      throw new ServletException("not enugh params");
    
    //Можно получить карту параметров переданных в запросе
    Map<String, String[]> pm =  req.getParameterMap();
    //Или значение параметра по имени параметра
    String par1 = req.getParameter("FindText");
    
    //Устанавливаем тип Mime ответа (Mime типы данных, которые могут быть переданы посредством сети интернет с применением стандарта MIME)
    resp.setContentType("text/html");
    
    PrintWriter pw = resp.getWriter();
    pw.println("Use method post");
    pw.close();
  }
  //Кроме методов GET и POST есть и другие методы обработки запросов:
  //  HEAD, PUT, DELETE, OPTIONS, TRACE
  
}
