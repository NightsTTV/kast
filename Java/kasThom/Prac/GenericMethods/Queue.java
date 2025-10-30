public class Queue<E> {
    private List<E> queueList;

    public Queue() {
        queueList = new List<E>("queue");
    }

    public void enqueue(E object) {
        queueList.insertAtBack(object);
    }

    public E dequeue() throws NoSuchElementException {
        return queueList.removeFromFront();
    }

    public boolean isEmpty() {
        return queueList.isEmpty();
    }

    public void print() {
        queueList.print();
    }
}