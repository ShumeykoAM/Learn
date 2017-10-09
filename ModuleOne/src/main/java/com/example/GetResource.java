package com.example;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * Пример получения ресурсов через методы getResource и getResourceAsStream класса Class, используя два способа формирования пути.
 * @author Kot
 * @ created 04.09.2017
 * @ $Author$
 * @ $Revision$
 */
public class GetResource
{
	static String getResource(String rsc)
	{
		String val = "";
		try
		{
			// input stream
			InputStream i = GetResource.class.getResourceAsStream(rsc);
			//URL
			URL url = GetResource.class.getResource(rsc);
			url.toString();

			BufferedReader r = new BufferedReader(new InputStreamReader(i));
			// reads each line
			String l;
			while ((l = r.readLine()) != null)
			{
				val = val + l;
			}
			i.close();
		}
		catch (Exception e)
		{
			System.out.println(e);
		}
		return val;
	}

	//!!! Что бы в консоли кодировка нормальная была нужно в параметры запуска добавить "-Dconsole.encoding=UTF-8"
	public static void main(String[] args)
	{
		//Если имя ресурса НЕ начинается с "/", как в примере ниже, это значит что поиск ресурса будет происходить по относительному пути,
		// к classpath или к корню jar ника прибавиться имя пакета класса, чей Class мы получили, плюс имя ресурса
		System.out.println("File1: " + getResource("resource.txt"));
		System.out.println("File2: " + getResource("subdir/resource.txt"));

		//Если имя ресурса начнется с "/", как в примере ниже, это значит что поиск ресурса будет происходить по абсолютному пути относительно
		// classpath и корня jar ника плюс имя ресурса
		System.out.println("File2: " + getResource("/com/example/resource.txt"));
		System.out.println("File2: " + getResource("/com/example/subdir/resource.txt"));

		//То есть оба способа отличаются наличием\отсутствием в пути к ресурсу имени пакета класса, чей Class использовался для поиска ресурса.
	}
}
