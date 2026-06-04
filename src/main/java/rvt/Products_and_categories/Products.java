package rvt.Products_and_categories;

public class Products {
    private int id;
    private String name;
    private double price;
    private String categoryName; // Izmantosim, lai attēlotu saistīto kategoriju

    public Products(int id, String name, double price, String categoryName) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.categoryName = categoryName;
    }

    @Override
    public String toString() {
        return "ID: " + id + " | Nosaukums: " + name + " | Cena: " + price + " EUR | Kategorija: " + categoryName;
    }
}