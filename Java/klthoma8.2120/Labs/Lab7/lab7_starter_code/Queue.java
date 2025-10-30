public interface Queue<T> {

    public int size();

    public boolean isEmpty();
	
    // this is the "insert" operation
    // in a queue, new items are inserted at the "back" of the queue
	public void enqueue(T element);

    // this is the "delete and return" operation
    // in a queue, the "next" items are deleted from the "front" of the queue
	public T dequeue();
	
    // this is the "return but don't delete" operation
    // in a queue, the "next" items are returned from the "front" of the queue
	public T peek();
	
} // end class Queue
