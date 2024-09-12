package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Objects;
import static jm.task.core.jdbc.common.Constant.*;

public class Util {

    private static Connection connection;

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
}