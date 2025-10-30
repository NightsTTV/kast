import java.util.Scanner;

public class averaging{
public static void main(String[] args){
	Scanner input = new Scanner(System.in);
	System.out.println("Enter Value 1:");
	int v1 = input.nextInt();
	System.out.println("Enter Value 2:");
	int v2 = input.nextInt();
	float avg = (v1 + v2)/2;
	System.out.printf("%.3f%n", avg);
	}
}