package jms;

import com.sun.messaging.ConnectionConfiguration;
import com.sun.messaging.ConnectionFactory;

import javax.jms.*;

/**
 * @author SBT-Shumeyko-AM
 * @ created 06.03.2017
 * @ $Author$
 * @ $Revision$
 */
public class DirectMessageReceiver
    implements MessageListener
{
    private ConnectionFactory factory = new com.sun.messaging.ConnectionFactory();
    private JMSConsumer consumer;
    public void run()
    {
        new Thread(new Runnable(){
            @Override
            public void run()
            {
                try
                {
                    listen();
                } catch(InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    
    private void listen() throws InterruptedException
    {
        try
        {
            factory.setProperty(ConnectionConfiguration.imqAddressList, "mq://127.0.0.1:7677,mq://127.0.0.1:7677");
            try(JMSContext context = factory.createContext("admin","admin"))
            {
                Destination ordersQueue = context.createQueue("TestQueue");
                consumer = context.createConsumer(ordersQueue);
                consumer.setMessageListener(this);
                System.out.println( "Listening to the TestQueue...");
    
                Thread.sleep(1000000);
            }
        } catch(JMSException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void onMessage(Message message)
    {
        try
        {
            System.out.println( "Got the text message: " + message.getBody(String.class) );
            Thread.sleep(3400);
        }
        catch (JMSException e)
        {
            System.err.println( "JMSException: " + e.toString() );
        }
        catch(InterruptedException e)
        {
            e.printStackTrace();
        }
    }
}
