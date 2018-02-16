package com.tools.logger;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.logging.LogLevel;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

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
        System.setProperty(LOGGING_LEVEL_ROOT, LogLevel.TRACE.name());
    }

    /**
     * Тест вывода всех уровней логирования
     */
    @Test
    public void loggerTest() throws IOException
    {
      /*  ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PrintStream stdout = System.out;
        PrintStream stderr = System.err;
        String lineSeparator = System.getProperty("line.separator");
        try (PrintStream out = new PrintStream(byteArrayOutputStream, true, StandardCharsets.UTF_8.name()))
        {
            System.setOut(out);
            System.setErr(out);

            //Пытаемся залогировать все возможные уровни
            LogEx logger = new LogEx(TestLog.class, true);
            logger.exception(LogLevel.TRACE, new LogMessage().put(LogLevel.TRACE.name(), "Трасса"));
            logger.exception(LogLevel.DEBUG, new LogMessage().put(LogLevel.DEBUG.name(), "Отладка"));
            logger.exception(LogLevel.INFO, new LogMessage().put(LogLevel.INFO.name(), "Информация"));
            logger.exception(LogLevel.WARN, new LogMessage().put(LogLevel.WARN.name(), "Предупреждение"));
            logger.exception(LogLevel.ERROR, new LogMessage().put(LogLevel.ERROR.name(), "Ошибка"));
            logger.exception(LogLevel.FATAL, new LogMessage().put(LogLevel.FATAL.name(), "Полный карачун"));
            logger.exception(LogLevel.OFF, new LogMessage().put(LogLevel.OFF.name(), "Пустая операция, ни чего не делает"));
            logger.exception(new Throwable("Исключительная ситуация"));

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
        }*/
    }

    /**
     * Нужен для взведения флажка printStackTrace
     */
    private static class LogEx extends Log
    {
        private LogEx(Class clazz, boolean printStackTrace)
        {
            super(clazz);
            //LogEx.printStackTrace = printStackTrace;
        }
    }
}