package com.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author kot
 * @ created 19.03.2017
 * @ $Author$
 * @ $Revision$
 */

@WebServlet("/server/*")
public class MainServlet
    extends HttpServlet
{
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException
    {
        try
        {
            response.getWriter().println("I worked three");
        }
        catch (IOException e)
        {   }
    }
}
