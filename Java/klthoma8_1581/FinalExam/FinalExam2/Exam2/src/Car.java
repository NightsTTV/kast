//Copy the code and complete the to-dos

// Author: Shreya Banerjee
// This class implements a generic Car

public class Car extends Vehicle{
	private final String type; 
	   // constructor
    public Car(String make,String model,String id, String type) {
     super(make,model,id);
     this.type = type;
    } 
    
    // return type
    public String getType() {return type;}
    
    @Override
	public void move()
	{
    	//**  To-do 1: ** 
    	
		 try {
            // Start the engine and check if the engine is on
            engine.startEngine();
            if (!engine.isRunning()) {
                throw new IllegalStateException("Engine failed to start.");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return; // Exit the method if engine fails to start
        }

        // Accelerating
        System.out.printf("%d%n", accelerator.goFast());
		
		//**  To-do 2: ** 
		
		//Similar to accelerating as above, now slow down(i.e. use brake)
		
		System.out.printf("%d%n", brake.slowDown());

		//**  To-do 3: ** 
		
		 try {
            // Stop the engine and check if the engine has stopped
            engine.stopEngine();
            if (engine.isRunning()) {
                throw new IllegalStateException("Engine failed to stop.");
            }
        } catch (Exception e) {
            System.out.println("Error:" + e.getMessage());
        }
    }
    
    @Override
    public String toString() {
       return String.format("%s %s%n%s identification number: %s", 
    		   getMake(), getModel(), getType(), getIdNumber());
    }

}
