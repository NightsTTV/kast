public class Human {
	private String firstName;
	private String lastName;
	private double age;
	private double height;

	private class Human(String firstName, String lastName, double age, double height){

		if (age < 0.0) {
			throw new IllegalArgumentException("Invalid age (have not been born)");
		}

		if (height < 2.0 || height > 9.0) {
			throw new IllegalArgumentException("Invalid height");
		}

		this.firstName = firstName;
		this.lastName = lastName;
		this.age = age;
		this.height = height;
	}

	public String getFirstName() {return firstName;} 

	public String getLasName() {return lastName;}

	public void setAge(double age) {
	if (age < 0.0) {
			throw new IllegalArgumentException("Invalid age (have not been born)");
		}

		this.grossSales = grossSales;
	}

	public double getAge() {return age;}

	public double setHeight(double height) {
			if (height < 2.0 || height > 9.0) {
			throw new IllegalArgumentException("Invalid height");
		}

		this.height = height;
	} 

	public double getHeight() {return height;}
}