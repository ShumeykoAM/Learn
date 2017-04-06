package hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

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
			Students student = new Students();
			student.setId(435);
			student.setName("Супер новый студент");

			Configuration configuration = new Configuration();
			configuration.configure();

			SessionFactory sf = configuration.buildSessionFactory();
			Session session = sf.openSession();


			session.beginTransaction();

			session.persist(student);

			session.getTransaction().commit();
			long id = student.getId();
		}
		catch (Throwable e)
		{
			int f = 0;
		}
	}
}
