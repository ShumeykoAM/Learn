package com.learn;

import com.tools.logger.Log;
import com.tools.logger.LogMessage;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;

import static com.learn.MainApp.uncaughtExceptionHandler;

/**
 * Перехватчик всех неперехваченных исключений
 * @author Kot
 * @ created 18.02.2018
 * @ $Author$
 * @ $Revision$
 */
@ControllerAdvice
public class AllExceptionHandler
{
	private static final Log LOG = new Log(MainApp.class);

	/**
	 * Перехватим все неперехваченные исключения
	 * @param request
	 * @param th
	 */
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(Throwable.class)
	@Order
	public void CatchUncaughtException(HttpServletRequest request, Throwable th)
	{
		uncaughtExceptionHandler.uncaughtException(Thread.currentThread(), th);
		LOG.error(new LogMessage()
			.put("ServletPath", request.getServletPath())
			.put("Method", request.getMethod())
			.put("errorType", "UncaughtException"));
	}
}
