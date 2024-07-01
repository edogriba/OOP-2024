package example;

import static org.junit.Assert.*;
import org.junit.Test;

import java.util.*;

import journals.*;

public class TestApp {
	@Test
	public void test() throws JException {
		Journals js = new Journals();
		int j = 0;
		Double dd = 0.0;
		String result;

		// R1
		System.out.println("R1");
		j = js.addJournal("jSoftEng", 2.4);
		assertEquals(8, j);

		try	{
			j = js.addJournal("jSoftEng", 2.1);
			fail("Expected exception for duplicated journal"); // duplicated journal
		} catch (JException ex) {}

		double dbl = js.getImpactFactor("jSoftEng");
		assertEquals("Wrong impact factor", 2.4, dbl, 0.1);

		j = js.addJournal("jDataBases", 2.4);
		assertEquals(10, j);

		j = js.addJournal("jDataScience", 2.6);
		assertEquals(12, j);

		SortedMap<Double, List<String>> map = js.groupJournalsByImpactFactor();
		result = map.toString();
		assertEquals("{2.4=[jDataBases, jSoftEng], 2.6=[jDataScience]}", result);

		// R2
		System.out.println("R2");

		j = js.registerAuthors("a1", "b3", "b2");
		assertEquals(j, 3);
		j = js.registerAuthors("b7", "b3");
		assertEquals(j, 4);

		result = js.addPaper("jSoftEng", "newBPM", "b3", "a1");
		assertEquals("jSoftEng" + ":" + "newBPM", result);

		result = js.addPaper("jSoftEng", "newApproach1", "a1");
		assertEquals("jSoftEng" + ":" + "newApproach1", result);

		result = js.addPaper("jDataBases", "noSQL", "b3", "b2");
		assertEquals("jDataBases" + ":" + "noSQL", result);

		try {
			result = js.addPaper("newJournal", "climate change", "b3", "b2");
			fail("Expected exception for undefined journal"); // undefined journal
		} catch (JException ex) {
		}

		try {
			result = js.addPaper("jDataBases", "noSQL", "b3", "b2");
			fail("Expected exception for duplicate paper"); // dup paper
		} catch (JException ex) {
		}

		SortedMap<String, Integer> map2 = js.giveNumberOfPapersByJournal();
		result = map2.toString();
		assertEquals("{jDataBases=1, jSoftEng=2}", result);

		// R3
		System.out.println("R3");

			dd = js.getAuthorImpactFactor("b3");
			// System.out.println(dd);
			assertEquals(" ", 4.8, dd, 0.1);

		dd = js.getAuthorImpactFactor("b7");
		assertEquals("Wrong impact factor for author", 0.0, dd, 0.1);// b7 has no papers

		try {
			js.getAuthorImpactFactor("b1");
			fail("expected exception for author not registered"); // b1 not registered
		} catch (JException ex) {
		}

		SortedMap<Double, List<String>> map3 = js.getImpactFactorsByAuthors();
		result = map3.toString();
		assertEquals("{2.4=[b2], 4.8=[a1, b3]}", result);

		// R4
		System.out.println("R4");

		SortedMap<String, Integer> map4 = js.getNumberOfPapersByAuthor();
		result = map4.toString();
		assertEquals("{a1=2, b2=1, b3=2}", result);

		result = js.getJournalWithTheLargestNumberOfPapers();
		assertEquals("jSoftEng:2", result);
	}
	
	
}
