 Product types  @media print { /\* adjusted to print the html to a single-page pdf \*/ body { font-size: 10pt; }

Product types
=============

The program simulates the operations between seller and customers. All classes are found in the **operations** package. The main class is **Operations**.

The **TestApp** class in the **example** package contains examples and presents the main test cases but not all. Exceptions are thrown using the **OException** class; only the specified checks must be carried out and not all possible ones. If a method throws an exception there is no change in the data present in the main class. Suggestions: look at _TestApp_ to understand the requirements of methods; every time you have implemented a method, run _TestApp_ to check the result.

The JDK documentation is accessible at URL [https://oop.polito.it/api/](https://oop.polito.it/api/).

R1: Products and prices
-----------------------

New products can be added using method **int addProductType (String productType, int n, int price) throws OException** that has 3 parameters: the product type, the number of products belonging to the product type; the price, which is the same for a single product item. You can safely assume product types are unique labels that contain no spaces. The result is the overall price of all the products of the same product type. An exception is thrown if the product type is duplicated.

Method **int getNumberOfProducts (String productType) throws OException** gives the number of products belonging to the given productType; an exception is thrown if the productType does not exist.

Method **groupingProductTypeByPrices()** groups the product types by increasing price. The product types with same price are listed in alphabetical order.

R2: Discounts and customer orders
---------------------------------

Method **int addDiscount (String customer, int discount)** allows the seller to add a discount to the indicated customer. The same customer can receive more than one discount. The result is the total discount received by the customer.

Customers' orders can be placed using method **int customerOrder(String customer, String ptpn, int discount) throws OException** that has 3 parameters: the customer, the products with the relative quantity, and the discount requested (which may also be 0). An example that can be found in TestApp is this one: _customerOrder("ctr1", "tableX:2 loungeChair:1", 20)_; this call allows customer _ctr1_ to buy two tables (tableX is the name of the product type) and one loungeChair, a discount of 20 is requested. This string has two substrings separated by one space; a substring consists of a product type and the number of product items separated by _:_. The result is the total price: it consists of the sum of the prices of the products minus the discount (if greater than 0).  
Possible problems to be managed: 1) If not all the number of products requested are available the result is 0; the customer doesn't buy anything. 2) If the requested discount exceeds the available discount for the customer, an exception is thrown.  
In case of no problems, the number of products available is reduced by the number of products requested by the customer order. In addition, the available discount is decremented by the requested discount; the available discount is the discount that can be used by the next _customerOrder()_ call.

Method **int getDiscountAvailable (String customer)** gives the available discount for the customer (it could be 0).

R3: Evaluations and scores
--------------------------

Method **int evalByCustomer (String customer, String productType, int score) throws OException** allows a customer to give a score in the range 4..10 to the product type. An exception is thrown if the customer: has not purchased any product of the indicated product type, has already given a score to the indicated product type, or the score is out of range. The result is the overall number of product types scores given by the customer.

Method **int getScoreFromProductType (String customer, String productType) throws OException** shows the score assigned by the customer to the indicated type of product. An exception is thrown if the customer has not assigned a score to that product type.

Method **groupingCustomersByScores(String productType)** groups customers by increasing scores relating to the type of product indicated. Customers are listed in alphabetical order.

R4: Statistics
--------------

Method **groupingCustomersByNumberOfProductsPurchased()** groups the customers by increasing number (greater than 0) of products purchased. Customers are listed in alphabetical order. Customers with no orders are not reported.

Method **largestExpenseForCustomer ()** provides the largest expense in an individual order for each customer (in increasing order). Customers with no orders are not reported.