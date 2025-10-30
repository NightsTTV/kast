
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.AfterClass;
import static org.junit.Assert.*;//Comment this out and uncomment
//import junit.framework.* and extends TestCase and things will also work

//import junit.framework.*;//Makes TestCase and Asserts work
import java.io.*; //IO Library for testing System.out messages

public class TestLinkedListQueue //extends TestCase
{

	private Queue<String> queue;
	//private final ByteArrayOutputStream output = new ByteArrayOutputStream();
	//private final PrintStream systemOut = System.out;
	
	@Before
	public void setUp()
	{
		queue = new LinkedListQueue<String>();
		//Output Streams for testing
		//System.setOut(new PrintStream(output));
	}
	
	@Test
	public void testEnqueueAndPeek()
	{
		queue.enqueue("This");
		queue.enqueue("is");
		queue.enqueue("a");
		queue.enqueue("queue");

	    String result;
		result = queue.peek();
		assertEquals("This", result);

		result = queue.peek();
		assertEquals("This", result);

		result = queue.peek();
		assertEquals("This", result);
	}

	@Test
	public void testEnqueueAndDequeue()
	{
		queue.enqueue("This");
		queue.enqueue("is");
		queue.enqueue("a");
		queue.enqueue("queue");

		String result;
		result = queue.dequeue();
		assertEquals("This", result);

		result = queue.dequeue();
		assertEquals("is", result);

		result = queue.dequeue();
		assertEquals("a", result);

		result = queue.dequeue();
		assertEquals("queue", result);
	}

	@After
	public void restoreStreams() {
    	//System.setOut(systemOut);
	}
	
}
