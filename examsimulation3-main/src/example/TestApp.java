package example;
import static org.junit.Assert.*;
import org.junit.Test;

import java.util.*;
import operations.*;

public class TestApp {

	@Test
	public void test() throws OException {
		Operations op = new Operations();
		int j = 0;
		String expected;
		String result;
		// R1
		System.out.println("R1");

		j = op.addProductType("tableX", 3, 150);
		assertEquals(450, j);
		j = op.addProductType("writingDesk", 2, 200);
		assertEquals(400, j);
		j = op.addProductType("loungeChair", 2, 150);
		assertEquals(300, j);
		j = op.addProductType("chair", 10, 70);
		assertEquals( 700,j);
		j = op.getNumberOfProducts("tableX"); // the number of available products belonging to the productType
		assertEquals(3, j);

		SortedMap<Integer, List<String>> map1 = op.groupingProductTypesByPrices();
		result = map1.toString();
		expected = "{70=[chair], 150=[loungeChair, tableX], 200=[writingDesk]}";
		assertEquals(expected, result);

		// R2
		System.out.println("R2");

		j = op.addDiscount("ctr1", 10); // String customer, int discount
		assertEquals(10, j);
		j = op.addDiscount("ctr1", 20);
		assertEquals(30, j);
		j = op.addDiscount("ctr2", 15);
		assertEquals(15, j);
		j = op.addDiscount("ctr3", 12);
		assertEquals(12, j);
		j = op.addDiscount("ctr4", 25);
		assertEquals(25, j);

		j = op.customerOrder("ctr1", "tableX:2 loungeChair:1", 20);
		assertEquals(450 - 20, j);
		j = op.getNumberOfProducts("tableX"); // the number of available products belonging to the productType
		assertEquals(1, j);

		j = op.getDiscountAvailable("ctr1"); // total discount that can be used by ctr1
		assertEquals(10, j);

		j = op.customerOrder("ctr2", "tableX:2", 0); // only one tableX available, no purchase
		assertEquals(0, j);
		j = op.getNumberOfProducts("tableX");
		assertEquals(1, j); // only one tableX available

		// R3
		System.out.println("R3");

		SortedMap<Integer, List<String>> map;

		j = op.customerOrder("ctr3", "loungeChair:1", 0);
		assertEquals( 150, j);
		j = op.evalByCustomer("ctr3", "loungeChair", 8);
		assertEquals( 1, j);
		j = op.customerOrder("ctr3", "chair:5", 0);
		assertEquals( 350, j);
		j = op.evalByCustomer("ctr3", "chair", 6);
		assertEquals( 2, j);
		j = op.customerOrder("ctr4", "chair:3", 0);
		assertEquals( 210, j);
		j = op.evalByCustomer("ctr4", "chair", 5);
		assertEquals( 1, j);
		j = op.customerOrder("ctr2", "chair:1", 0);
		assertEquals( 70, j);
		j = op.evalByCustomer("ctr2", "chair", 6);
		assertEquals( 1, j);
		map = op.groupingCustomersByScores("chair");
		// groups customers by increasing scores. Customers are listed in alphabetical
		// order.

		result = map.toString();
		expected = "{5=[ctr4], 6=[ctr2, ctr3]}";
		assertEquals(expected, result);

		j = op.getScoreFromProductType("ctr3", "chair");
		assertEquals( 6, j);

		map = op.groupingCustomersByNumberOfProductsPurchased();
		// System.out.println(map);

		// R4
		System.out.println("R4");

		SortedMap<Integer, List<String>> map2 = op.groupingCustomersByNumberOfProductsPurchased();
		result = map2.toString();
		expected = "{1=[ctr2], 3=[ctr1, ctr4], 6=[ctr3]}";
		assertEquals(expected, result);

		SortedMap<String, Integer> map3 = op.largestExpenseForCustomer();
		result = map3.toString();
		expected = "{ctr1=430, ctr2=70, ctr3=350, ctr4=210}";
		assertEquals(expected, result);
	}

}

