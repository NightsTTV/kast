// Copy the program and complete the to-dos

// Driver program to run and test the application

public class VehicleTest {
	
	public static void main(String[] args) {
	
	     
	   // Creating 3-element Movement array containing Car and SportsCar objects
	      Movement[] objects = new Movement[] {
	         new Car( "Honda", "Civic Sedan", "1HGBH41JXMN109186","Car"),
	         new SportsCar("Chevrolet", "Corvette Z06", "1ABCD41JXMN112345","SportsCar"),
	         new SportsCar("Porsche", "911 GT3", "3TN5000JXMN04300","SportsCar")};
	      
	  // Polymorphically processing each element in the array objects
	      for (Movement movingObject : objects) {
	    	  
	    	  movingObject.move();
	    	  movingObject.toString();
	    	  
	  //** To-do **: If the movingObject is an instance of a Car, call the move method again.
	    	  
		            if (movingObject instanceof Car) {
		            System.out.println("Calling move again for Car instance...");
		            ((Car) movingObject).move(); 
		        }
	
	      }
	     
	}

}
