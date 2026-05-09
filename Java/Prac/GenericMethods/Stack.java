public class Stack<E> {
    private List<E> stackList;

    public Stack() {
        stackList = new List<E>("stack");
    }

    public void push(E object) {
        stackList.insertAtFront(object);
    }

    public E pop() throws NoSuchElementException {
        return stackList.removeFromFront();
    }

    public boolean isEmpty() {
        return stackList.isEmpty();
    }

    public void print() {
        stackList.print();
    }
}