import java.util.ArrayList;

public class ArrayListQueue<T> implements Queue<T> {
	
	private ArrayList<T> elements;
	
	public ArrayListQueue() {
		elements = new ArrayList<>();
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
    // in a queue, new items are inserted at the "back" of the queue
    @Override
	public void enqueue(T item) {
		elements.add(item);
	}

    // this is the "return but don't delete" operation
    // in a queue, the "next" items are returned from the "front" of the queue
    @Override
	public T peek() {
		if (isEmpty()) {
			throw new IllegalStateException("Queue is empty");
		}
		return elements.get(0);
	}

    // this is the "delete and return" operation
    // in a queue, the "next" items are deleted from the "front" of the queue
    @Override
	public T dequeue() {
		if (isEmpty()) {
			throw new IllegalStateException("Queue is empty");
		}
		return elements.remove(0);
	}
	

} // end class ArrayListQueue
