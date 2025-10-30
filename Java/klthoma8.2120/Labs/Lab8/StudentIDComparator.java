// StudentIDComparator.java
import java.util.Comparator;

public class StudentIDComparator implements Comparator<Student> {
    @Override
    public int compare(Student s1, Student s2) {
        // increasing numerical order of studentID
        return Integer.compare(s1.getStudentID(), s2.getStudentID());
    }
}
