public class midtermPrac2 {
    public static void main(String[] args) {
        String[] array = {"1", "2", "3"};
        String[] choice = {"pizza", "burger", "sandwich"};

       
        for (int i = 0; i < array.length; i++){
        	System.out.println(array[i]);
        }
     
    int counter = 1; 
    do {
      if (counter != 1) {
        System.out.printf("%d: Fizz%n", counter); 
      } else {
        System.out.printf("%d: Buzz%n", counter); 
      } 
      counter++; 
    } while (counter < 4);  	
 

    }
}