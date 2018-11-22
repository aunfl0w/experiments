package functional;

import java.util.List;

public class NotFunctional {
	public static void main(String[] args) {

		List<Item> listOfItems = DataGenerator.makeDataList((int) (Math.random() * 10000.0));

		// total count
		System.out.println("Total number of items is: " + listOfItems.size());
			
		// find how many are "cookies"
		int cookiesCount = 0;
		for (Item i : listOfItems) {
			if (i != null) {
				if (i.type != null) {
					if (i.type.equals("cookie")) {
						cookiesCount++;
					}
				}
			}
		}
		System.out.println("Cookies Count is: " + cookiesCount);

		// find how many until we find a space in the type
		int countUntilSpace = 0;
		for (Item i : listOfItems) {
			if (i != null) {
				if (i.type != null && i.type.contains(" ")) {
					countUntilSpace++;
				}
			}
		}
		System.out.println("Count Until Space : " + countUntilSpace);

		// sum up the cost
		double totalCost = 0.0;
		for (Item i : listOfItems) {
			if (i != null) {
				totalCost += i.cost;
			}
		}
		System.out.println("Total Cost: " + totalCost);

		// how many are YELLOW
		int yellowCount = 0;
		for (Item i : listOfItems) {
			if (i != null) {
				if (i.color == Item.Color.YELLOW) {
					yellowCount++;
				}
			}
		}
		System.out.println("Total Yellow count: " + yellowCount);

	}
}
