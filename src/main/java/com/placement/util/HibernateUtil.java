package com.placement.util;

import com.placement.model.User;
import com.placement.model.Candidate;
import com.placement.model.Recruiter;
import com.placement.model.Offer;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();
                configuration.configure("hibernate.cfg.xml");

                configuration.addAnnotatedClass(User.class);
                configuration.addAnnotatedClass(Candidate.class);
                configuration.addAnnotatedClass(Recruiter.class);
                configuration.addAnnotatedClass(Offer.class);


                StandardServiceRegistryBuilder registryBuilder =
                        new StandardServiceRegistryBuilder()
                                .applySettings(configuration.getProperties());

                sessionFactory = configuration.buildSessionFactory(registryBuilder.build());
            } catch (Throwable ex) {
                System.err.println("SessionFactory creation failed: " + ex);
                ex.printStackTrace();
                throw new ExceptionInInitializerError(ex);
            }
        }
        return sessionFactory;
    }

    public static void shutdown() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }
}