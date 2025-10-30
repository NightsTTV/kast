public class Fish extends Animal{
	// Fish is a subclass of Animal

	public Fish (){
		// no constructor since its a subclass
		super("Fish");
	}
		@Override
		public void move(){
			System.out.println("I just keep swimming for real.");
		}
}