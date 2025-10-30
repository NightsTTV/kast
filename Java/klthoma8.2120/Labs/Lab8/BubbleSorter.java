import java.util.List;
import java.util.Comparator;
import java.util.Collections;

public class BubbleSorter {

    /**
     * Sorts the list in natural order (elements must implement Comparable).
     */
    public static <T> void sort(List<T> theList) {
        // Delegate to the comparator-based sort using natural ordering
        sort(theList, new Comparator<T>() {
            @SuppressWarnings("unchecked")
            public int compare(T o1, T o2) {
                return ((Comparable<? super T>) o1).compareTo(o2);
            }
        });
    }

    /**
     * Sorts the list using the provided comparator.
     */
    public static <T> void sort(List<T> theList, Comparator<T> comparator) {
        int n = theList.size();
        boolean swapped;
        do {
            swapped = false;
            for (int i = 1; i < n; i++) {
                // If the previous element is greater than the current, swap
                if (comparator.compare(theList.get(i - 1), theList.get(i)) > 0) {
                    Collections.swap(theList, i - 1, i);
                    swapped = true;
                }
            }
            // After each pass, the largest element is bubbled to the end
            n--;
        } while (swapped);
    }
}
