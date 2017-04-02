package com.jaxb;

import com.services.restful.Book;

import java.io.StringReader;
import java.io.StringWriter;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

/**
 * @author kot
 * @ created 02.04.2017
 * @ $Author$
 * @ $Revision$
 */
public class Main
{
	public static void main(String args[])
	{
		try
		{
			//Маршализация объекта
			String sdata = marshal(Data.class, new Data("логин", "Имя", "Телефон", new Data.Address("Россия")));
			Data data = unmarshal(Data.class, sdata);

			//Маршализация объекта со списком объектов
			DataCentre dataCentre = new DataCentre();
			dataCentre.add(data);
			dataCentre.add(new Data("логин2", "Имя2", "789639449", new Data.Address("Беларусь")));
			String scentre = marshal(DataCentre.class, dataCentre);
			DataCentre unDataCentre = unmarshal(DataCentre.class, scentre);

			String sBook = marshal(Book.class, new Book("Супер книга"));
			int i = 0;
		}
		catch (JAXBException e)
		{
			e.printStackTrace();
		}
	}

	private static <D> String marshal(Class<D> dClass, D data) throws JAXBException
	{
		StringWriter sw = new StringWriter();               //Поток вывода в строку
		//Маршализируем объект в строку с xml
		JAXBContext context = JAXBContext.newInstance(dClass);  //Создаем контекст JAXB для маршализируемого типа
		Marshaller marshaller = context.createMarshaller();         //Создаем маршалер из конткста
		marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
		marshaller.marshal(data, sw);                               //Маршализируем объект в строку
		return sw.toString();
	}
	private static <D> D unmarshal(Class<D> dClass, String sdata) throws JAXBException
	{
		//Демаршализируем xml строку в объект
		JAXBContext context = JAXBContext.newInstance(dClass);  //Создаем контекст JAXB для маршализируемого типа
		Unmarshaller unmarshaller = context.createUnmarshaller();   //Создаем демаршалер из конткста
		StringReader sr = new StringReader(sdata);
		D data = (D)unmarshaller.unmarshal(sr);               //Демаршализируем объект из строки
		return data;
	}
}
