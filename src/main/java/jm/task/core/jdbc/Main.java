package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        UserService dao = new UserServiceImpl();
        dao.dropUsersTable();

        dao.createUsersTable();

        dao.saveUser("Oleg", "Ivanov", (byte) 50);
        System.out.println("User с именем – Oleg добавлен в базу данных ");

        dao.saveUser("Dima", "West", (byte) 60);
        System.out.println("User с именем – Dima добавлен в базу данных ");

        dao.saveUser("Natasha", "Dumkin", (byte) 70);
        System.out.println("User с именем – Natasha добавлен в базу данных ");

        dao.saveUser("Maxim", "Last", (byte) 80);
        System.out.println("User с именем – Maxim добавлен в базу данных ");

        System.out.println(dao.getAllUsers());

        dao.cleanUsersTable();

        dao.dropUsersTable();
    }
}
