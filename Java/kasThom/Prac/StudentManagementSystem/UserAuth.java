// 4/4/25
import java.util.List;

public class UserAuth {

    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "7911";

    //Method to add Student Signup
    public static boolean studentSignup(int studentId, String name, String email, String password) {
        // Read from existing students
        List<Student> students = CSVFileHandler.readStudentFromCSV("students.csv");
        // Check if email or ID already exists
        for (Student student : students) {
            if (student.getEmail().equalsIgnoreCase(email)) {
                System.out.println("Email aleady exists");
                return false;
            }
            if (student.getStudentId() == studentId){
                System.out.println("Student ID already exists");
                 return false;
            }
        }

        // If email doesn't exist, add new student
        Student newStudent = new Student(studentId, name, email, password);
        CSVFileHandler.addStudentToCSV("students.csv", newStudent);
        return true;
    }

    public static boolean teacherSignup(int teacherId, String name, String email, String password) {
        // Read from existing Teachers
        List<Teacher> teachers = CSVFileHandler.readTeachersFromCSV("teachers.csv");
        // Check if email or ID already exists
        for (Teacher teacher : teachers) {
            if (teacher.getEmail().equalsIgnoreCase(email)) {
                System.out.println("Email already exists");
                return false;
            }
            if (teacher.getTeacherId() == teacherId) {
                System.out.println("Teacher ID already exists");
                return false;
            }
        }

        // If email doesn't exist, create new teacher
        Teacher newTeacher = new Teacher(teacherId, name, email, password);
        CSVFileHandler.addTeacherToCSV("teachers.csv", newTeacher);
        return true;
    }

    // Method to check for Student Login
    public static boolean studentLogin(String email, String password) {
        List<String[]> students = CSVFileHandler.readFromCSV("students.csv");
        for (String[] student : students) {
            if (student[2].equals(email) && student[3].equals(password)) {
                return true;
            }
        }
        return false;
    }

    // Method to check for Teacher Login
    public static boolean teacherLogin(String email, String password) {
        List<String[]> teachers = CSVFileHandler.readFromCSV("teachers.csv");
        for (String[] teacher : teachers) {
            if (teacher[2].equals(email) && teacher[3].equals(password)) {
                return true;
            }
        }
        return false;
    }

    // Method to check for Admin Login
    public static boolean adminLogin(String username, String password) {
        // Check hardcoded admin credentials first
        if (username.equals(ADMIN_USERNAME) && password.equals(ADMIN_PASSWORD)) {
            return true;
        }

        // Fallback to check admins.csv for additional admins
        List<String[]> admins = CSVFileHandler.readFromCSV("admins.csv");
        for (String[] admin : admins) {
            if (admin[1].equals(username) && admin[2].equals(password)) {
                return true;
            }
        }
        return false;
    }
}