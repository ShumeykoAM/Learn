
//TODO надо доразобраться и сделать какой либо простейший пример

//Чтобы данный недоделаный пример начал работать, надо запустить GlassFish сервак, а потом JNDI конфигурацию

package jndi;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Properties;

/**
 * @author sbt-shumeyko-am
 * @ created 06.03.2017
 * @ $Author$
 * @ $Revision$
 */
public class Main
{
    public static void main(String args[])
    {
        try
        {
            //Следующие проперти подходят для GlassFish сервера
            final Properties env = new Properties();
            //env.setProperty("java.naming.factory.initial", "com.sun.enterprise.naming.impl.SerialInitContextFactory");
            env.setProperty("java.naming.factory.url.pkgs", "com.sun.enterprise.naming");
            env.setProperty("java.naming.factory.state", "com.sun.corba.ee.impl.presentation.rmi.JNDIStateFactoryImpl");
            env.setProperty("org.omg.CORBA.ORBInitialHost", "localhost");
            env.setProperty("org.omg.CORBA.ORBInitialPort", "8080");
            InitialContext initContext = new InitialContext(env);
    
            //Попытаемся найти объект
            Object destination = initContext.lookup("java.naming.factory.state");
            
            Context namingContext = new InitialContext();
            
            int cddfd = 0;
        } catch(NamingException e)
        {
            e.printStackTrace();
        }
    }
    
}

