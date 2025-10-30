import java.util.Scanner;

public class TimesTables{
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		int baseN = input.nextInt();
		int caseN = input.nextInt();
		for (int i = baseN; i <= caseN; i++){
			int total = (i * baseN);
			System.out.println(total);
		}
	}
}
