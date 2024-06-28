package discounts;

public class Card {
    private String holder;
    private int id;
    private double totPurchased;

    public Card(String holder, int id) {
        this.holder = holder;
        this.id = id;

    }
    
    public String getHolder() {
        return holder;
    }

    public int getId() {
        return id;
    }

    public void addPurchase(double purchase) {
        totPurchased += purchase;
    }

    public double getTotPurchased() {
        return totPurchased;
    }

}
