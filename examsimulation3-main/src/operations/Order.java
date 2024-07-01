package operations;

import java.util.Map;

public class Order {
    private int id;
    private String client;
    private Map<String, Integer> orderMap;
    private int totNoDisc;
    private int discount;

	public Order(int id, String client, Map<String, Integer> orderMap, int discount, int totNoDisc) {
		this.id = id;
		this.client = client;
		this.orderMap = orderMap;
        this.discount = discount;
        this.totNoDisc = totNoDisc;
	}
    
    public int getDiscount() {
        return discount;
    }

    public int getId() {
		return id;
	}
	public String getClient() {
		return client;
	}
	public Map<String, Integer> getOrderMap() {
		return orderMap;
	}
    public int getTotNoDisc() {
        return totNoDisc;
    }

    public int getTotAfterDisc() {
        return totNoDisc-discount;
    }
}
