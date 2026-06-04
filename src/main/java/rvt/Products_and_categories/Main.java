package rvt.Products_and_categories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        // Inicializējam datubāzi un tabulas pirms programmas startēšanas
        DBconection.initializeDatabase();

        while (true) {
            System.out.println("\n--- IZVELNE ---");
            System.out.println("1 - Pievienot kategoriju");
            System.out.println("2 - Pievienot produktu");
            System.out.println("3 - Paradit visas kategorijas");
            System.out.println("4 - Paradit visus produktus");
            System.out.println("5 - Meklet produktus pec kategorijas");
            System.out.println("0 - Iziet");
            System.out.print("Izvelies darbibu: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    addCategory();
                    break;
                case "2":
                    addProduct();
                    break;
                case "3":
                    showAllCategories();
                    break;
                case "4":
                    showAllProducts();
                    break;
                case "5":
                    searchProductsByCategory();
                    break;
                case "0":
                    System.out.println("Programma aptureta.");
                    return;
                default:
                    System.out.println("Nederiga izvele! Megini velreiz.");
            }
        }
    }

    // 1. OBLIGĀTĀ FUNKCIJA: Kategorijas pievienošana
    private static void addCategory() {
        System.out.print("Ievadi kategorijas nosaukumu: ");
        String name = scanner.nextLine();

        if (name.trim().isEmpty()) {
            System.out.println("Nosaukums nevar but tukss!");
            return;
        }

        String sql = "INSERT INTO categories(name) VALUES(?)";

        try (Connection conn = DBconection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, name);
            pstmt.executeUpdate();
            System.out.println("Kategorija '" + name + "' veiksmigi saglabata!");
            
        } catch (SQLException e) {
            System.out.println("Kluda saglabajot kategoriju: " + e.getMessage());
        }
    }

    // 2. OBLIGĀTĀ FUNKCIJA: Produkta pievienošana
    private static void addProduct() {
        System.out.print("Ievadi produkta nosaukumu: ");
        String name = scanner.nextLine();

        System.out.print("Ievadi produkta cenu: ");
        double price;
        try {
            price = Double.parseDouble(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Kluda: Cenai jabut skaitlim!");
            return;
        }

        // Parādām pieejamās kategorijas, lai lietotājs varētu izvēlēties ID
        System.out.println("\nPieejamas kategorijas:");
        showAllCategories();
        System.out.print("Ievadi kategorijas ID no saraksta: ");
        int categoryId;
        try {
            categoryId = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Kluda: ID jabut veselam skaitlim!");
            return;
        }

        String sql = "INSERT INTO products(name, price, category_id) VALUES(?, ?, ?)";

        try (Connection conn = DBconection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, name);
            pstmt.setDouble(2, price);
            pstmt.setInt(3, categoryId);
            
            pstmt.executeUpdate();
            System.out.println("Produkts '" + name + "' veiksmigi pievienots datubazei!");
            
        } catch (SQLException e) {
            System.out.println("Kluda pievienojot produktu (Parliecinies, vai kategorijas ID eksiste): " + e.getMessage());
        }
    }

    private static void showAllCategories() {
        String sql = "SELECT * FROM categories";

        try (Connection conn = DBconection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            boolean hasData = false;
            while (rs.next()) {
                hasData = true;
                Categories cat = new Categories(rs.getInt("id"), rs.getString("name"));
                System.out.println(cat);
            }
            if (!hasData) {
                System.out.println("Kategoriju saraksts ir tukss.");
            }
        } catch (SQLException e) {
            System.out.println("Kluda atlasot kategorijas: " + e.getMessage());
        }
    }

    private static void showAllProducts() {
        String sql = "SELECT p.id, p.name, p.price, c.name AS category_name " +
                     "FROM products p " +
                     "LEFT JOIN categories c ON p.category_id = c.id";

        try (Connection conn = DBconection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            boolean hasData = false;
            while (rs.next()) {
                hasData = true;
                String catName = rs.getString("category_name");
                if (catName == null) catName = "Nav kategorijas";
                
                Products prod = new Products(rs.getInt("id"), rs.getString("name"), rs.getDouble("price"), catName);
                System.out.println(prod);
            }
            if (!hasData) {
                System.out.println("Produktu saraksts ir tukss.");
            }
        } catch (SQLException e) {
            System.out.println("Kluda atlasot produktus: " + e.getMessage());
        }
    }

    // 4. OBLIGĀTĀ FUNKCIJA: Meklēšana pēc kategorijas ID vai nosaukuma
    private static void searchProductsByCategory() {
        System.out.print("Ievadi meklejamās kategorijas ID vai precizu nosaukumu: ");
        String input = scanner.nextLine();

        // SQL pieprasījums, kas atlasa produktus, filtrējot pēc c.id VAI c.name ar drošajiem parametriem `?`
        String sql = "SELECT p.id, p.name, p.price, c.name AS category_name " +
                     "FROM products p " +
                     "INNER JOIN categories c ON p.category_id = c.id " +
                     "WHERE c.id = ? OR c.name = ?";

        try (Connection conn = DBconection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            // Mēģinām pirmo parametru iestatīt kā ID (ja lietotājs ievadīja skaitli)
            int idParam = -1;
            try {
                idParam = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                // Ievade nav skaitlis, tātad tas ir tikai teksts (nosaukums)
            }

            pstmt.setInt(1, idParam); // Ja nav skaitlis, meklēs pēc -1 (kas neatradīs ID)
            pstmt.setString(2, input); // Meklēs pēc sakrītoša nosaukuma tekstā

            try (ResultSet rs = pstmt.executeQuery()) {
                boolean found = false;
                while (rs.next()) {
                    found = true;
                    Products prod = new Products(rs.getInt("id"), rs.getString("name"), rs.getDouble("price"), rs.getString("category_name"));
                    System.out.println(prod);
                }
                if (!found) {
                    System.out.println("Kategorijai '" + input + "' netika atrasts neviens produkts.");
                }
            }
        } catch (SQLException e) {
            System.out.println("Kluda meklesanas procesa: " + e.getMessage());
        }
    }
}