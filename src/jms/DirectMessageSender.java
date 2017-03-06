package jms;

import com.sun.messaging.ConnectionConfiguration;

import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.Queue;

/**
 * @author SBT-Shumeyko-AM
 * @ created 06.03.2017
 * @ $Author$
 * @ $Revision$
 */
public class DirectMessageSender
{
    public static void main(String[] args)
    {
        //Запустим реализацию получателя сообщений
        DirectMessageReceiver directMessageReceiver = new DirectMessageReceiver();
        directMessageReceiver.run();

        try
        {
            doSender();
            Thread.sleep(100000);
        }
        catch(JMSException e)
        {
            e.printStackTrace();
        }
        catch(InterruptedException e)
        {
            e.printStackTrace();
        }
    }
    
    private static void doSender() throws JMSException, InterruptedException
    {

        com.sun.messaging.ConnectionFactory factory = new com.sun.messaging.ConnectionFactory();
        factory.setProperty(ConnectionConfiguration.imqAddressList, "mq://127.0.0.1:7677,mq://127.0.0.1:7677");
        try( JMSContext context = factory.createContext("admin","admin") )
        {
            Queue ordersQueue = context.createQueue("TestQueue");
            JMSProducer producer = context.createProducer();
            // Send msg to buy 200 shares of IBM at market price
            for(Integer i=0; i<10; i++)
            {
                producer.send(ordersQueue, "IBM 200 Mkt " + i.toString());
                System.out.println("Push " + i.toString());
                Thread.sleep(2000);
            }
        }
    }
}

