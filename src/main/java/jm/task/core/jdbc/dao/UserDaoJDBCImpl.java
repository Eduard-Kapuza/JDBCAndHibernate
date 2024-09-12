package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import static jm.task.core.jdbc.common.Constant.*;

public class UserDaoJDBCImpl implements UserDao {

    private final Connection connection = Util.getConnection();

    public void createUsersTable() {
        try (PreparedStatement preparedStatement = connection.prepareStatement(CREATE_TABLE_USER_QUERY)) {
            preparedStatement.execute();
        } catch (SQLException e) {
            System.out.println("Error create table");
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        try (PreparedStatement preparedStatement = connection.prepareStatement(DROP_USERS_TABLE_QUERY)) {
            preparedStatement.execute();
        } catch (SQLException e) {
            System.out.println("Error drop table");
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SAVE_USER_QUERY)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setInt(3, age);
            preparedStatement.execute();
            System.out.printf("User с именем Ц %s добавлен в базу данных\n", name);
        } catch (SQLException e) {
            System.out.println("Error save User");
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(REMOVE_USER_BY_ID_QUERY)) {
            preparedStatement.setInt(1, (int) id);
            preparedStatement.execute();
        } catch (SQLException e) {
            System.out.println("Error removeUserById");
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_USERS_QUERY)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong(1));
                user.setName(resultSet.getString(2));
                user.setLastName(resultSet.getString(3));
                user.setAge(resultSet.getByte(4));
                list.add(user);
            }
        } catch (SQLException e) {
            System.out.println("error when reading all users ");
            e.printStackTrace();
        }
        return list;
    }

    public void cleanUsersTable() {
        try (PreparedStatement preparedStatement = connection.prepareStatement(CLEAN_USERS_TABLE_QUERY)) {
            preparedStatement.execute();
        } catch (SQLException e) {
            System.out.println("Error truncate table");
            e.printStackTrace();
        }
    }

    public static void closeConnection() {
        try {
            Util.getConnection().close();
            System.out.println("JDBC: connection to the database is closed");
        } catch (SQLException e) {
            System.out.println("JDBC: error when closing connection ");
        }
    }
}