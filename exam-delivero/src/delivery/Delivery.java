package delivery;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Collectors;


public class Delivery {

	private List<String> categories;
	private Map<String, Restaurant> rests;
	private Map<String, List<Dish>> restDishes;
	private Map<Integer, Order> orders;
	private Map<String, List<Integer>> ratings;
	private int numOrder;

	public Delivery() {
		categories = new LinkedList<>();
		rests = new HashMap<>();
		restDishes = new HashMap<>();
		orders = new HashMap<>();
		ratings = new HashMap();
		numOrder=0;

	}

	// R1
	
    /**
     * adds one category to the list of categories managed by the service.
     * 
     * @param category name of the category
     * @throws DeliveryException if the category is already available.
     */
	public void addCategory (String category) throws DeliveryException {
	if (categories.contains(category)) {
		throw new DeliveryException();
	}
	categories.add(category);
	}
	
	/**
	 * retrieves the list of defined categories.
	 * 
	 * @return list of category names
	 */
	public List<String> getCategories() {
		return categories;
	}
	
	/**
	 * register a new restaurant to the service with a related category
	 * 
	 * @param name     name of the restaurant
	 * @param category category of the restaurant
	 * @throws DeliveryException if the category is not defined.
	 */
	public void addRestaurant (String name, String category) throws DeliveryException {
		if (rests.containsKey(name)) {
			throw new DeliveryException();
		}
		if (!categories.contains(category)) {
			throw new DeliveryException();
		}
		rests.put(name, new Restaurant(name, category));
	}
	
	/**
	 * retrieves an ordered list by name of the restaurants of a given category. 
	 * It returns an empty list in there are no restaurants in the selected category 
	 * or the category does not exist.
	 * 
	 * @param category name of the category
	 * @return sorted list of restaurant names
	 */
	public List<String> getRestaurantsForCategory(String category) {
		List<String> l = new LinkedList<>();
		if (!categories.contains(category)) {
			return l;
		}
        l.addAll(rests.values().stream().filter(r->r.getCategory().equals(category)).map(Restaurant::getName).sorted().toList());
		return l;
	}
	
	// R2
	
	/**
	 * adds a dish to the list of dishes of a restaurant. 
	 * Every dish has a given price.
	 * 
	 * @param name             name of the dish
	 * @param restaurantName   name of the restaurant
	 * @param price            price of the dish
	 * @throws DeliveryException if the dish name already exists
	 */
	public void addDish(String name, String restaurantName, float price) throws DeliveryException {
		if (!rests.containsKey(restaurantName)) {
			throw new DeliveryException();
		}
		if (restDishes.containsKey(restaurantName)) {
			if (restDishes.get(restaurantName).stream().map(Dish::getName).toList().contains(name)) {
				throw new DeliveryException();
			}
			else {
				restDishes.get(restaurantName).add(new Dish(price, name, restaurantName));
			}
		}
		else {
			List<Dish> dishes = new LinkedList<>();
			dishes.add(new Dish(price, name, restaurantName));
			restDishes.put(restaurantName, dishes);
		}

	}
	
	/**
	 * returns a map associating the name of each restaurant with the 
	 * list of dish names whose price is in the provided range of price (limits included). 
	 * If the restaurant has no dishes in the range, it does not appear in the map.
	 * 
	 * @param minPrice  minimum price (included)
	 * @param maxPrice  maximum price (included)
	 * @return map restaurant -> dishes
	 */
	public Map<String,List<String>> getDishesByPrice(float minPrice, float maxPrice) {
        return restDishes.values().stream().flatMap(Collection::stream).filter(d-> d.getPrice() <= maxPrice && d.getPrice() >= minPrice).collect(Collectors.groupingBy(Dish::getRest, Collectors.mapping(Dish::getName, Collectors.toList())));
	}
	
	/**
	 * retrieve the ordered list of the names of dishes sold by a restaurant. 
	 * If the restaurant does not exist or does not sell any dishes 
	 * the method must return an empty list.
	 *  
	 * @param restaurantName   name of the restaurant
	 * @return alphabetically sorted list of dish names 
	 */
	public List<String> getDishesForRestaurant(String restaurantName) {
		List<String> l = new LinkedList<>();
		if (!rests.containsKey(restaurantName)) {
			return l;
		}
		if (!restDishes.containsKey(restaurantName)) {
			return l;
		}
        return restDishes.get(restaurantName).stream().map(Dish::getName).sorted().toList();
	}
	
	/**
	 * retrieves the list of all dishes sold by all restaurants belonging to the given category. 
	 * If the category is not defined or there are no dishes in the category 
	 * the method must return and empty list.
	 *  
	 * @param category     the category
	 * @return 
	 */
	public List<String> getDishesByCategory(String category) {
		List<String> l = new LinkedList<>();
		if (!categories.contains(category)) {
			return l;
		}
		if (getRestaurantsForCategory(category).isEmpty()) {
			return l;
		}
        return restDishes.values().stream().flatMap(Collection::stream).filter(d->rests.get(d.getRest()).getCategory().equals(category)).map(Dish::getName).toList();
	}
	
