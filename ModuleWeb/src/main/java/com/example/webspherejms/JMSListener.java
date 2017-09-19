package com.example.webspherejms;

import javax.ejb.EJBException;
import javax.ejb.MessageDrivenBean;
import javax.ejb.MessageDrivenContext;
import javax.jms.Message;
import javax.jms.MessageListener;

/**
 * @author sbt-shumeyko-am
 * @ created 19.09.2017
 * @ $Author$
 * @ $Revision$
 */
public class JMSListener implements MessageDrivenBean, MessageListener
{

	@Override public void onMessage(Message message)
	{
		System.out.print("Сработало1");
	}

	@Override public void setMessageDrivenContext(MessageDrivenContext ctx) throws EJBException
	{
		System.out.print("Сработало2");
	}

	@Override public void ejbRemove() throws EJBException
	{
		System.out.print("Сработало3");
	}
}
