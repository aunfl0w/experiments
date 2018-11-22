package functional;

import java.util.Date;
import java.util.List;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import functional.Item.Color;

public class SomeFunctional {

	private static final Predicate<? super Item> NOT_NULL_TYPE = (x) -> x != null && x.type != null;
	private static final Predicate<? super Item> NOT_NULL = (x) -> x != null;
	private static final Predicate<? super Item> COOKIES = (x) -> x.type.equals("cookie");
	private static final Function<Item, Double> GET_COST = x -> x.cost;
	private static final BinaryOperator<Double> SUM_COST = (total, next) -> total + next;
	private static final Predicate<? super Item> COLOR_YELLOW = (x) -> x.color == Color.YELLOW;

	public static void main(String[] args) {
		List<Item> listOfItems = DataGenerator.makeDataList((int) (Math.random() * 50_000_000.0));

		System.out.println("Total number of items is: " + listOfItems.size());

		long cookiesCount = timed(() -> countCookies(listOfItems),"cookie time ", System.out::println); 
		System.out.println("Cookies Count is: " + cookiesCount);

		double totalCost = timed(() -> sumCost(listOfItems), "cost time ", System.out::println);
		System.out.println("Total Cost: " + totalCost);

		long yellowCount = timed(() -> countYellow(listOfItems), "yellow time ", System.out::println);
		System.out.println("Total Yellow count: " + yellowCount);
	}
	
	public static <A> A timed(Supplier<A> code, String description, Consumer<String> output) {
		final long start = new Date().getTime();
		A result = code.get();
		final long end = new Date().getTime();
		output.accept(description + (end - start));
		return result;
	}
	
	static long countYellow(List<Item> listOfItems) {
		long yellowCount = listOfItems
				.parallelStream()
				.filter(NOT_NULL)
				.filter(COLOR_YELLOW)
				.count();
		return yellowCount;
	}

	static double sumCost(List<Item> listOfItems) {
		double totalCost = listOfItems
				.parallelStream()
				.filter(NOT_NULL)
				.map(GET_COST)
				.reduce(SUM_COST)
				.orElse(0.0);
		return totalCost;
	}

	static long countCookies(List<Item> listOfItems) {
		long cookiesCount = listOfItems
				.parallelStream()
				.filter(NOT_NULL_TYPE)
				.filter(COOKIES)
				.count();
		return cookiesCount;
	}
	

}
