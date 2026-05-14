package rvt.OnlineShop;

public class Warehouse {
    public void addProduct(String product, int price, int stock) {
    };
    public int price(String product) {
        return 0;
    };

    static Warehouse warehouse = new Warehouse();
    public static void main(String[] args) {
        warehouse.addProduct("milk", 3, 10);
        warehouse.addProduct("coffee", 5, 7);

        System.out.println("prices:");
        System.out.println("milk: " + warehouse.price("milk"));
        System.out.println("coffee: " + warehouse.price("coffee"));
        System.out.println("sugar: " + warehouse.price("sugar"));
    }
}
