package com;

import com.cdi.Book;
import com.cdi.BookService;
import com.services.Service1;
import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;

import java.net.URL;

/**
 * @author sbt-shumeyko-am
 * @ created 09.03.2017
 * @ $Author$
 * @ $Revision$
 */
public class Main
{
    public static void main(String ... args)
    {
        //Иногда полезно узнать текущий путь в файловой системе
        URL location = Main.class.getProtectionDomain().getCodeSource().getLocation();


        Service1 service1 = new Service1();
        service1.f(null);
        try
        {
            Weld weld = new Weld();
            //weld.property()
            WeldContainer container = weld.initialize();

            BookService bookService = container.instance().select(BookService.class).get();
            Book book = bookService.createBook("H2G2");
            
            weld.shutdown();
        }
        catch (Throwable e)
        {
            int f = 0;
        }
    }
}
