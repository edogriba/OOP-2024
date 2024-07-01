package operations;

public class Product {
    private String type;
    private int n;
    private int price;

    public Product(String type, int n, int price) {
        this.type = type;
        this.n = n;
        this.price = price;
    }

	public String getType() {
		return type;
	}
	public int getN() {
		return n;
	}
    public void setN(int i) {
        n += i;
    }
	public int getPrice() {
		return price;
	}

    public int getTotPrice() {
        return n*price;
    }

}
