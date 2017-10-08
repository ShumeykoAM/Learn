package com.example.ejb_jms;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.*;
import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * @StateFul - означает что объект ejb_jms существует в контексте пользователя, привязан к сессии,
 *   класс такого ejb_jms может иметь поля, в которых можно хранить информацию между запросами
 * @author Kot
 * @ created 06.10.2017
 * @ $Author$
 * @ $Revision$
 */
@Remote//Уровень доступа RMI (Remote Method Invocation), есть еще @Local, надо разбираться.
@Stateful
public class SimpleStateful_Jms
{
	//Контекстно зависимое поле
	private int count;

	public int getCount()
	{
		return count;
	}

	/**
	 * Если вызвать метод аннотированный @Remove, сессия закончится, но этот же объект может потом быть использован
	 *      другим пользователем, в другой сессии, по этому !!! ОЧИСТКУ объекта нужно сделать самому
	 * В дальнейшем, при обращении к этому компоненту, будет выброшено исключение.
	 * !!! Как создать новую сессию, я так и не понял.
	 */
	@Remove
	public void endSession()
	{
		count = 0;
	}

	//Какие ни будь методы, которые могут работать в контексте, обращаться к контекстным переменным (полям)
	public String sayHelloWorld()
	{
		count++;
		sendMessage();
		return "Hello StatefulEJB " + count + " count";
	}

	/**Какие ни будь методы, которые могут работать в контексте, обращаться к контекстным переменным (полям)
	 * Отправим сообщение в очередь
	 */
	public void sendMessage()
	{
		//https://www.journaldev.com/9906/jms1-1-with-eclipse-and-jboss6-example
		try
		{
			Context context = new InitialContext();
			//Когда запущен сервак glassfish нужно выполнить команды, что бы добавить фабрику и очередь на сервак, потом их можно лукапить и использовать
			//asadmin create-jms-resource --restype javax.jms.ConnectionFactory com.example.jms.ConnectionFactory
			ConnectionFactory factory = (ConnectionFactory) context.lookup("com.example.jms.ConnectionFactory");
			//asadmin create-jms-resource --restype javax.jms.Queue com.example.jms.MyQueue
			Destination queue = (Destination) context.lookup("com.example.jms.MyQueue");

			Connection connection = factory.createConnection();
			connection.start();
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			MessageProducer producer = session.createProducer(queue);
			Message message = session.createMessage();
			message.setStringProperty("count", new Integer(count).toString());
			producer.send(message);
		}
		catch (NamingException e)
		{
			e.printStackTrace();
		}
		catch (JMSException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Вызывается сразу после активации
	 */
	@PostConstruct
	private void postConstruct()
	{
		//Делаем инициализацию
		count = 0;
	}

	/**
	 * Вызывается перед уничтожением
	 */
	@PreDestroy
	private void preDestroy()
	{
		//Делаем очистку
	}

	/**
	 * Вызывается при активации
	 */
	@PostActivate
	private void ostActivate()
	{

	}

	/**
	 * Вызывается при деактивации
	 */
	@PrePassivate
	private void rePassivate()
	{

	}
}
