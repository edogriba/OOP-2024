import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class App {
    public static void main(String[] args) throws Exception {
        class Order {
            private String date;
            private int cost;

            public Order(String date, int cost) {
                this.date = date;
                this.cost = cost;
            }

            public String getDate() {
                return this.date;
            }
            public int getcost() {
                return this.cost;
            }
        }

        class Product {

            private String name;
            private List<Order> orderList;
            private int numOrders;
    
            public Product(String name) {
                this.name = name;
                orderList = new LinkedList<>();
                this.numOrders = 0;
            }

            public void addOrder(Order o) {
                orderList.add(o);
                numOrders++;
            }
    
            public String getName() {
                return this.name;
            }
            public int getNumOrders() {
                return numOrders;
            }

        }
    
    
        List<Product> inventoryList = new LinkedList<>();

        Order o1 = new Order("29/10/2020", 89);
        Order o2 = new Order("09/08/2021", 97);
        Order o3 = new Order("19/11/2023", 549);
        Order o4 = new Order("14/01/2024", 5);
        Product p1 = new Product("Calze");
        p1.addOrder(o1);
        p1.addOrder(o3);
        Product p2= new Product("Maglietta");
        p2.addOrder(o2);
        Product p3= new Product("Scarpe");
        //p3.addOrder(o4);
        inventoryList.add(p1);
        inventoryList.add(p2);
        inventoryList.add(p3);

        inventoryList.stream().sorted(Comparator.comparing(Product::getName)).map(Product::getName).forEach(System.out::println);
        inventoryList.stream().filter(x-> x.getNumOrders() > 0 ).sorted(Comparator.comparing(Product::getName)).map(Product::getName).forEach(System.out::println);
        long count = inventoryList.stream().filter(x-> x.getNumOrders() > 0 ).sorted(Comparator.comparing(Product::getName)).map(Product::getName).collect(Collectors.counting());
        System.out.println(count);
        List<String> lista = inventoryList.stream().filter(x-> x.getNumOrders() > 0 ).sorted(Comparator.comparing(Product::getNumOrders).reversed()).map(Product::getName).collect(Collectors.toList());
        System.out.println(lista.get(0));
    }
}
