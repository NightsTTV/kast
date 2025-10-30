public abstract class Animal {
	// instance variables
	private String type;

	// constructor
	public Animal(String type) {
		this.type = type;
	}

	//getter method
	public String getType() {
		return this.type;
	}

	public abstract void move();

	public abstract String call();
}