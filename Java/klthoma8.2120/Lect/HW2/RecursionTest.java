import org.junit.Test;
import static org.junit.Assert.*;

public class RecursionTest {
    @Test
    public void testSingleDigitMultiply() {
        assertEquals(12, RecursionUtils.singleDigitMultiply(3, 4));
        assertEquals(0, RecursionUtils.singleDigitMultiply(0, 5)); 
        assertEquals(15, RecursionUtils.singleDigitMultiply(5, 3));
    }
    @Test
    public void testFindMinimum() {
        Student[] students = new Student[]{new Student("Alice", "Z", 105, 3.2), new Student("Bob", "A", 101, 3.5), new Student("Charlie", "B", 103, 3.0)
        };
        assertEquals(students[1], RecursionUtils.findMinimum(students, students.length));
    }
}

