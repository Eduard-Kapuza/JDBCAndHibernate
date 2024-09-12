package jm.task.core.jdbc.common;

public class Constant {

    // connection
    public static final String URL = "jdbc:postgresql://localhost:5432/postgres";


    public static final String DRIVER = "org.postgresql.Driver";

    public static final String PASSWORD = "postgres";

    public static final String USER = "postgres";

    // query
    public static final String CREATE_TABLE_USER_QUERY = """
                create table IF NOT EXISTS users (
                id        bigserial constraint users_pk primary key,
                name      varchar(200),
                last_name varchar(200),
                age       integer);
                """;

    public static final String DROP_USERS_TABLE_QUERY = "drop table IF EXISTS users;";

    public static final String SAVE_USER_QUERY = "insert into users(name, last_name, age) values (?,?,?);";

    public static final String REMOVE_USER_BY_ID_QUERY = "delete from users where id = ?;";

    public static final String GET_ALL_USERS_QUERY = "select * from users;";

    public static final String CLEN_USERS_TABLE_QUERY = "truncate table users;";
}
