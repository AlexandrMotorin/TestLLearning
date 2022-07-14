package org.example.util;

import org.hibernate.SessionFactory;
import org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    public static Configuration getConfiguration(){
        Configuration configuration = new Configuration();
        configuration.setPhysicalNamingStrategy(new CamelCaseToUnderscoresNamingStrategy());

        return configuration;
    }

    public static SessionFactory sessionFactory(){
        Configuration configuration = getConfiguration();

        configuration.configure();
        return configuration.buildSessionFactory();
    }
}
