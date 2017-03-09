package cdi;

import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;
import org.jboss.weld.bootstrap.WeldBootstrap;

/**
 * @author sbt-shumeyko-am
 * @ created 09.03.2017
 * @ $Author$
 * @ $Revision$
 */
public class Main
{
    public static void main(String[] args)
    {
        
        try
        {
            //Вот тут почему то не работает, наверное кудато надо копировать web\WEB-INF\beans.xml
            
            WeldBootstrap weldBootstrap = new WeldBootstrap();
            
            Weld weld = new Weld();
            WeldContainer container = weld.initialize();
            BookService bookService =
                container.instance().select(BookService.class).get();
            Book book = bookService.createBook("H2G2");
            System.out.println(book);
            weld.shutdown();
        }
        catch(Throwable e)
        {
            int i = 0;
        }
        
    }
}
