package com.tools.logger;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.logging.LogLevel;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 * Интеграционный тест инструмента логирования
 *
 * @author sbt-shumeyko-am
 * @ created 30.10.2017
 * @ $Author$
 * @ $Revision$
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {com.learn.MainApp.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestLog
{
	private static final String LOGGING_LEVEL_ROOT = "logging.level.root";

	/**
	 * Изменим уровень логирования на максимальный
	 */
	@BeforeClass
	public static void configure()
	{
		System.setProperty(LOGGING_LEVEL_ROOT, LogLevel.DEBUG.name());
	}

	/**
	 * Тест вывода всех уровней логирования
	 */
	@Test
	public void loggerTest() throws IOException
	{
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		PrintStream stdout = System.out;
		PrintStream stderr = System.err;
		String lineSeparator = System.getProperty("line.separator");
		try (PrintStream out = new PrintStream(byteArrayOutputStream, true, StandardCharsets.UTF_8.name()))
		{
			System.setOut(out);
			System.setErr(out);

			//Пытаемся залогировать все возможные уровни
			Log log = new Log(TestLog.class);
			log.debug(new LogMessage().put(LogLevel.DEBUG.name(), "Отладка"));
			log.info(new LogMessage().put(LogLevel.INFO.name(), "Информация"));
			log.warning(new LogMessage().put(LogLevel.WARN.name(), "Предупреждение"));
			log.error(new LogMessage().put(LogLevel.ERROR.name(), "Ошибка"));
			log.exception(new Throwable("Исключительная ситуация"));

			//Ожидаемые строки лога без даты времени
			Properties expectedProperties = new Properties();
			expectedProperties.load(TestLog.class.getResourceAsStream("expected.properties"));

			//Актуальные строки лога
			String logStream = new String(byteArrayOutputStream.toByteArray(), StandardCharsets.UTF_8);
			List<String> actualValues = Arrays.asList(logStream.split(lineSeparator));

			for (String expected : expectedProperties.values().toArray(new String[1]))
			{
				//Если ожидаемой строки лога нет в потоке вывода, то ошибка
				if (actualValues.stream().noneMatch(s -> s.contains(expected)))
				{
					StringBuilder stringBuilder = new StringBuilder("Ожидаемая строка в логе не найдена, текст строки: ");
					stringBuilder.append(lineSeparator).
						append(expected).
						append(lineSeparator).append(lineSeparator).
						append("Актуальные строки лога, НАЧАЛО:").
						append(lineSeparator).
						append(logStream).
						append("Актуальные строки лога, КОНЕЦ.").
						append(lineSeparator).append(lineSeparator);
					org.junit.Assert.fail(stringBuilder.toString());
				}
			}
		}
		finally
		{
			System.setOut(stdout);
			System.setErr(stderr);
		}
	}
}