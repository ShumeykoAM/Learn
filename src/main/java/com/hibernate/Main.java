package com.hibernate;

import com.hibernate.entities.Student;
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
            //org.hibernate.id.enhanced.TableGenerator
            //org.hibernate.id.IdentityGenerator

            ServiceRegistry serviceRegistry;
            Configuration configuration = new Configuration();
            configuration.configure();
            SessionFactory sessionFactory = configuration.buildSessionFactory();
            Session session = sessionFactory.openSession();

            Transaction transaction = session.beginTransaction();

            Student st = new Student();
            st.setName("wewewewe");
            st.setId(1);
            
            session.persist(st);

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
