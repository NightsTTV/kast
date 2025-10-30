// JUNit 5 imports
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

// import for Comparator interface
import java.util.Comparator;

public class StudentOrderTester2 {

    private Student s1;
    private Student s2;
    private Student s3;
    private Student s4;
    private Student s5;
    private StudentIDComparator myComparator;

    @BeforeEach
    public void setup() {
        s1 = new Student("Siobhan","OMalley",12345);
        s2 = new Student("Shirelle","Jackson",67890);
        s3 = new Student("Jackie","DeStefano",34567);
        s4 = new Student("Michael","Jackson",98765);
        s5 = new Student("Michael","Levitt",70122);

        myComparator = new StudentIDComparator();
        
    }

    @Test
    public void testIntialState() {

        assertEquals("Siobhan", s1.getFirstName());
        assertEquals("Shirelle", s2.getFirstName());
        assertEquals("Jackie", s3.getFirstName());
        assertEquals("Michael", s4.getFirstName());
        assertEquals("Michael", s5.getFirstName());

        assertEquals("OMalley", s1.getLastName());
        assertEquals("Jackson", s2.getLastName());
        assertEquals("DeStefano", s3.getLastName());
        assertEquals("Jackson", s4.getLastName());
        assertEquals("Levitt", s5.getLastName());

        assertEquals(12345, s1.getStudentID());
        assertEquals(67890, s2.getStudentID());
        assertEquals(34567, s3.getStudentID());
        assertEquals(98765, s4.getStudentID());
        assertEquals(70122, s5.getStudentID());
    
    }

    @Test
    public void testInOrder() {
        assertTrue( myComparator.compare(s1,s2) < 0 ); 
        assertTrue( myComparator.compare(s1,s3) < 0 ); 
        assertTrue( myComparator.compare(s1,s4) < 0 ); 
        assertTrue( myComparator.compare(s1,s5) < 0 ); 
        assertTrue( myComparator.compare(s3,s2) < 0 ); 
        assertTrue( myComparator.compare(s2,s4) < 0 ); 
        assertTrue( myComparator.compare(s3,s4) < 0 ); 
        assertTrue( myComparator.compare(s3,s5) < 0 ); 
        assertTrue( myComparator.compare(s5,s4) < 0 ); 
    }

    @Test
    public void testOutOfOrder() {
        assertTrue( myComparator.compare(s2,s1) > 0 ); 
        assertTrue( myComparator.compare(s3,s1) > 0 ); 
        assertTrue( myComparator.compare(s4,s1) > 0 ); 
        assertTrue( myComparator.compare(s5,s1) > 0 ); 
        assertTrue( myComparator.compare(s2,s3) > 0 ); 
        assertTrue( myComparator.compare(s4,s2) > 0 ); 
        assertTrue( myComparator.compare(s4,s3) > 0 ); 
        assertTrue( myComparator.compare(s5,s3) > 0 ); 
        assertTrue( myComparator.compare(s4,s5) > 0 ); 
    }

    @Test
    public void testEqualOrIndistinguishable() {
        assertTrue( myComparator.compare(s1,s1) == 0 ); 
        assertTrue( myComparator.compare(s2,s2) == 0 ); 
        assertTrue( myComparator.compare(s3,s3) == 0 ); 
        assertTrue( myComparator.compare(s4,s4) == 0 ); 
        assertTrue( myComparator.compare(s5,s5) == 0 ); 
    }

}
