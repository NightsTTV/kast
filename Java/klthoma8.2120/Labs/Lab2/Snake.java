public class Snake extends Animal{
	// Snake is a subclass of Animal

	public Snake (){
		// no constructor since its a subclass
		super("Snake");
	}
		@Override
		public void move(){
			System.out.println("I slither on the ground sssss.");
		}
}