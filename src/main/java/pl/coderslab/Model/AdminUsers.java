package pl.coderslab.Model;

import pl.coderslab.Utils.DBConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class AdminUsers {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String answer = "";
        while (!answer.equals("quit")) {
            System.out.println("Lista użytkowników:");
            try (Connection conn = DBConnection.getConnection()) {
                User[] users = User.loadAllUsers(conn);
                for (User item : users) {
                    System.out.println(item);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            System.out.println(
                    "\nWybierz jedną z opcji:\n - add - dodanie użytkownika \n - edit - edycja użytkownika \n - delete - usuń użytkownika\n - quit - wyjdz z programu");
            answer = scanner.nextLine();
            //ADD
            if (answer.equals("add")) {
                System.out.println("Podaj kolejno username, email, haslo");
                String username = scanner.nextLine();
                String email = scanner.nextLine();
                String password = scanner.nextLine();
                System.out.println("Wybierz id grupy do której ma zostać przypisany użytkownik");
                try (Connection conn = DBConnection.getConnection()) {
                    UserGroup[] userGroups = UserGroup.loadAllUserGroups(conn);
                    for (UserGroup item : userGroups) {
                        System.out.println(item);
                    }
                    int groupId = scanner.nextInt();
                    scanner.nextLine();
                    UserGroup userGroup = UserGroup.loadUserGroupById(conn, groupId);
                    User user = new User(username, email, password, userGroup);
                    user.saveToDB(conn);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                //EDIT
            } else if (answer.equals("edit")) {
                System.out.println("Użytkownik o jakim id ma byc edytowany?");
                int userId = scanner.nextInt();
                scanner.nextLine();
                System.out.println("Podaj kolejno username, email, haslo");
                String username = scanner.nextLine();
                String email = scanner.nextLine();
                String password = scanner.nextLine();
                System.out.println("Wybierz id grupy do której ma zostać przypisany użytkownik");
                try (Connection conn = DBConnection.getConnection()) {
                    UserGroup[] userGroups = UserGroup.loadAllUserGroups(conn);
                    for (UserGroup item : userGroups) {
                        System.out.println(item);
                    }
                    int groupId = scanner.nextInt();
                    scanner.nextLine();
                    User user = User.loadUserById(conn, userId);
                    user.setUsername(username);
                    user.setEmail(email);
                    user.setPassword(password);
                    user.setUserGroup(UserGroup.loadUserGroupById(conn, groupId));
                    user.saveToDB(conn);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                //DELETE
            } else if (answer.equals("delete")) {
                System.out.println("Użytkownik o jakim id ma byc usuniety?");
                int userId = scanner.nextInt();
                scanner.nextLine();
                try (Connection conn = DBConnection.getConnection()) {
                    User user = User.loadUserById(conn, userId);
                    user.delete(conn);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
