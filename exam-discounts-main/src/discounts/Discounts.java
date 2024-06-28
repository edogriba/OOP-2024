package discounts;

import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Collectors;


public class Discounts {

	private int numCards;
	private int numPurchases;
	private Map<Integer, Card> cards;
	private Map<String, Product> products;
	private Set<String> categories;
	private Map<String, Integer> discounts;
	private Map<Integer, Purchase> purchases;
	

	public Discounts() {
		numCards = 0;
		numPurchases = 0;
		cards = new HashMap<>();
		products = new HashMap<>();
		categories = new HashSet<>();
		discounts = new HashMap<>();
		purchases = new HashMap<>();
	}

	
	//R1
	public int issueCard(String name) {
		numCards++;
		cards.put(numCards, new Card(name, numCards));
	    return numCards;
	}
	
    public String cardHolder(int cardN) {
        return cards.get(cardN).getHolder();
    }
    

	public int nOfCards() {
	       return numCards;

	}
	
	//R2
	public void addProduct(String categoryId, String productId, double price) throws DiscountsException {
		if (products.containsKey(productId)) {
			throw new DiscountsException();
		}
		if (!categories.contains(categoryId)) {
			categories.add(categoryId);
			discounts.put(categoryId, 0);
		}
		products.put(productId, new Product(categoryId, productId, price));
	}
	
	public double getPrice(String productId) throws DiscountsException {
        if (!products.containsKey(productId)) {
			throw new DiscountsException();
		}
		return products.get(productId).getPrice();
	}

	public int getAveragePrice(String categoryId) throws DiscountsException {
		if (!categories.contains(categoryId)) {
			throw new DiscountsException();
		}
        double avg =  products.values().stream().filter(p->p.getCategoryCode().equals(categoryId)).collect(Collectors.averagingDouble(Product::getPrice));
		return (int) Math.round(avg);
	}
	
	//R3
	public void setDiscount(String categoryId, int percentage) throws DiscountsException {
		if (!categories.contains(categoryId) || percentage > 50 || percentage < 0) {
			throw new DiscountsException();
		}
		discounts.put(categoryId, percentage);
	}

	public int getDiscount(String categoryId) {
        return discounts.get(categoryId);
	}

	//R4
	public int addPurchase(int cardId, String... items) throws DiscountsException {
		Map<Product, Integer> map = new HashMap<>();
		if (!cards.containsKey(cardId)) {
			throw new DiscountsException();
		}
		for (String i : items) {
			String[] splittings = i.split(":");
			if (!products.containsKey(splittings[0])) {
				throw new DiscountsException();
			}
			products.get(splittings[0]).addPurchasedunits(Integer.parseInt(splittings[1]));
			map.put(products.get(splittings[0]), Integer.parseInt(splittings[1]));
		}
		numPurchases++;
		double sum = 0;
		double disc = 0.0;
		for (Map.Entry<Product, Integer> e : map.entrySet()) {
			sum += e.getKey().getPrice() * e.getValue() * (1.0-(double)(discounts.get(e.getKey().getCategoryCode())/(double) 100));
			disc += e.getKey().getPrice() * e.getValue() * ((double)discounts.get(e.getKey().getCategoryCode())/100);
		}
		purchases.put(numPurchases, new Purchase(Optional.ofNullable(cardId), numPurchases, map, sum, disc));
		cards.get(cardId).addPurchase(sum);
        return numPurchases;
	}

	public int addPurchase(String... items) throws DiscountsException {
		Map<Product, Integer> map = new HashMap<>();
		for (String i : items) {
			String[] splittings = i.split(":");
			if (!products.containsKey(splittings[0])) {
				throw new DiscountsException();
			}
			products.get(splittings[0]).addPurchasedunits(Integer.parseInt(splittings[1]));
			map.put(products.get(splittings[0]), Integer.parseInt(splittings[1]));
		}
		numPurchases++;
		double sum = 0.0;
		for (Map.Entry<Product, Integer> e : map.entrySet()) {
            sum += e.getKey().getPrice() * e.getValue();
        }
		purchases.put(numPurchases, new Purchase(Optional.ofNullable(null), numPurchases, map, sum, 0.0));
        return numPurchases;
	}
	
	public double getAmount(int purchaseCode) {
        return purchases.get(purchaseCode).getAmount();
	}
	
	public double getDiscount(int purchaseCode)  {
        return purchases.get(purchaseCode).getDiscount();
	}
	
	public int getNofUnits(int purchaseCode) {
		return purchases.get(purchaseCode).getNumUnits();
	}
	
	//R5
	public SortedMap<Integer, List<String>> productIdsPerNofUnits() {
        return products.values().stream().filter(p->p.getPurchasedUnits() > 0).sorted(Comparator.comparing(Product::getPurchasedUnits).thenComparing(Product::getProductCode)).collect(Collectors.groupingBy(Product::getPurchasedUnits, TreeMap::new, Collectors.mapping(Product::getProductCode, Collectors.toList())));
	}
	
	public SortedMap<Integer, Double> totalPurchasePerCard() {
		return purchases.values().stream().filter(Purchase::hasCard).collect(Collectors.groupingBy(Purchase::getCard, TreeMap::new, Collectors.summingDouble(Purchase::getAmount)));
	}
	
	public double totalPurchaseWithoutCard() {
        return purchases.values().stream().filter(p->!p.hasCard()).collect(Collectors.summingDouble(Purchase::getAmount));
	}
	
	public SortedMap<Integer, Double> totalDiscountPerCard() {
		return purchases.values().stream().filter(p->p.getDiscount() > 0).collect(Collectors.groupingBy(Purchase::getCard, TreeMap::new, Collectors.summingDouble(Purchase::getDiscount)));
	}


}
