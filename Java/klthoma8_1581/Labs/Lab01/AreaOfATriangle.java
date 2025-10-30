import java.util.Scanner;

public class AreaOfATriangle {
	public static void main(String[] args){
		Scanner scanner = new Scanner(System.in); // creates a scanner that takes input stream
		double num1 = scanner.nextDouble(); //creates a double variable read by scanner
		double num2 = scanner.nextDouble(); //creates a second double to be read by the scanner
		double tArea = (0.5* num1* num2); // variable is created using num1, num 2, and area of a triangle 
		System.out.println(tArea); // prints the Area of a triangle based on user input
	}
}