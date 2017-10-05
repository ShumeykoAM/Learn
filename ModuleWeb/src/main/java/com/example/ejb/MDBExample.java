package com.example.ejb;

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
public class MDBExample implements MessageListener
{
	@Override public void onMessage(Message message)
	{
		try
		{
			String value = message.getStringProperty("param1");
			System.out.println(value);
		}
		catch (JMSException e)
		{
			e.printStackTrace();
		}
	}
}
