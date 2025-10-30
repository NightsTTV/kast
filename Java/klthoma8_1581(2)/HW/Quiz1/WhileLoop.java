import java.util.Scanner;

public class WhileLoop {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int count = 1;

        // Loop until count reaches 10
        while (count <= 10) {
            int letterGrade = input.nextInt();

            // Determine the letter grade based on the input
            if (letterGrade >= 80) {
                System.out.println("A");
            } else if (letterGrade < 80 && letterGrade >= 60) {
                System.out.println("B");
            } else if (letterGrade < 60) {
                System.out.println("C");
            }

            // Increment the counter to avoid infinite loop
            count++;
        	}
        }
    }