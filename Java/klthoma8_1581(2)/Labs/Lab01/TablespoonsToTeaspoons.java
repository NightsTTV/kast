import java.util.Scanner ;

public class TablespoonsToTeaspoons {
	public static void main(String[] args){
		Scanner scanner = new Scanner(System.in);
		int tbleSpoon = scanner.nextInt(); // takes the input value as Tablespoons
		int teaSpoons = (tbleSpoon * 3); // takes the tblespoon and and turns it into teaspoons
		System.out.println(teaSpoons); // prints teaspoons
	}
}