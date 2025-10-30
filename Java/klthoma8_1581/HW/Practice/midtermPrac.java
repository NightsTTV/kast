public class midtermPrac{
	public static void main(String[] args){
		int age;
		age = 3;

		if (age == 1){
			System.out.println("1");
		}else if (age == 2){
			System.out.println("2");
		}else if (age == 3){
			System.out.println("3");
		}

		switch (age) {
		case 1:
			System.out.println("age is 1");
			break;
		case 2:
			System.out.println("age is 2");
			break;
		case 3:
			System.out.println("age is 3");
			break;
		case 4:
			System.out.println("age is 4");
			break;
		default:
			System.out.println("Age is unknown.");
			break;
		}
	}
}