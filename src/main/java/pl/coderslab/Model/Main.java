package pl.coderslab.Model;

import pl.coderslab.Utils.DBConnection;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args)
    {
        try (Connection conn = DBConnection.getConnection())
        {
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
