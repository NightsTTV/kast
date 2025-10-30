class Dog extends Animal {
	public Dog(String type){
		super(type);
	}
	@Override
	public void move() {
		System.out.println("The dog is walking on four legs.");
	}
	public void bark() {
		System.out.println("Woof!");
	}
}