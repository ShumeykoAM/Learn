package com.example;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Пример работы с пропертями (файлами *.properties)
 * @author sbt-shumeyko-am
 * @ created 07.09.2017
 * @ $Author$
 * @ $Revision$
 */
public class GetProperties
{
	
	//!!! Что бы в консоли кодировка нормальная была нужно в параметры запуска добавить "-Dconsole.encoding=UTF-8"
	public static void main(String[] args) throws IOException
	{
		//Получаем ресурс - файл с пропертями, см ModuleOne\src\main\java\com\example\GetResource.java
		InputStream inputStream = GetProperties.class.getResourceAsStream("/com/example/properties/someProps.properties");
		Properties properties = new Properties();
		properties.load(inputStream);
		for (String name : properties.stringPropertyNames())
		{
			String value = properties.getProperty(name);
			System.out.print(name + " = ");
			System.out.println(value);
		}
	}
}
