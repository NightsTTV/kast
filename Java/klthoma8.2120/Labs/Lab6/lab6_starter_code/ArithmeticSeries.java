public class ArithmeticSeries {

    public static void main(String[] args) {
        int N = 10; // Example value of N
        long sum = calculateArithmeticSeries(N);
        System.out.println("Sum of arithmetic series up to " + N + " is: " + sum);

        // Verify the result using the formula
        long expectedSum = N * (N + 1) / 2;
        System.out.println("Expected sum using formula: " + expectedSum);
    }

    // Static method to calculate the arithmetic series
    public static long calculateArithmeticSeries(int N) {
        long sum = 0;
        for (int i = 1; i <= N; i++) {
            sum += i;
        }
        return sum;
    }
}