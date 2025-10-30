// Author: Shreya Banerjee
// This class implements Engine of a Vehicle

public class Engine {
	
	private boolean start = false;
	private boolean stop = false;
	public boolean startEngine()
	{
		start = true;
		System.out.println("Engine started");			
		return(start);
	}
	public boolean isRunning() {
		return start;
	}
	public boolean stopEngine()
	{
		stop = true;
		System.out.println("Engine stopped");			
		return(stop);
	}
}
