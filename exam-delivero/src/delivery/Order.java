package delivery;

import java.util.Map;

public class Order {
    private String customerName;
    private String restName;
    private int deliveryTime;
    private int deliveryDistance;
    private Map<String, Integer> order;
    private int id;
    private boolean assigned;
    
    public Order(String customerName, String restName, int deliveryTime, int deliveryDistance, Map<String, Integer> order, int id) {
        this.customerName = customerName;
        this.restName = restName;
        this.deliveryTime = deliveryTime;
        this.deliveryDistance = deliveryDistance;
        this.order = order;
        this.assigned = false;
        this.id = id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public int getDeliveryTime() {
        return deliveryTime;
    }

    public int getDeliveryDistance() {
        return deliveryDistance;
    }

    public Map<String, Integer> getOrder() {
        return order;
    }

    public void setAssigned() {
        this.assigned = true;
    }
    public boolean isAssigned() {
        return assigned;
    }
    public boolean isNotAssigned() {
        return !assigned;
    }

    public int getId() {
        return id;
    }
    public String getRestName() {
        return restName;
    }
    
    
}
