import java.util.Scanner;

public class fitnessTracker{
	public static void main(String[] args) {
		/* Fitness Tracker App
		initilize instance variables*/
		Scanner input = new Scanner(System.in);
		System.out.println("Enter your weeky exercise goal (in minutes):");

		int goal = input.nextInt();
		int[] dailyTime = new int[7];
		System.out.println("Enter minutes exercised each day (Mon-Sun):");
		boolean goalMet = false;
		int total = 0;

		//Logic
		//math
		//int total = (mon + tues + wed + thur + fri + sat + sun);
		// user input loop
		for (int i = 0; i < dailyTime.length; i++){
			System.out.println("Enter minutes exercised for day " + (i + 1) + ": ");
			dailyTime[i] = input.nextInt(); // Stores user input in array.
		}
		// store total time exercised
		for (int i = 0; i < dailyTime.length; i++){
			total += dailyTime[i];
		}
		// compare total to goal
		if (total >= goal){
			goalMet = !goalMet;
		}

		//while (input.)

		//UI

		System.out.printf("Weekly Fitness Tracker%n----------------%n");
		System.out.printf("Weekly Goal: %d%nTotal Time Exercised: %d%n", goal, total);
		System.out.printf("Monday: %d minutes%n", dailyTime[0]);
		System.out.printf("Tuesday: %d minutes%n", dailyTime[1]);
		System.out.printf("Wednesday: %d minutes%n", dailyTime[2]);
		System.out.printf("Thursday: %d minutes%n", dailyTime[3]);
		System.out.printf("Friday: %d minutes%n", dailyTime[4]);
		System.out.printf("Saturday: %d minutes%n", dailyTime[5]);
		System.out.printf("Sunday: %d minutes%n", dailyTime[6]);
        System.out.printf("----------------%n");
        System.out.printf("Goal Met? %b%n", goalMet);

	}
}