public class Dowhile {
public static void main(String[] args) { 
boolean n = false;
int count = 0;

do { 
if (count >= 6) { 
break; 
} 
System.out.printf("%10.2f ", (double)count);
count++; 

}while(n);

System.out.printf("\nThe loop breaks at: %d\n", count);
} 
} 
