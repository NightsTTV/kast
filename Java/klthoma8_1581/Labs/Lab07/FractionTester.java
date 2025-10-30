public class FractionTester {
    public static void main(String[] args) {
       // Create two fractions
        Fraction f1 = new Fraction(1, 4);  // 1/2
        Fraction f2 = new Fraction(3, 2);  // 1/3
        
        // Add the fractions
        Fraction f3 = f1.add(f2);  // f3 = 1/2 + 1/3

        // Print the result in a formatted way
        System.out.printf("%s+%s=%s\n", f1, f2, f3);
    }
}
