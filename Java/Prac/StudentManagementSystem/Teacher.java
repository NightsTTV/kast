// 4/1/25
public class Teacher {
    private int teacherId;
    private String firstName;
    private String lastName;
    private String email;
    private String password;

    public Teacher(int teacherId, String name, String email, String password) {
        this.teacherId = teacherId;
        String[] nameParts = name.trim().split("\\s+", 2);
        this.firstName = nameParts[0];
        this.lastName = nameParts.length > 1 ? nameParts[1] : "";
        this.email = email;
        this.password = password;
    }

    // Setters
    public void setTeacherId(int teacherId) { this.teacherId = teacherId; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public void setEmail(String email) { this.email = email; }
    public void setPassword(String password) { this.password = password; }

    // Getters
    public int getTeacherId() { return teacherId; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getName() { return firstName + " " + lastName; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
}