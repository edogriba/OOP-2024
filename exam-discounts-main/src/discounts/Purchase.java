package discounts;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


public class Purchase {
    
    private int id;
    private Optional<Integer> c;
    private Map<Product, Integer> products;
    private double amount;
    private double discount;

    public Purchase(Optional<Integer> c, int id, Map<Product, Integer> products, double amount, double disc) {
        this.c = c;
        this.id = id; 
        this.products = products;
        this.amount = amount;
        this.discount = disc;
    }

    public double getAmount() {
        return amount;
    }

    public int getNumUnits() {
        return products.values().stream().collect(Collectors.summingInt(a->a));
    }

    public double getDiscount() {
        return discount;
    }

    public boolean hasCard() {
        return c.isPresent();
    }

    public Integer getCard() {
        return c.get();
    }
    public Map<Product, Integer> getProducts() {
        return products;
    }

}
