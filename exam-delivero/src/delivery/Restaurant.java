package delivery;

public class Restaurant {
    private String name;
    private String category;

    public Restaurant(String name, String category) {
        this.name = name;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }
     
}