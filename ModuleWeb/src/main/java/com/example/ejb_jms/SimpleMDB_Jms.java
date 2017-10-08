package com.example.ejb_jms;

import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

/**
 * @author Kot
 * @ created 05.10.2017
 * @ $Author$
 * @ $Revision$
 */
@MessageDriven(mappedName = "com.example.jms.MyQueue")
public class SimpleMDB_Jms implements MessageListener
{
	@Override public void onMessage(Message message)
	{
		try
		{
			int count = Integer.parseInt(message.getStringProperty("count"));
			System.out.println(count);
		}
		catch (JMSException e)
		{
			e.printStackTrace();
		}
	}
}
