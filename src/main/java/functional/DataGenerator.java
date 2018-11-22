package functional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import functional.Item.Color;

public class DataGenerator {
	static List<String> types = Arrays.asList("cookie", "potato", "cars", "toys", "fall leaves", null);

	public static List<Item> makeDataList(final int size) {
		int count = 0;
		List<Item> data = new ArrayList<>();
		do {
			int randint = (int) (Math.random() * 100);
			int index = randint % Item.Color.values().length;
			if (Math.random() < 0.05) {
				data.add(new Item(types.get(randint % types.size()), Color.values()[index], Math.random() * 1000));
			} else {
				data.add(null);
			}
			count++;
		} while (count < size);

		return data;

	}

}
