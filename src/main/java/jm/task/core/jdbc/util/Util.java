package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.cfg.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Objects;

import static jm.task.core.jdbc.common.Constant.*;

public class Util {

    private static Connection connection;

    private static SessionFactory sessionFactory;

    public static Connection getConnection() {
        try {
            if (Objects.isNull(connection)) {
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
            }
        } catch (SQLException e) {
            System.out.println("Error get connection");
            e.printStackTrace();
            return connection;
        }
        return connection;
    }

    public static SessionFactory getSessionFactory() {
        if (Objects.isNull(sessionFactory)) {
            sessionFactory = new Configuration()
                    .setProperty(AvailableSettings.JAKARTA_JDBC_URL, "jdbc:postgresql://localhost:5432/postgres")
                    .setProperty(AvailableSettings.JAKARTA_JDBC_DRIVER, "org.postgresql.Driver")
                    .setProperty(AvailableSettings.JAKARTA_JDBC_USER, "postgres")
                    .setProperty(AvailableSettings.JAKARTA_JDBC_PASSWORD, "postgres")
                    .setProperty(AvailableSettings.DIALECT, "org.hibernate.dialect.PostgresPlusDialect")
                    .setProperty(AvailableSettings.SHOW_SQL, "true")
                    .setProperty(AvailableSettings.FORMAT_SQL, "true")
                    .addAnnotatedClass(User.class)
                    .buildSessionFactory();
        }
        return sessionFactory;
    }
}