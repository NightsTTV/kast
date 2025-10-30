import java.util.Scanner;

public class MinutesToSeconds {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);  // creates the scanner in the class main
		int minutes = scanner.nextInt(); // takes in value user inputs as minutes
		int seconds = (minutes * 60);  // takes minutes and multiplies it by 60
		System.out.println(seconds); // calls the system to take the output value of seconds and print it
	}
}