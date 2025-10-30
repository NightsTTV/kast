import java.util.LinkedList;

public class LinkedListStack <T> implements Stack<T> {

	private LinkedList<T> elements;
	
	public LinkedListStack() {
		elements = new LinkedList<>();
	}
	
    @Override
	public int size() {
		return elements.size();
	}
	
    @Override
	public boolean isEmpty() {
		return elements.isEmpty();
	}
	
    // this is the "insert" operation
    // in a stack, new items are inserted at the "top" of the stack
    @Override
	public void push(T item) {
		elements.push(item);
	}
	
    // this is the "return but don't delete" operation
    // in a stack, the "next" items are returned from the "top" of the stack
    @Override
	public T peek() {
		if (isEmpty()) {
			throw new IllegalStateException("Stack is empty");
		}
		return elements.peek();
	}
	
    // this is the "delete and return" operation
    // in a stack, the "next" items are deleted from the "top" of the stack 
    @Override
	public T pop() {
		if (isEmpty()){
			throw new IllegalStateException("Stack is empty.");
		}
		return elements.pop();
	}
	
} // end class LinkedListStack
