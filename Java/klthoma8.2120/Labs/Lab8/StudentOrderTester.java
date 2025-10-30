// JUNit 5 imports
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;


public class StudentOrderTester {

    private Student s1;
    private Student s2;
    private Student s3;
    private Student s4;
    private Student s5;

    @BeforeEach
    public void setup() {
        s1 = new Student("Siobhan","OMalley",12345);
        s2 = new Student("Shirelle","Jackson",67890);
        s3 = new Student("Jackie","DeStefano",34567);
        s4 = new Student("Michael","Jackson",98765);
        s5 = new Student("Michael","Levitt",70122);
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
    }

    @Test
    public void testInOrder() {
        assertTrue( s2.compareTo(s1) < 0 ); 
        assertTrue( s3.compareTo(s1) < 0 ); 
        assertTrue( s4.compareTo(s1) < 0 ); 
        assertTrue( s5.compareTo(s1) < 0 ); 
        assertTrue( s3.compareTo(s2) < 0 ); 
        assertTrue( s4.compareTo(s2) < 0 ); 
        assertTrue( s5.compareTo(s2) < 0 ); 
        assertTrue( s3.compareTo(s4) < 0 ); 
    }

    @Test
    public void testOutOfOrder() {
        assertTrue( s1.compareTo(s2) > 0 ); 
        assertTrue( s1.compareTo(s3) > 0 ); 
        assertTrue( s1.compareTo(s4) > 0 ); 
        assertTrue( s1.compareTo(s5) > 0 ); 
        assertTrue( s2.compareTo(s3) > 0 ); 
        assertTrue( s2.compareTo(s4) > 0 ); 
        assertTrue( s2.compareTo(s5) > 0 ); 
        assertTrue( s4.compareTo(s3) > 0 ); 
    }

    @Test
    public void testEqualOrIndistinguishable() {
        assertTrue( s4.compareTo(s5) ==  0 ); 
        assertTrue( s5.compareTo(s4) ==  0 ); 
    }

}
