import java.util.ArrayList;

public class RecursionUtils {

    public static int singleDigitMultiply(final int i1, final int i2) {
        if (i2 == 0) return 0;
        return i1 + singleDigitMultiply(i1, i2 - 1);
    }

    public static Student findMinimum(final Student[] studentArray, int numStudents) {
        return findMinimumHelper(studentArray, 0, numStudents);
    }

    private static Student findMinimumHelper(final Student[] arr, int index, int size) {
        if (index == size - 1) return arr[index];
        Student minRest = findMinimumHelper(arr, index + 1, size);
        return (arr[index].compareTo(minRest) < 0) ? arr[index] : minRest;
    }
}

