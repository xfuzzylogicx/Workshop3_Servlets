package pl.coderslab.Utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/CodingSchool?useSSL=false&characterEncoding=utf8",
                                           "root", "coderslab");
    }
}
