import java.util.Scanner;

public class AnInterestingProblem {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        
        int testcase = input.nextInt(); // Number of test cases

        for (; testcase >= 1; testcase--) {
            double accountBalance = input.nextDouble(); // Get balance for each test case
            double iRate = input.nextDouble(); // Get interest rate for each test case
            int years2Goal = 0; // Reset years to 0 for each test case
            int goal = 1000000; // Set the goal to 1,000,000

            // Calculate how many years it takes to reach the goal
            while (accountBalance < goal) {
                accountBalance = accountBalance * (1 + (iRate/100)); // Apply interest rate
                years2Goal++; // Increment year count
            }

            // Output the result for the current test case
            System.out.printf("%d years\n", years2Goal);
        }

    }
}
