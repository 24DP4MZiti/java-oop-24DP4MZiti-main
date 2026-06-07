package rvt.Online_Shop;

import java.util.HashMap;
import java.util.Set;

public class Warehouse {
    
    HashMap<String, Integer> map = new HashMap<>();
    private String product;
    private int stock;
    private int price;
  
    public void addProduct(String product, int price, int stock){
        this.stock = stock;
        this.product = product;
        this.price = price;
        this.map.put(product, price);
    }
    public int price(String product){
        if(this.map.get(product) != null){
            return this.map.get(product).intValue();
        }
        return -99;
    }
    public int stock(String product){
        if(this.map.get(product) != null){
            return this.map.get(product).valueOf(this.stock);
        }
        return 0;
    }
    public boolean take(String product){
        if(this.map.get(product).valueOf(this.stock) > 0){
            stock -= 1;
            return true;
        }
        return false;
    }
    public Set<String> products(){
        return this.map.keySet();
    }
}
