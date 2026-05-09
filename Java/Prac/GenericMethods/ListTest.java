import java.util.NoSuchElementException;

public class ListTest {
    public static void main(String[] args) {
        List<Integer> list = new List<>();

        // build up the list
        list.insertAtFront(-2);
        list.print();            // The list is: -2
        list.insertAtFront(-1);
        list.print();            // The list is: -1 -2
        list.insertAtBack(0);
        list.print();            // The list is: -1 -2 0
        list.insertAtFront(1);
        list.print();            // The list is: 1 -1 -2 0
        list.insertAtBack(2);
        list.print();            // The list is: 1 -1 -2 0 2

        // tear it down
        try {
            int removed = list.removeFromFront();
            System.out.printf("%n%d removed%n", removed);
            list.print();

            removed = list.removeFromFront();
            System.out.printf("%n%d removed%n", removed);
            list.print();

            removed = list.removeFromBack();
            System.out.printf("%n%d removed%n", removed);
            list.print();

            removed = list.removeFromBack();
            System.out.printf("%n%d removed%n", removed);
            list.print();
        } catch (NoSuchElementException ex) {
            ex.printStackTrace();
        }
    }
}
