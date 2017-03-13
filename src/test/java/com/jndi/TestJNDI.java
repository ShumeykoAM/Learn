package com.jndi;

import com.cdi.Book;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * @author sbt-shumeyko-am
 * @ created 13.03.2017
 * @ $Author$
 * @ $Revision$
 */

public class TestJNDI
{
    @Test
    public void entryPoint()
    {
        try
        {
            //Получаем контекст
            InitialContext ic= new InitialContext(); //У разных серваков своя реализация получения и инициализации контекста
            //Ищем объект в именованной директории JNDI
            Book foundObject = (Book)ic.lookup("java:/comp/env/Books/BasicBook");
            
            Assert.assertEquals("JNDI is great!", foundObject.getName());
        } catch(NamingException e)
        {
            Assert.fail("Object not found");
        }
    }
    
    @BeforeClass
    public static void beforeClassTest()
    {
        //Что бы работать автономно создадим свой контекст, с помощью библиотек TomCat
        try
        {
            //Укажем для системы InitialContextFactory, будем использовать библиотеки tomcat для этого
            //apache-tomcat-7.0.34/bin/tomcat-juli.jar
            //apache-tomcat-7.0.34/lib/catalina.jar
            System.setProperty(Context.INITIAL_CONTEXT_FACTORY, "org.apache.naming.java.javaURLContextFactory");
            System.setProperty(Context.URL_PKG_PREFIXES, "org.apache.naming");
        
            //Создаем контекст на основе вышезаданных системных настроек
            InitialContext ic = new InitialContext();
        
            //Создаем директорию
            ic.createSubcontext("java:");
            ic.createSubcontext("java:/comp");
            ic.createSubcontext("java:/comp/env");
            ic.createSubcontext("java:/comp/env/Books");
        
            //Привязываем объект
            ic.bind("java:/comp/env/Books/BasicBook", new Book("JNDI is great!"));
        } catch(NamingException e)
        {
            e.printStackTrace();
        }
    }
    
}
