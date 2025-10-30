// Author: Shreya Banerjee
//  This class implements Brake of a Vehicle

public class Brake {
	private int pedal = 0;
	public int slowDown()
	{
		++pedal;
		if(pedal==1)
			System.out.println("Brake pedal pressed once. The vehicle slowed down.");
		else if(pedal>=2)
			System.out.println("Brake pedal pressed again. The vehicle stopped.");	
		return(pedal);
	}
}
