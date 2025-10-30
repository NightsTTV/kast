// 4/4/25
public class Student {
    private int studentId;
    private String firstName;
    private String lastName;
    private String email;
    private String password;

    public Student(int studentId, String name, String email, String password) {
        this.studentId = studentId;
        String[] nameParts = name.trim().split("\\s+", 2); // Split into first and last name
        this.firstName = nameParts[0];
        this.lastName = nameParts.length > 1 ? nameParts[1] : "";
        this.email = email;
        this.password = password;
    }

    // Getters
    public int getStudentId() { return studentId; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getName() { return firstName + " " + lastName; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }

    // Setters (optional, included for completeness)
    public void setStudentId(int studentId) { this.studentId = studentId; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public void setEmail(String email) { this.email = email; }
    public void setPassword(String password) { this.password = password; }
}