	//R3
	
	/**
	 * creates a delivery order. 
	 * Each order may contain more than one product with the related quantity. 
	 * The delivery time is indicated with a number in the range 8 to 23. 
	 * The delivery distance is expressed in kilometers. 
	 * Each order is assigned a progressive order ID, the first order has number 1.
	 * 
	 * @param dishNames        names of the dishes
	 * @param quantities       relative quantity of dishes
	 * @param customerName     name of the customer
	 * @param restaurantName   name of the restaurant
	 * @param deliveryTime     time of delivery (8-23)
	 * @param deliveryDistance distance of delivery
	 * 
	 * @return order ID
	 */
	public int addOrder(String dishNames[], int quantities[], String customerName, String restaurantName, int deliveryTime, int deliveryDistance) {
		
		Map<String, Integer> orderMap = new HashMap<>();
		numOrder++;
		int a = dishNames.length;
		for (int i=0; i<a; i++) {
			orderMap.put(dishNames[i], quantities[i]);
		}
		orders.put(numOrder, new Order(customerName, restaurantName, deliveryTime, deliveryDistance, orderMap, numOrder));
 	    return numOrder;
	}
	
	/**
	 * retrieves the IDs of the orders that satisfy the given constraints.
	 * Only the  first {@code maxOrders} (according to the orders arrival time) are returned
	 * they must be scheduled to be delivered at {@code deliveryTime} 
	 * whose {@code deliveryDistance} is lower or equal that {@code maxDistance}. 
	 * Once returned by the method the orders must be marked as assigned 
	 * so that they will not be considered if the method is called again. 
	 * The method returns an empty list if there are no orders (not yet assigned) 
	 * that meet the requirements.
	 * 
	 * @param deliveryTime required time of delivery 
	 * @param maxDistance  maximum delivery distance
	 * @param maxOrders    maximum number of orders to retrieve
	 * @return list of order IDs
	 */
	public List<Integer> scheduleDelivery(int deliveryTime, int maxDistance, int maxOrders) {
		List<Integer> li = new LinkedList<>();
		li.addAll(orders.values().stream().filter(a->((a.getDeliveryTime() == deliveryTime) && (a.getDeliveryDistance() <= maxDistance))).map(Order::getId).limit(maxOrders).toList());
		if (!li.isEmpty()) {
			li.stream().forEach(a->orders.get(a).setAssigned());
		}
        return li;
	}
	
	/**
	 * retrieves the number of orders that still need to be assigned
	 * @return the unassigned orders count
	 */
	public int getPendingOrders() {
        return (int) orders.values().stream().filter(Order::isNotAssigned).count();
	}
	
	// R4
	/**
	 * records a rating (a number between 0 and 5) of a restaurant.
	 * Ratings outside the valid range are discarded.
	 * 
	 * @param restaurantName   name of the restaurant
	 * @param rating           rating
	 */
	public void setRatingForRestaurant(String restaurantName, int rating) {
		if (rating >= 0 || rating <= 5 && rests.containsKey(restaurantName)){
			if (ratings.containsKey(restaurantName)) {
				ratings.get(restaurantName).add(rating);
			}
			else {
				List<Integer> lis = new LinkedList<>();
				lis.add(rating);
				ratings.put(restaurantName, lis);
			}	
		}
	}
	
	/**
	 * retrieves the ordered list of restaurant. 
	 * 
	 * The restaurant must be ordered by decreasing average rating. 
	 * The average rating of a restaurant is the sum of all rating divided by the number of ratings.
	 * 
	 * @return ordered list of restaurant names
	 */
	public List<String> restaurantsAverageRating() {
        // Not useful but still a nice stream -> return ratings.entrySet().stream().collect(Collectors.groupingBy(a->a.getKey(), Collectors.averagingInt(a->a.getValue()))).entrySet().stream().sorted(Comparator.comparing((Map.Entry<String, Double> entry) -> entry.getValue()).reversed()).map(a->a.getKey()).collect(Collectors.toList());
		SortedMap<Double, String> map = new TreeMap<>(Comparator.reverseOrder());
		for (String r : ratings.keySet()) {
			double avg = ratings.get(r).stream().collect(Collectors.averagingInt(a->a));
			map.put(avg, r);
		}
		return map.values().stream().collect(Collectors.toList());
	}
	
	//R5
	/**
	 * returns a map associating each category to the number of orders placed to any restaurant in that category. 
	 * Also categories whose restaurants have not received any order must be included in the result.
	 * 
	 * @return map category -> order count
	 */
	public Map<String,Long> ordersPerCategory() {
		Map<String, Long> m = orders.values().stream().collect(Collectors.groupingBy(o->rests.get(o.getRestName()).getCategory(), Collectors.counting()));
		for (String c : categories) {
			if (!m.containsKey(c)) {
				m.put(c, 0L);
			}
		}
        return m;
	}
	
	/**
	 * retrieves the name of the restaurant that has received the higher average rating.
	 * 
	 * @return restaurant name
	 */
	public String bestRestaurant() {
        return restaurantsAverageRating().get(0);
	}
}
