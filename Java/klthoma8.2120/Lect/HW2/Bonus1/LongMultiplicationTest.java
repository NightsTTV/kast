import static org.junit.Assert.*;
import org.junit.Test;

public class LongMultiplicationTest {

    @Test
    public void testBasicMultiplication() {
        assertEquals(56088, LongMultiplication.longMultiply(123, 456));
        assertEquals(0, LongMultiplication.longMultiply(0, 9999));
        assertEquals(42, LongMultiplication.longMultiply(6, 7));
    }
    @Test
    public void testMultiplyingByOne() {
        assertEquals(9876, LongMultiplication.longMultiply(9876, 1));
        assertEquals(9876, LongMultiplication.longMultiply(1, 9876));
    }
    @Test
    public void testSingleDigitPairs() {
        assertEquals(9, LongMultiplication.longMultiply(3, 3));
        assertEquals(16, LongMultiplication.longMultiply(4, 4));
    }
} 

