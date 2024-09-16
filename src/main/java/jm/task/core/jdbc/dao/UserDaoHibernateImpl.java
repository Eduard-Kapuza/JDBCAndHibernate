package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;

import java.util.List;
import java.util.Objects;

import static jm.task.core.jdbc.common.Constant.*;

public class UserDaoHibernateImpl implements UserDao {

    public UserDaoHibernateImpl() {
    }

    private void executeTransaction(String query) {
        try (Session session = Util.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.createNativeQuery(query, User.class).executeUpdate();
            session.getTransaction().commit();
        }
    }

    public void cleanUsersTable() {
        executeTransaction(CLEAN_USERS_TABLE_QUERY);
    }

    public void createUsersTable() {
        executeTransaction(CREATE_TABLE_USER_QUERY);
    }

    public void dropUsersTable() {
        executeTransaction(DROP_USERS_TABLE_QUERY);
    }

    public void saveUser(String name, String lastName, byte age) {
        User buildUser = User.builder()
                .name(name)
                .lastName(lastName)
                .age(age)
                .build();

        try (Session session = Util.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.persist(buildUser);
            session.getTransaction().commit();
            System.out.printf("User с именем Ц %s добавлен в базу данных\n", name);
        }
    }

    public void removeUserById(long id) {
        try (Session session = Util.getSessionFactory().openSession()) {
            session.beginTransaction();
            User user = session.find(User.class, id);
            if (Objects.nonNull(user)) {
                session.remove(user);
                session.getTransaction().commit();
            }
        }
    }

    public List<User> getAllUsers() {
        List<User> users;

        try (Session session = Util.getSessionFactory().openSession()) {
            session.beginTransaction();
            users = session.createNativeQuery(SELECT_USERS_FROM_TABLE_QUERY, User.class).stream().toList();
            session.getTransaction().commit();
        }
        return users;
    }
}