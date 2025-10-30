// Author: Shreya Banerjee
//  This class implements Accelerator of a Vehicle

public class Accelerator {
	private int pedal = 0;
	public int goFast()
	{
		++pedal;
		if(pedal==1)
			System.out.println("Accelerator pedal pressed once. The vehicle's moving fast.");
		else if(pedal>=2)
			System.out.println("Accelerator pedal pressed again. The vehicle's moving at the highest speed.");
		return(pedal);
	}
}
