package org.example;

import org.example.Util.TestSession;
import org.example.domen.User;
import org.hibernate.Session;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(MyRunner.class)
public class SqlTest {

    @TestSession
    private static Session session;

    @Test
    public void selectAllList(){
        System.out.println(session);
        System.out.println(session.getTransaction());
        List<User> list = session.createNativeQuery("select * from users", User.class)
                .list();
        assertEquals(3, list.size());
    }

    @Test
    public void selectById(){

        System.out.println(session);
        System.out.println(session.getTransaction());
        List<User> users = session.createNativeQuery("select * from users where id=:id", User.class)
                .setParameter("id", 2)
                .list();
        assertNotNull(users);
        assertEquals(1, users.size());
    }

    @Test
    public void selectByName(){
        System.out.println(session);
        System.out.println(session.getTransaction());
        String name = "Sasha";

        List<User> users = session.createNativeQuery("select * from users where users.name = :name",User.class)
                .setParameter("name", name)
                .list();

        User user = users.get(0);

        assertEquals(1, users.size());
        assertEquals(name, user.getName());
    }
}
