package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        UserService service = new UserServiceImpl();

        service.dropUsersTable();
        service.createUsersTable();
        service.saveUser("Ed", "Kap", (byte) 26);
        service.saveUser("Z", "Z", (byte) 5);
        service.saveUser("A", "A", (byte) 20);
        service.saveUser("B", "B", (byte) 40);
        System.out.println(service.getAllUsers());
        service.removeUserById(2);
        System.out.println(service.getAllUsers());
        service.cleanUsersTable();
        System.out.println(service.getAllUsers());
        service.closeConnection();
    }
}
