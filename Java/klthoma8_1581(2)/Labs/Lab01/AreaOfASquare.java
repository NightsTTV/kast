import java.util.Scanner;

public class AreaOfASquare {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
			int length = scanner.nextInt(); // input value = length
			int area = (length * length); // area = length^2 or length * length 
		System.out.println(area);  // system takes the output value and prints it
	}
}
