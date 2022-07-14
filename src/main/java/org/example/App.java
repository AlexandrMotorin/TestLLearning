package org.example;


import org.example.domen.User;
import org.example.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public class App {
    public static void main( String[] args ) {

//        User anna = User.builder()
//                .name("Anna")
//                .age(23)
//                .build();
//
//        User sergey = User.builder()
//                .name("Sergey")
//                .age(25)
//                .build();
//
//        saveUser(anna);
//        saveUser(sergey);


        SessionFactory sessionFactory = HibernateUtil.sessionFactory();
        Session session = sessionFactory.openSession();

        List<User> list = session.createNativeQuery("select * from users", User.class)
                .list();

        System.out.println(list);

    }

    private static void saveUser(User user) {
        SessionFactory sessionFactory = HibernateUtil.sessionFactory();
        try(Session session = sessionFactory.openSession()){
            session.beginTransaction();

            session.persist(user);

            session.getTransaction().commit();
        }
    }
}
