package rvt;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class App {
    public static void main(String[] args) {
        try(
            Connection connection = DriverManager.getConnection(
                "jdbc:sqlite:todo.db"
            );
            Statement statement = connection.createStatement(); 
        ) {
            String sql = "CREATE TABLE todo"
            + "(id INTEGER PRIMARY KEY, task  TEXT NOT NULL) STRICT";
            statement.executeUpdate(sql);
        
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
