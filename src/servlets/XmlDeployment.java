// 5)

//Пример реализации сервлеты без использования аннотаций
//  разворачивание происходит с помощью web\WEB-INF\web.xml

package servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


//Аннотация не нужна так как развертывание данной сервлеты расписано в web\WEB-INF\web.xml
//@WebServlet(urlPatterns = {"/web_xml_deployment", "/deployment_from_xml"})
public class XmlDeployment
  extends HttpServlet
{
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
  {
    
    PrintWriter pw = resp.getWriter();
    pw.println("I deployed with help web.xml");
    pw.close();
  }
}
