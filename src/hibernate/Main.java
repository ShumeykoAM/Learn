package hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.util.List;

/**
 * @author sbt-shumeyko-am
 * @ created 06.04.2017
 * @ $Author$
 * @ $Revision$
 */
public class Main
{
	public static void main(String args[])
	{
		try
		{
			Configuration configuration = new Configuration();
			configuration.configure();

			SessionFactory sf = configuration.buildSessionFactory();
			Session session = sf.openSession();
			f(session);
		}
		catch (Throwable e)
		{
			int f = 0;
		}
	}

	public static void f(Session session)
	{
		try
		{
			//Students student = new Students();
			//student.setId(436);
			//student.setName("Супер новый студент");


			//Создаем именованный запрос
			Query query = session.getNamedQuery("com.q1");
			query.setParameter("id", 0);
			query.setMaxResults(1);
			query.setFirstResult(2);

			List l = query.list();




			//session.beginTransaction();

			//session.persist(student);




			//session.getTransaction().commit();
			//long id = student.getId();
			int i = 0;
		}
		catch (Throwable e)
		{
			int f = 0;
		}
	}



}
