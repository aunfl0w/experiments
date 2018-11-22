package functional;

public class Item {
	
	public enum Color {
		RED, GREEN, BLUE, YELLOW;
	}

	final String type;
	final Color color;
	final double cost;

	public Item(final String type, final Color color, final double cost) {
		this.type = type;
		this.color = color;
		this.cost = cost;
	}
	
}
