import java.util.NoSuchElementException;

public class List<E> {
    // nested node class
    private static class ListNode<E> {
        E data;
        ListNode<E> next;

        ListNode(E data) {
            this(data, null);
        }

        ListNode(E data, ListNode<E> next) {
            this.data = data;
            this.next = next;
        }
    }

    private ListNode<E> firstNode;
    private ListNode<E> lastNode;
    private final String name;  // used in printing

    // constructors
    public List() {
        this("list");
    }

    public List(String name) {
        this.name = name;
        firstNode = lastNode = null;
    }

    // insert at front
    public void insertAtFront(E item) {
        if (isEmpty()) {
            firstNode = lastNode = new ListNode<>(item);
        } else {
            firstNode = new ListNode<>(item, firstNode);
        }
    }

    // insert at back
    public void insertAtBack(E item) {
        if (isEmpty()) {
            firstNode = lastNode = new ListNode<>(item);
        } else {
            lastNode.next = new ListNode<>(item);
            lastNode = lastNode.next;
        }
    }

    // remove from front
    public E removeFromFront() {
        if (isEmpty()) {
            throw new NoSuchElementException(name + " is empty");
        }

        E removed = firstNode.data;
        if (firstNode == lastNode) {         // only one element
            firstNode = lastNode = null;
        } else {
            firstNode = firstNode.next;
        }
        return removed;
    }

    // remove from back
    public E removeFromBack() {
        if (isEmpty()) {
            throw new NoSuchElementException(name + " is empty");
        }

        E removed = lastNode.data;
        if (firstNode == lastNode) {         // only one element
            firstNode = lastNode = null;
        } else {
            // walk to the node just before lastNode
            ListNode<E> current = firstNode;
            while (current.next != lastNode) {
                current = current.next;
            }
            lastNode = current;
            lastNode.next = null;
        }
        return removed;
    }

    // is-empty check
    public boolean isEmpty() {
        return firstNode == null;
    }

    // print list contents
    public void print() {
        if (isEmpty()) {
            System.out.printf("Empty %s%n", name);
            return;
        }

        System.out.printf("The %s is: ", name);
        for (ListNode<E> current = firstNode; current != null; current = current.next) {
            System.out.print(current.data + " ");
        }
        System.out.println();
    }
}
