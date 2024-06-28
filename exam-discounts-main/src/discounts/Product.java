package discounts;

public class Product {

    private String categoryCode;
    private String productCode;
    private double price;
    private int purchasedUnits = 0;

    public Product(String categoryCode, String productCode, double price) {
        this.categoryCode = categoryCode;
        this.productCode = productCode;
        this.price = price;
    }

    public String getCategoryCode() {
        return categoryCode;
    }

    public String getProductCode() {
        return productCode;
    }

    public double getPrice() {
        return price;
    }

    public void addPurchasedunits( int num) {
        purchasedUnits += num;
    }

    public int getPurchasedUnits() {
        return purchasedUnits;
    }

    
}
