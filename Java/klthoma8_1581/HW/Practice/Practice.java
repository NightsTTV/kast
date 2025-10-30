public class Practice{
public static void main(String[] args){
	System.out.println(3+8/5+"4"+3*1/4+3+2 );
	System.out.println(!((true || 4>5) && 1==2));
	System.out.println("Cat".equals("cat"));
	System.out.println("");
	System.out.println("");
	int x = 4;
	int intResult = 6/x;
	int modulusResult = 6%x;
	double doubleResult = 3.0/x;
	System.out.printf("int result:%d\n", intResult);
	System.out.printf("mod result:%d\n", modulusResult);
	System.out.printf("int result: %f\n", doubleResult);
	System.out.printf("int result: %f\n", doubleResult);
	String textdata = "hello world";
	System.out.printf("text: %s\n", textdata);
	char character = 'a';
	System.out.printf("char result: %c\n", character);

	//Relational operation 
	boolean boolResult = x > intResult + 4;
	System.out.printf("relational: %b\n", boolResult);
	boolResult = x == 4;
	System.out.printf("relational: %b\n", boolResult);
	boolResult = 'a'!= '4';
	System.out.printf("relational: %b\n", boolResult);
	
	System.out.printf("String name variable: %s\n", name);
	System.out.printf("String  variable: %s\n", pra);
	System.out.printf("String name variable: %s\n", string.name);

int i = 6;
int j; // uninitialized
int [ ] a = {1,3,5,7,9}; // creates a 5-element array
int [ ] b = new int[3]; // auto initialized to 0
String s = new String("abcdef“); // creates a new string
String t = null; // null is a reference
j=i; //copyavalue
b = a; // copy a reference; now nothing refers to [0,0,0]
t=s; //now s and t refer to the same object
a[0] = 6;
t = t +”g”; // make a new string. Why?
	}
}

