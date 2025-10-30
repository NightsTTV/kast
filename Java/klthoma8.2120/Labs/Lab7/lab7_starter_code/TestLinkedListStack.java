import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import java.io.*;

public class TestLinkedListStack
{
	private Stack<String> stack;
	
	@Before
	public void setUp()
	{
		stack = new LinkedListStack<String>();
	}
	
	@Test
	public void testPush()
	{
		stack.push("This");
        AssertEquals( "This", stack.peek());
		stack.push("is");
        AssertEquals("is", stack.peek());
		stack.push("a");
        AssertEquals("a", stack.peek());
		stack.push("stack");
        AssertEquals("stack", stack.peek());
	}

	
	@Test
	public void testPop()
	{
		stack.push("This");
		stack.push("is");
		stack.push("a");
		stack.push("stack");

	    String result;
		result = stack.pop();
		assertEquals("stack", result);

		result = stack.pop();
		assertEquals("a", result);

		result = stack.pop();
		assertEquals("stack", result);

	}
	
	@Test
	public void testPeek()
	{
		stack.push("This");
		stack.push("is");
		stack.push("a");
		stack.push("stack");

		String result;
		result = stack.peek();
		assertEquals("stack", result);

        stack.pop();

		result = stack.peek();
		assertEquals("a", result);
	}
	
	@After
	public void restoreStreams() {
	}
}
