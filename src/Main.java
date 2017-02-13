import entities.TableNameEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.persistence.Query;
import javax.persistence.metamodel.Metamodel;
import java.util.List;
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
      TableNameEntity townEntity = new TableNameEntity(902);
      //  и сохраним ее в БД
      session.beginTransaction();
      session.save(townEntity);
      session.getTransaction().commit();
      
      session.beginTransaction();
      //Создаем запрос
      Query query = session.createQuery("FROM entities.TableNameEntity WHERE id = :id");
      query.setParameter("id", 78); //Задаем переменные запроса
      List<TableNameEntity> list1 = query.getResultList(); //Получить список результирующих сущностей
      //Получить одну сущность, запрос должен возвращать немножесвенный результат
      TableNameEntity tableNameEntity = (TableNameEntity)query.getSingleResult();
      
      //А тут вернем много сущностей
      query = session.createQuery("FROM entities.TableNameEntity WHERE id != :id");
      query.setParameter("id", 78);
      List<TableNameEntity> list2 = query.getResultList();
      
      session.getTransaction().commit();

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