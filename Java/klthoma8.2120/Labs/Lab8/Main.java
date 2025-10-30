// Main.java
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        if (args.length != 1) {
            System.err.println("Usage:  java Main numstudents");
            System.exit(1);
        }

        int numStudents = Integer.parseInt(args[0]);
        RandomStudentGenerator myGenerator = new RandomStudentGenerator();
        Collection<Student> studentList = myGenerator.generate(numStudents);

        // print unsorted
        System.out.println("Unsorted students:");
        for (Student s : studentList) {
            System.out.println(s);
        }

        // now sort by GPA using an anonymous Comparator
        List<Student> list = new ArrayList<>(studentList);
        Collections.sort(list, new Comparator<Student>() {
            @Override
            public int compare(Student s1, Student s2) {
                return Double.compare(s1.getGPA(), s2.getGPA());
            }
        });

        // print sorted
        System.out.println("\nStudents sorted by GPA:");
        for (Student s : list) {
            System.out.println(s);
        }
    }
}
