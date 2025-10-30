// Author: Shreya Banerjee
// This class implements a generic Vehicle

public abstract class Vehicle implements Movement
{
		private final String make;
		private final String model;
		private final String idNumber;
		protected Brake brake;
		protected Accelerator accelerator;
		protected Engine engine;
	    
    // constructor
    public Vehicle(String make,String model,String id) {
       this.make = make;
       this.model = model;
       this.idNumber = id;
       this.brake = new Brake();
       this.accelerator = new Accelerator();
       this.engine = new Engine();
    } 
    

    // return make
    public String getMake() {return make;}
    
    // return model
    public String getModel() {return model;}

    // return ID number
    public String getIdNumber() {return idNumber;}
    
    @Override
    public String toString() {
       return String.format("%s %s %n Vehicle identification number: %s", 
    		   getMake(), getModel(), getIdNumber());
    }
	 
	public abstract void move();
	
}
