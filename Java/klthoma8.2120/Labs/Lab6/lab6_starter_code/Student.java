public class Student {
	private int studentId;
	private String name;
	private String email;
	private String password;

	public Student(int studentId, String name, String email, String password) {
		this.studentId = studentId;
		this.name = name;
		this.email = email;
		this.password = password;
	}

	public void setStudentId(int studentId) {
		this.studentID = studentID;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getStudentId() {
		return studentID;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

}