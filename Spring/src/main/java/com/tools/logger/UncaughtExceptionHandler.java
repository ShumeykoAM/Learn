package com.tools.logger;

/**
 * Обработчик непойманных исключений.
 *
 * @author kot
 * @ created 25.10.2017
 * @ $Author$
 * @ $Revision$
 */
public class UncaughtExceptionHandler implements Thread.UncaughtExceptionHandler
{
	private Log log = new Log(this.getClass());

	@Override
	public void uncaughtException(Thread thread, Throwable throwable)
	{
		log.exception(throwable);
		log.error(new LogMessage()
			.put("ThreadName", thread.getName())
			.put("ThreadId", thread.getId() + "")
			.put("errorType", "UncaughtException"));
	}
}
