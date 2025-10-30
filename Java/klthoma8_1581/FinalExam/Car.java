public class Car extends Vehicle {
	 private Engine engine;
    private Accelerator accelerator;
    private Brake brake;

    public Car() {
        this.engine = new Engine();
        this.accelerator = new Accelerator();
        this.brake = new Brake();
    }
}
