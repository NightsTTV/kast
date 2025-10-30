import java.util.Scanner;

public class PrimeNumber {
	public static void main(String[] args){
		Scanner input = new Scanner(System.in);
		int testCase = input.nextInt();

		for (int i = 0=; i < testCase; i++){
			int num = input.nextInt();
			input.nextLine();

			boolean isPrime = true;

			for (int j = 2; j < num; j++) {
				if (num % j == 0){
					isPrime = false;
					break;
				}
			}
			System.out.println(isPrime);
		}
	}
}