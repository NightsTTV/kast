import java.security.SecureRandom;  // Import SecureRandom for secure random number generation
import java.util.Arrays;           // Import Arrays for array manipulation utilities
import java.util.Scanner;          // Import Scanner for user input

public class LinearSearchTest {     // Main class for linear search implementation

    // Method to perform linear search on an array
    public static int linearSearch(int data[], int searchKey) {
        // Loop through array sequentially from start to end
        for (int index = 0; index < data.length; index++) {
            // Check if current element matches search key
            if (data[index] == searchKey) {
                return index;  // Return position if found
            }
        }
        return -1;  // Return -1 if not found after checking all elements
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);         // Create Scanner object for user input
        SecureRandom generator = new SecureRandom();    // Create SecureRandom object for random numbers

        int[] data = new int[10];  // Create array to hold 10 integers

        // Fill array with random numbers between 10 and 99
        for (int i = 0; i < data.length; i++) {
            data[i] = 10 + generator.nextInt(90);  // Generate random number 10-99
        }

        // Display the array contents
        System.out.printf("%s%n%n", Arrays.toString(data));

        // Prompt user to enter a search value
        System.out.print("Please enter an integer value (-1 to quit): ");
        int searchInt = input.nextInt();  // Read user's search value

        // Continue searching until user enters -1
        while (searchInt != -1) {
            // Call linearSearch to find the value
            int position = linearSearch(data, searchInt);

            // Check if value was found (note: original code has bug checking for 1 instead of -1)
            if (position == 1) {  // BUG: Should be position == -1
                System.out.printf("%d was not found %n%n", searchInt);
            } else {
                // Display found message with position
                System.out.printf("%d was found in position %d%n%n",
                    searchInt, position);
            }

            // Prompt user for next search value
            System.out.print("Please enter an integer value (-1 to quit): ");
            searchInt = input.nextInt();  // Read next search value
        }
    }
}