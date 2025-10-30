public class InitArray1 {
    public static void main(String[] args) {
        System.out.println("Declare and initialize an array separately:");
        
        // declare variable array and initialize it with an array object
        int[] array = new int[10]; // create the array object
        System.out.printf("Array size: %d%n", array.length); // Every array object knows its own length and stores it in a length instance variable
        System.out.printf("%s%8s%n", "Index", "Value"); // Printing column headings
        
        // What value does an integer array store by default? Let's see...
        // Iterates over each element of array to access and output each element's value
        for (int counter = 0; counter < array.length; counter++) {
            System.out.printf("%5d%8d%n", counter, array[counter]); // By default integer array contains 0s
        }
        
        // Change each array element's value (arrays are mutable, remember?)
        for (int counter = 0; counter < array.length; counter++) {
            array[counter] = counter + 1;
        }
        
        // Prints the same array now having new values
        System.out.printf("%s%8s%n", "Index", "Value"); // Printing column headings
        for (int counter = 0; counter < array.length; counter++) {
            System.out.printf("%5d%8d%n", counter, array[counter]);
        }
        
        System.out.println("Declare and initialize array at the same time, using array initializer:");
        
        // declare and initialize array using array initializer
        int[] x = {100, 23, 45, 56, 89, 14}; // Array initializer is the ordered list of array elements within {}
        
        // output each element's value using for loop with a counter starting from 1
        for (int counter = 1; counter <= x.length; counter++) {
            System.out.printf("%5d%8d%n", counter - 1, x[counter - 1]);
        }
        
        // Are these the same?
        // Access 5th element of array x
        System.out.println(x[4]);
        
        // Access element at index 5 of array x
        System.out.println(x[5]);
        
        // Access last element of the array
        System.out.printf("Last item: %d%n", x[x.length - 1]);
    }
}