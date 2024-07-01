package operations;


import java.util.SortedMap;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import static java.util.Comparator.*;
import static java.util.stream.Collectors.*;
import java.util.TreeMap;

public class Operations {

	private Map<String, Product> products;
	private Map<String, Client> clients;
	private Map<Integer, Order> orders;
	private int numOrders;
	//private Map<String, Integer> discounts;


	public Operations () {
		products =new HashMap<>();
		//discounts = new HashMap<>();
		clients = new HashMap<>();
		orders = new HashMap<>();
		numOrders = 0;
	}

//R1
	
	/**
	* adds a new product type with name, number of products and price. 
	* 
	* @param productType name of the product type (unique labels that contain no spaces);
	* @param n number of products belonging to the product type;
	* @param price the product price, which is the same for a single product item.
	* @return the overall price of all the products of the same product type.
	* @throws OException if the product type is duplicated.
	*/
	public int addProductType(String productType, int n, int price) throws OException {
		if (products.containsKey(productType)) {
			throw new OException("Product type already present");
		}
		products.put(productType, new Product(productType, n, price));

		return products.get(productType).getTotPrice();
	}

	/**
	* gives the number of products belonging to the given productType.
	* 
	* @param productType name of the product type.
	* @return the number of products belonging to the product type.
	* @throws OException if the productType does not exist.
	*/
	public int getNumberOfProducts(String productType) throws OException {
		if (!products.containsKey(productType)) {
			throw new OException("Product type already present");
		}
		return products.get(productType).getN();
	}

	/**
	* groups the product types by increasing price. 
	* 
	* @param productType name of the product type.
	* @return the sorted map of products grouped by price. The product types with same price are listed in alphabetical order.
	*/
	public SortedMap<Integer, List<String>> groupingProductTypesByPrices() {
		return products.values().stream().sorted(comparing(Product::getType)).collect(groupingBy(Product::getPrice, TreeMap::new, mapping(Product::getType, toList())));
	}

//R2
	
	/**
	* adds a discount to a customer (the same customer can receive more than one discount).
	* 
	* @param customer name of the customer;
	* @param discount amount of the discount.
	* @return the total discount received by the customer.
	*/
	public int addDiscount(String customer, int discount) {
		if (!clients.containsKey(customer)) {
			clients.put(customer, new Client(customer, discount));
		}
		else {
			clients.get(customer).addDiscount(discount);
		}
		return clients.get(customer).getDiscount();
	}


	public Order createOrder(String c, String s, int discount) throws OException{
		try {
			String [] orders = s.strip().split(" ");
			Map<String, Integer> m = new HashMap<>();
			int sum = 0;
			for (String o : orders) {
				String[] splits = o.strip().split("\\s*:\\s*");
				String p = splits[0];
				int q = Integer.parseInt(splits[1]);
				if (!products.containsKey(p)) {
					throw new OException("No product found");

				}
				if (products.get(p).getN() < q ) {
					return null;
				}
				if (clients.get(c).getDiscount() < discount) {
					throw new OException("Not good");
				}
				sum += (products.get(p).getPrice() * q);
				m.put(p, q);
			}
			numOrders++;
			for (Map.Entry<String, Integer> e : m.entrySet()) {
				products.get(e.getKey()).setN(-e.getValue());
				clients.get(c).addOrderedStuff(e.getKey());
				clients.get(c).addTotOrdered(e.getValue());
			}
			clients.get(c).addDiscount(-discount);
			clients.get(c).setOrdered();
			clients.get(c).setMaxExpense(sum-discount);
			Order o = new Order(numOrders, c, m, discount, sum);
			this.orders.put(numOrders, o);
			return o;
		}
		catch (Exception e ) {
			throw new OException(s);
		}
	}
	
