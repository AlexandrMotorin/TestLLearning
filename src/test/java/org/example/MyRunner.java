package org.example;

import org.example.Util.TestSession;
import org.example.Util.TestSqlException;
import org.example.util.HibernateUtil;
import org.hibernate.JDBCException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.Statement;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public class MyRunner extends BlockJUnit4ClassRunner {

    private Class<?> klass;
    private SessionFactory sessionFactory;
    private Session session;

    public MyRunner(Class<?> klass) throws InitializationError, IllegalAccessException {
        super(klass);
        this.klass = klass;

        sessionFactory = HibernateUtil.sessionFactory();
        injectSession();
    }

    private void injectSession() throws IllegalAccessException {
        Field[] fields = klass.getDeclaredFields();

        for(Field field : fields){
            field.setAccessible(true);
            Annotation[] annotations = field.getAnnotations();
            for(Annotation annotation : annotations){
                if(annotation.annotationType() == TestSession.class){
                    field.set(null, sessionFactory.openSession());
                }
            }
        }
    }

    @Override
    protected Statement methodInvoker(FrameworkMethod method, Object test) {
        Statement base = super.methodInvoker(method, test);
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                Session session = sessionFactory.openSession();
                try {
                    session.beginTransaction();
                    base.evaluate();
                    session.getTransaction().commit();
                } catch (JDBCException e) {
                    session.getTransaction().rollback();
                    throw new JDBCException(e.getMessage(), e.getSQLException());
                } catch (Exception e){
                    throw new TestSqlException(e.getMessage());
                } finally {
                    session.close();
                    if(!session.isOpen()) System.out.println("!!!session closed!!!");
                }
            }
        };
    }

}
