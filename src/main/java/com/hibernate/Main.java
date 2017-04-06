package com.hibernate;

import com.hibernate.entities.Students;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.util.List;
import javax.persistence.Query;

public class Main
{
    public static void main(String[] args) {

        try
        {

            ServiceRegistry serviceRegistry;
            Configuration configuration = new Configuration();
            configuration.configure();
            SessionFactory sessionFactory = configuration.buildSessionFactory();
            Session session = sessionFactory.openSession();

            Students students = new Students();
            students.setName("Имя4");
            students.setId(8);

            Transaction transaction = session.beginTransaction();
            session.persist(students);
            transaction.commit();

            Query query = session.createNamedQuery("query.q1");
            List list = query.getResultList();

            session.close();
            sessionFactory.close();
        }
        catch (Throwable e)
        {
            int f = 0;
        }
    }
}
