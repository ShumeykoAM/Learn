package com.learn;

import com.tools.logger.Log;
import com.tools.logger.UncaughtExceptionHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Инициализация и запуск Spring Boot
 *
 * @author sbt-shumeyko-am
 * @ created 17.10.2017
 * @ $Author$
 * @ $Revision$
 */
@SpringBootApplication
public class MainApp
{
	public static final UncaughtExceptionHandler uncaughtExceptionHandler = new UncaughtExceptionHandler();
	private static final Log LOG = new Log(MainApp.class);

	/**
	 * Точка входа
	 * @param args можно переопределить параметры SpringBoot передав их в параметрах args, например: --server.port=8090
	 */
	public static void main(String[] args)
	{
		Thread.setDefaultUncaughtExceptionHandler(uncaughtExceptionHandler);
		List<String> lArgs = new LinkedList<>(Arrays.asList(args));
		args = lArgs.toArray(args);
		SpringApplication.run(MainApp.class, args);
	}
}
