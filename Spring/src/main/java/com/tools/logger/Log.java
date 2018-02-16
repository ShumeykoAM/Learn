package com.tools.logger;

import org.slf4j.LoggerFactory;

/**
 * Обёртка для логгера
 *
 * @author kot
 * @ created 24.10.2017
 * @ $Author$
 * @ $Revision$
 */
public class Log
{
	private final org.slf4j.Logger logger;

	public Log(Class clazz)
	{
		logger = LoggerFactory.getLogger(clazz);
	}

	public void exception(Throwable th)
	{
		error(new LogMessage()
			.put("ExceptionClass", th.getClass().getName())
			.put("ExceptionMessage", th.getMessage()));
	}

	public void debug(LogMessage message)
	{
		logger.debug(message.getMessageString());
	}

	public void error(LogMessage message)
	{
		logger.error(message.getMessageString());
	}

	public void warning(LogMessage message)
	{
		logger.warn(message.getMessageString());
	}

	public void info(LogMessage message)
	{
		logger.info(message.getMessageString());
	}
}
