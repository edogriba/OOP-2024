package delivery;

public class Dish {
    private float price;
    private String name;
    private String restName;

    public Dish(float price, String name, String restName) {
        this.price = price;
        this.name = name;
        this.restName = restName;
    }

    public float getPrice() {
        return price;
    }
    public String getName() {
        return name;
    }
    public String getRest() {
        return restName;
    }
    
}
