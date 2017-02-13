import entities.TableNameEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.persistence.metamodel.Metamodel;
import java.util.Set;

/**
 * @author SBT-Shumeyko-AM
 * @ created 13.02.2017
 * @ $Author$
 * @ $Revision$
 */
public class Main
{
  
  public static void main(final String[] args) throws Exception
  {
    //Создадим сессию
    SessionFactory sessions = new Configuration().configure().buildSessionFactory();
    Session session = sessions.openSession();
    
    try
    {
      //Можно получить
      Metamodel metamodel = session.getMetamodel();
      Set enSets = metamodel.getEntities();
  
      //Создадим POJO сущность
      TableNameEntity townEntity = new TableNameEntity(68784);
      //  и сохраним ее в БД
      session.beginTransaction();
      session.save(townEntity);
      session.getTransaction().commit();
      session.close();

    }
    catch(Throwable th)
    {
      int fff = 0;
    }
    finally
    {
      session.close();
    }
  }
}