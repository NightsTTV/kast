import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MyDoublyLinkedListTest {
    private MyDoublyLinkedList<Integer> list;

    @BeforeEach
    void setUp() {
        list = new MyDoublyLinkedList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
    }

    @Test
    void testSizeAndGet() {
        assertEquals(4, list.size());
        assertEquals(1, list.get(0));
        assertEquals(4, list.get(3));
    }

    @Test
    void testAddAtIndex() {
        list.add(2, 99);
        assertEquals(5, list.size());
        assertEquals(99, list.get(2));
        assertEquals(3, list.get(3));
    }

    @Test
    void testRemove() {
        int removed = list.remove(1);
        assertEquals(2, removed);
        assertEquals(3, list.get(1));
        assertEquals(3, list.size());
    }

    @Test
    void testSet() {
        list.set(2, 77);
        assertEquals(77, list.get(2));
    }

    @Test
    void testContainsAndIndexOf() {
        assertTrue(list.contains(3));
        assertEquals(2, list.indexOf(3));
        assertFalse(list.contains(99));
        assertEquals(-1, list.indexOf(99));
    }

    @Test
    void testForwardIteration() {
        MyDoublyLinkedList<Integer>.MyListIterator it = list.iterator();
        int[] expected = {1, 2, 3, 4};
        int idx = 0;
        while (it.hasNext()) {
            assertEquals(expected[idx++], it.next());
        }
        assertEquals(4, idx);
    }

    @Test
    void testBackwardIteration() {
        MyDoublyLinkedList<Integer>.MyListIterator it = list.iterator();
        it.setToEnd(); // move to end
        int[] expected = {4, 3, 2, 1};
        int idx = 0;
        while (it.hasPrevious()) {
            assertEquals(expected[idx++], it.previous());
        }
        assertEquals(4, idx);
    }

    @Test
    void testIteratorAt() {
        MyDoublyLinkedList<Integer>.MyListIterator it = list.iteratorAt(3);
        assertTrue(it.hasNext());
        assertEquals(3, it.next());
    }

    @Test
    void testToString() {
        String str = list.toString().trim();
        assertTrue(str.contains("1"));
        assertTrue(str.contains("2"));
        assertTrue(str.contains("3"));
        assertTrue(str.contains("4"));
    }
}
