import java.util.Scanner;

public class SimpleAddition {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in); // new scanner input
		int n1 = scanner.nextInt(); // takes first number from user input
		int n2 = scanner.nextInt(); // takes second number from user input
		System.out.println(n1 + n2); // uses an addition function and prints expected integer of combind user input values.
	}
}
