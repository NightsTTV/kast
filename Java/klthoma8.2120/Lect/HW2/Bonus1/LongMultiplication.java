public class LongMultiplication {

    /**
     * Recursively multiplies two positive multi-digit integers
     * using single-digit multiplication
     * @param num1 the multiplicand
     * @param num2 the multiplier
     * @return the product of num1 and num2
     */
    public static int longMultiply(int num1, int num2) {
        return multiplyHelper(num1, String.valueOf(num2), 0);
    }

    //function to recursively solve each digit of num2
    private static int multiplyHelper(int num1, String num2Str, int digitPlace) {
        if (num2Str.length() == 0) {
            return 0;
        }

        char digitChar = num2Str.charAt(num2Str.length() - 1);
        int digit = digitChar - '0';

        //multiplication with that digit
        int partial = RecursionUtils.singleDigitMultiply(num1, digit);

        //correct place value 
        int shifted = partial * pow10(digitPlace);

        // cut off last digit and move to the left
        return shifted + multiplyHelper(num1, num2Str.substring(0, num2Str.length() - 1), digitPlace + 1);
    }

    //recursive call
    private static int pow10(int exp) {
        if (exp == 0) {
            return 1;
        }

        return 10 * pow10(exp - 1);
    }
} 

