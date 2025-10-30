public class Teacher {
	private int teacherId;
	private String name;
	private String email;
	private String password;

	public Teacher(int teacherId, String name, String email, String password) {
		this.teacherId = teacherId;
		this.name = name;
		this.email = email;
		this.password = password;
	}

	public void setTeacherId(int teacherId) {
		this.teacherID = studentID;
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

	public int getTeacherId() {
		return teacherID;
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