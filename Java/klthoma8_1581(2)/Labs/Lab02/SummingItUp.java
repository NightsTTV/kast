import java.util.Scanner;

public class SummingItUp {
	public static void main(String[] args){
		Scanner input = new Scanner(System.in);
		int testcase = input.nextInt();

			for (int t = 0; t < testcase; t++){
				int i = 0;

				int num1 = input.nextInt();
				int num2 = input.nextInt();

		if (num1 < num2) {
			while (num1 <= num2) {
				i += num1;
				num1++;
			}
		} else {
			while (num1 >= num2) {
				i += num1;
				num1--; 
			}
		}
		System.out.println(i);
		}
	}
}