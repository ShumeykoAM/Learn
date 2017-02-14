import entities.Town;
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
      //Можно получить множество возможных сущностей
      Metamodel metamodel = session.getMetamodel();
      Set enSets = metamodel.getEntities();
  
      //Создадим POJO сущность и сохраним ее
      Town town = new Town(67, "COD", "Брянск", "2017-02-24");
      town.setId(865); //Автоинкрементный не удается сделать, наверное потому что СУБД SQLite
      session.beginTransaction(); //Все модификации должны происходить в транзакции
      session.save(town);
      town = new Town(67, "COD", "Брянск", "2017-02-24");
      town.setId(34);
      session.save(town);
      session.getTransaction().commit();
      int id = town.getId(); //Если бы отработал автоинкремент, то id можно получить так
      
      //Создаем запрос выборки (работает без транзакции)
      Query query = session.createQuery("FROM entities.Town WHERE name = :name");
      query.setParameter("name", "Брянск"); //Задаем переменные запроса
      query.setMaxResults(1);
      List<Town> list1 = query.getResultList(); //Получить список результирующих сущностей
      //Получить одну сущность, запрос НЕ должен возвращать множесвенный результат, иначе exception
      town = (Town)query.getSingleResult();
      
      //А тут вернем много сущностей
      query = session.createQuery("FROM entities.Town WHERE name = :name");
      query.setParameter("name", "Брянск"); //Задаем переменные запроса
      List<Town> list2 = query.getResultList();

      session.beginTransaction(); //Все модификации должны происходить в транзакции
      //Изменим запись
      town.setName("Москва");
      session.update(town);
      session.getTransaction().commit();
  
      session.beginTransaction(); //Все модификации должны происходить в транзакции
      //A тут удалим сущность
      session.delete(town);
      //  или через запрос, что бы не вынимать сущности
      query = session.createQuery("delete from entities.Town WHERE id = :id");
      query.setParameter("id", 865);
      int count = query.executeUpdate();
      session.getTransaction().commit();
      
      int i = 0;
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