	/**
	* places customers' orders. 
	* If not all the number of products requested are available the result is 0; the customer doesn't buy anything.
	* If the order is placed:
	*  - reduces the number of available products by the number of products requested by the customer order;
	*  - reduces the available discount by the requested discount.
	*  
	* @param customer the name of the customer.
	* @param ptpn the products with the relative quantity (example: "tableX:2 loungeChair:1").
	* @param discount the discount requested.
	* @return the total order price (the sum of the prices of the products minus the discount (if greater than 0)).
	* @throws OException if the requested discount exceeds the available discount for the customer.
	*/
	public int customerOrder(String customer, String ptpn, int discount) throws OException {
		if (!clients.containsKey(customer)) {
			throw new OException("Customer not found");
		}
		if (discount < 0 ) {
			throw new OException("Discount must be at least zero");
		}
		try {
			Order o = createOrder(customer, ptpn, discount);
			if ( o == null) {
				return 0;
			}
			else {
				return o.getTotAfterDisc();
			}
		}
		catch (OException oe) {
			throw oe;
		}
	}

	/**
	* gets the available discount for the customer as the difference between the total discount received by the
	* customer and the sum of the discounts already used.
	*  
	* @param customer the name of the customer.
	* @return the available discount for the customer (0 if there is no discount that can be used).
	*/
	public int getDiscountAvailable(String customer) {
		return clients.get(customer).getDiscount();
	}

//R3
	/**
	* allows a customer to give a score in the range from 4 to 10 to the product type.
	* 
	* @param customer the name of the customer.
	* @param productType the name of the product type.
	* @param score the score assigned by the customer to the product type.
	* @return the overall number of product types scores given by the customer.
	* @throws OException if the customer has not purchased the indicated product type, has already given a score to the product type, or the score is out of range.
	*/
	public int evalByCustomer(String customer, String productType, int score) throws OException {
		if (clients.get(customer).getEval().containsKey(productType)) {
			throw new OException("Eval already present");
		}
		if (!clients.get(customer).getOrderedStuff().contains(productType))  {//orders.values().stream().map(Order::getOrderMap).filter(o->o.containsKey(productType)).count() > 0 && 
			throw new OException("Stuff not bought");
		}	
		if (score > 10 || score < 4) {
			throw new OException("Score out of range");
		}
		clients.get(customer).addEval(productType, score);
		return clients.get(customer).getEval().keySet().size();
	}

	/**
	* gets the score assigned by the customer to the indicated type of product.
	* 
	* @param customer the name of the customer.
	* @param productType the name of the product type.
	* @return the score assigned by the customer to the indicated type of product.
	* @throws OException if the customer has not assigned a score to that product type.
	*/
	public int getScoreFromProductType(String customer, String productType) throws OException {
		if (!clients.get(customer).getEval().containsKey(productType)) {
			throw new OException("Eval not found");
		}
		return clients.get(customer).getEval().get(productType);
	}

	/**
	* groups customers by increasing scores relating to the type of product indicated.
	* 
	* @param productType the name of the product type.
	* @return the the sorted map of customers grouped by scores and listed in alphabetical order.
	*/
	public SortedMap<Integer, List<String>> groupingCustomersByScores(String productType) {
		return clients.values().stream().sorted(comparing(Client::getName)).filter(c->c.getEval().containsKey(productType)).collect(groupingBy(c->c.getEval().get(productType), TreeMap::new, mapping(Client::getName, toList())));
	}

//R4
	
	/**
	* groups the customers by increasing number of products purchased. 
	* Customers with no orders are not reported.
	* 
	* @return the the sorted map of customers grouped by number of products purchased and listed in alphabetical order.
	*/
	public SortedMap<Integer, List<String>> groupingCustomersByNumberOfProductsPurchased() {
		return clients.values().stream().filter(Client::getOrdered).sorted(comparing(Client::getName)).collect(groupingBy(Client::getTotOrdered, TreeMap::new, mapping(Client::getName, toList())));
	}
	
	/**
	* provides the largest expense in an individual order for each customer.
	* Customers with no orders are not reported.
	* 
	* @return the the sorted map of customers and their largest expenses in increasing order.
	*/
	public SortedMap<String, Integer> largestExpenseForCustomer() {
		return clients.values().stream().filter(Client::getOrdered).sorted(comparing(Client::getName)).collect(toMap(Client::getName, Client::getMaxExpense,  (oldV, newV)-> oldV, TreeMap::new));
	}

}
