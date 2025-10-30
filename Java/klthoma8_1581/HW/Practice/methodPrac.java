import java.util.Scanner;

public class methodPrac{

	public static void main(String[] args) {
		System.out.println("Print your name.");
		System.out.println(getName());
	}

	public static String getName() {

		return new Scanner(System.in).nextLine();
	}
}