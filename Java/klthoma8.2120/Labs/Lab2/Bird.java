public class Bird extends Animal{
	// Bird is a subclass of Animal

	public Bird (){
		// no constructor since its a subclass
		super("Bird");
	}
		@Override
		public void move(){
			System.out.println("Im flying through the air!");
		}
}