public class Dog {

		// instance variables
		//  if we took a snapshot of the values of all the instance
		//  variables at some point in time of the running of the program
		//  that snapshot would represent the STATE of the object

		// protected access allows direct manipulation by subtype objects
		
		protected String name;
		protected double height;
		protected double weight;
		protected boolean isCrabby;


		// instance methods

		// constructor - allows us to build and object an initialize the instance
		// variables - this method is going to have three parameters
		
		public Dog(String name, double height, double weight) {
			this.name = name;
			this.height = height;
			this.weight = weight;
			this.isCrabby = true;
		} // end constructor

		// getters or queries
		// they return something, but do not change the state of the object

		public String getName() {
				return this.name;
		}

		public double getHeight() {
				return this.height;
		}

		public double getWeight() {
				return this.weight;
		}

		// setters or commands
		// the don't return anything, but they do change the state

		public void setName(String name) {
				this.name = name;
		}

		public void setWeight(double weight) {
				this.weight = weight;
		}

		public void setHeight(double height) {
				this.height = height;
		}

		public void speak() {
				System.out.println("woof");
		}

		@Override		
		public String toString() {
			String returnVal = "";
			returnVal += "A Dog named " + this.name + " that weighs ";
			returnVal += this.weight + " lbs and is " + this.height;
			returnVal += " inches at the shoulder.";
			return returnVal;
		}

} // end class
