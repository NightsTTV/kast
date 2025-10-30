public interface Stack<T> {

	public int size();

	public boolean isEmpty();
	
    // this is the "insert" operation
    // in a stack, new items are inserted at the "top" of the stack
	public void push(T item);
	
    // this is the "return but don't delete" operation
    // in a stack, the "next" items are returned from the "top" of the stack
	public T peek(); 
	
    // this is the "delete and return" operation
    // in a stack, the "next" items are deleted from the "top" of the stack 
	public T pop();
	
}
