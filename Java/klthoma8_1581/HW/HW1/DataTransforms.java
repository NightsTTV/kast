public class DataTransforms {
	public static void main(String[] args) {
		//Arthimetic
		int x = 4;
		int intResult = 6/x;
		int modulusResult = 6%x;
		double doubleResult = 3.0/x;
			System.out.printf("int result: %d\n", intResult);
			System.out.printf("mod result: %d\n", modulusResult);
			System.out.printf("double result: %f \n", doubleResult);
			String textdata = "hello world";
			System.out.printf("text: %s", textdata);
			char character = 'a';
			System.out.printf("char result: %c", character);

			//Relational operation
			boolean boolResult = x >intResult + 4;
			System.out.printf("relational: %b\n", boolResult);
			boolResult = x == 4;
			System.out.printf("relational: %b\n", boolResult);
			boolResult = 'a' != 'b';
			System.out.printf("relational: %b\n", boolResult);
		}
	}