package com.struts;

import java.io.IOException;
import javax.servlet.*;

/**
 * @author Kot
 * @ created 25.02.2017
 * @ $Author$
 * @ $Revision$
 */
//Данный класс нужен, для правильной работы с кириллицей в формах
public class CharsetFilter
	implements Filter
{
	@Override public void init(FilterConfig filterConfig) throws ServletException
	{
		
	}
	@Override public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException
	{
		servletRequest.setCharacterEncoding("UTF-8");
		filterChain.doFilter(servletRequest, servletResponse);
	}
	@Override public void destroy()
	{

	}
}
