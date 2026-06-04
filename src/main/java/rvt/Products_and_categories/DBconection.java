package rvt.Products_and_categories;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBconection {
    private static final String URL = "jdbc:sqlite:sistema.db";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    public static void initializeDatabase() {
        String createCategoriesTable = "CREATE TABLE IF NOT EXISTS categories (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT NOT NULL" +
                ");";

        String createProductsTable = "CREATE TABLE IF NOT EXISTS products (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT NOT NULL, " +
                "price REAL NOT NULL, " +
                "category_id INTEGER, " +
                "FOREIGN KEY (category_id) REFERENCES categories(id)" +
                ");";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            
            // Iespējo Foreign Key atbalstu SQLite datubāzē
            stmt.execute("PRAGMA foreign_keys = ON;");
            
            // Izveido tabulas
            stmt.execute(createCategoriesTable);
            stmt.execute(createProductsTable);
            
        } catch (SQLException e) {
            System.out.println("Kluda datubazes inicializacija: " + e.getMessage());
        }
    }
}