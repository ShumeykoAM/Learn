package ru.mysite.web.servlets;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Kot on 27.08.2016.
 */
public class SimleServlet
  extends HttpServlet
{
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
  {
    resp.setCharacterEncoding("UTF-8");
    resp.getWriter().write("Hello wolf Прювэт!");
  }
}
