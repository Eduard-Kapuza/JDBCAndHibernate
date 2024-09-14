package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static jm.task.core.jdbc.common.Constant.*;

public class UserDaoHibernateImpl implements UserDao {
    private Optional<Transaction> transaction = Optional.empty();
    public UserDaoHibernateImpl() {
    }

    public void createUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = Optional.ofNullable(session.beginTransaction());
            session.createNativeQuery(CREATE_TABLE_USER_QUERY, User.class).executeUpdate();
            transaction.ifPresent(Transaction::commit);
        } catch (Exception e) {
            transaction.ifPresent(Transaction::rollback);
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = Optional.ofNullable(session.beginTransaction());
            session.createNativeQuery(DROP_USERS_TABLE_QUERY, User.class).executeUpdate();
            transaction.ifPresent(Transaction::commit);
        } catch (Exception e) {
            transaction.ifPresent(Transaction::rollback);
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        User buildUser = User.builder()
                .name(name)
                .lastName(lastName)
                .age(age)
                .build();

        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = Optional.ofNullable(session.beginTransaction());
            session.persist(buildUser);
            transaction.ifPresent(Transaction::commit);
            System.out.printf("User с именем Ц %s добавлен в базу данных\n", name);
        } catch (Exception e) {
            transaction.ifPresent(Transaction::rollback);
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = Optional.ofNullable(session.beginTransaction());
            User user = session.find(User.class, id);
            if (Objects.nonNull(user)) {
                session.remove(user);
                transaction.ifPresent(Transaction::commit);
            }
        } catch (Exception e) {
            transaction.ifPresent(Transaction::rollback);
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();

        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = Optional.ofNullable(session.beginTransaction());
            users = session.createNativeQuery(SELECT_USERS_FROM_TABLE_QUERY, User.class).stream().toList();
            transaction.ifPresent(Transaction::commit);
        } catch (Exception e) {
            transaction.ifPresent(Transaction::rollback);
            e.printStackTrace();
        }
        return users;
    }

    public void cleanUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = Optional.ofNullable(session.beginTransaction());
            session.createNativeQuery(CLEAN_USERS_TABLE_QUERY, User.class).executeUpdate();
            transaction.ifPresent(Transaction::commit);
        } catch (Exception e) {
            transaction.ifPresent(Transaction::rollback);
            e.printStackTrace();
        }
    }

    @Override
    public void closeConnection() {
    }
}