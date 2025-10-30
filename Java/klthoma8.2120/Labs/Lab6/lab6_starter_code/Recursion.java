public class Recursion {

    public static void main(String[] args) {
        System.out.println("Fibonacci: " + fibonacci(10));
        System.out.println("Factorial: " + factorial(5));
        System.out.println(stringWalker("Beware M. Bison's psycho power!"));
    }


    public static long fibonacci(long nthTerm) {
        if (nthTerm == 0) {
            return 0;
        }
        if (nthTerm == 1) {
            return 1;
        }
        return fibonacci(nthTerm - 1) + fibonacci(nthTerm - 2);
    }

   
    public static long factorial(long n) {
        System.out.println("n: " + n); // Keep this line
        if (n == 0 || n == 1) {
            return 1;
        }
        return n * factorial(n - 1);
    }


    public static char stringWalker(String string) {
        // Base case: If the string is empty, return a default character (e.g., '\0')
        if (string.length() == 0) {
            return '\0'; // No 'M' found
        }
        // Check if the last character is 'M'
        char lastChar = string.charAt(string.length() - 1);
        if (lastChar == 'M') {
            return lastChar; // Return 'M' if found
        }
        // Recursive case: Remove the last character and call the method again
        System.out.println(string); // Keep this line
        return stringWalker(string.substring(0, string.length() - 1));
    }
}