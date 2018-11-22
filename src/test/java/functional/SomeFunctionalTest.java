package functional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

import org.junit.jupiter.api.Test;

import functional.Item.Color;

class SomeFunctionalTest {

	static ArrayList<Item> testData = new ArrayList<>();
	static {
		testData.add(new Item(
				"cookie",
				Color.BLUE,
				1.00
				));
		testData.add(new Item(
				"toaster",
				Color.YELLOW,
				20.00
				));
		testData.add(new Item(
				"electric eel",
				Color.YELLOW,
				Math.PI * 100.0
				));
	}

	@Test
	void testTimed() {
		AtomicReference<String> output = new AtomicReference<>();
		final String logMessage = "make a bunny ";
		SomeFunctional.timed(()-> logMessage, logMessage, output::set);
		String timeoutput = output.get();
		assertTrue(timeoutput.contains(logMessage));
		
	}

	@Test
	void testCountYellow() {
		assertEquals(2, 
				SomeFunctional.countYellow(testData));
	}

	@Test
	void testSumCost() {
		double expectedTotal = 
				Math.PI * 100.0 + 20.00 + 1.00;
		assertEquals(expectedTotal,
				SomeFunctional.sumCost(testData));
	}

	@Test
	void testCountCookies() {
		assertEquals(1, 
				SomeFunctional.countCookies(testData));
	}

}
