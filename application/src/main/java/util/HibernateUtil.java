package util;
import entities.*;
import org.hibernate.cfg.Configuration;
import org.hibernate.SessionFactory;

public class HibernateUtil {

    private static SessionFactory sessionFactory = null;
    static {
        try {
            sessionFactory = new Configuration()
                    .configure()
                    .addAnnotatedClass(Users.class)
                    .addAnnotatedClass(Stations.class)
                    .addAnnotatedClass(Tickets.class)
                    .addAnnotatedClass(Subroutes.class)
                    .addAnnotatedClass(Routes.class)
                    .addAnnotatedClass(Company.class)
                    .addAnnotatedClass(StationsOfRoute.class)
                    .buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}