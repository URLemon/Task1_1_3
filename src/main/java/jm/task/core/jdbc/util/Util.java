package jm.task.core.jdbc.util;

import org.hibernate.HibernateException;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    private static final String URL = "jdbc:mysql://localhost:3306/dbusers?serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    private static SessionFactory sessionFactory;

    static {
        try {
            Configuration conf = new Configuration()
                    .setProperty("hibernate.connection.url", URL)
                    .setProperty("hibernate.connection.username", USER)
                    .setProperty("hibernate.connection.password", PASSWORD)
                    .setProperty("hibernate.connection.autocommit", "false")
                    .setProperty("hibernate.current_session_context_class", "thread")
                    .addAnnotatedClass(jm.task.core.jdbc.model.User.class);
            //ServiceRegistry registry = new StandardServiceRegistryBuilder().applySettings(conf.getProperties()).build();
            sessionFactory = conf.buildSessionFactory();
        }
        catch (HibernateException e){
            e.printStackTrace();
        }
    }

    public static SessionFactory getSessionFactory(){
        return sessionFactory;
    }

    public static void closeSessionFactory(){
        sessionFactory.close();
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }


}
