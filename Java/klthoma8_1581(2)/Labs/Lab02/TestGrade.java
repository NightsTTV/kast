import java.util.Scanner;

public class TestGrade {
	public static void main(String[] args){
		Scanner input = new Scanner(System.in);
		int testGrade = input.nextInt();

		if (testGrade >= 90 && testGrade <= 100) {
			System.out.println("A");
		}
		else if (testGrade >= 80 && testGrade <=89) {
			System.out.println("B");
		}
		else if (testGrade >= 70 && testGrade <=79) {
			System.out.println("C");
		}
		else if (testGrade >= 50 && testGrade <=69) {
			System.out.println("D");
		}
		else {
			System.out.println("F");
		}

	}
}
	