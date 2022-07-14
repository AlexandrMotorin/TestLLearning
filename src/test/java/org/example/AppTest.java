package org.example;

import org.example.domen.User;
import org.example.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;


public class AppTest {

    public static SessionFactory sessionFactory = HibernateUtil.sessionFactory();
    public static Session session;

    @Test
    public void selectAllList(){
        try{
            session = sessionFactory.openSession();
            session.beginTransaction();

            System.out.println(session);
            System.out.println(session.getTransaction());


            List<User> list = session.createNativeQuery("select * from users", User.class)
                    .list();
            assertEquals(3, list.size());

            session.getTransaction().commit();
        }catch (Exception e){
            if(session != null) session.getTransaction().rollback();
        }finally {
            if(session != null) session.close();
        }

    }

    @Test
    public void selectById(){
        try{
            session = sessionFactory.openSession();
            session.beginTransaction();

            System.out.println(session);
            System.out.println(session.getTransaction());


            List<User> users = session.createNativeQuery("select * from users where id=:id", User.class)
                    .setParameter("id", 2)
                    .list();
            assertNotNull(users);
            assertEquals(1, users.size());

            session.getTransaction().commit();
        }catch (Exception e){
            if(session != null) session.getTransaction().rollback();
        }finally {
            if(session != null) session.close();
        }

    }

    @Test
    public void selectByName(){
        try{
            session = sessionFactory.openSession();
            session.beginTransaction();

            System.out.println(session);
            System.out.println(session.getTransaction());


            String name = "Sasha";

            List<User> users = session.createNativeQuery("select * from users where users.name = :name",User.class)
                    .setParameter("name", name)
                    .list();

            User user = users.get(0);

            session.getTransaction().commit();
        }catch (Exception e){
            if(session != null) session.getTransaction().rollback();
        }finally {
            if(session != null) session.close();
        }

    }
}
