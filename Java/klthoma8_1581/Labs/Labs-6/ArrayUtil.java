public class ArrayUtil{
	public static void reverse(String[] array){
		int length = array.length;
		for(int i = 0; i < (length/2); i++){
			String temp = array[i];
			array[i] = array[length - i - 1];
			array[length - i - 1] = temp;
		}
	}
	public static String[] resize(String[] array){
		String[] newArray = new String[array.length * 2];
		for (int i = 0; i < array.length; i++) {
			newArray[i] = array[i];
		}
		return newArray;
	}
	public static String[] add(String element, String[] array) {
    if (array == null) {
        return new String[]{element};
    }
    String[] newArray = new String[array.length + 1];
    for (int i = 0; i < array.length; i++) {
        newArray[i] = array[i];
    }
    newArray[array.length] = element;
    return newArray;
	}
	public static boolean contains(String element, String[] array){
		for (int i = 0; i < array.length; i++){
			if (array[i].equals(element)){
				return true;
			}
		}
		return false;
	}
	public static int findMinValue(int[] array) {
        if (array == null || array.length == 0) {
            throw new IllegalArgumentException("Array must not be null or empty");
        }
        
        int minValue = array[0];
        for (int i = 1; i < array.length; i++) {
            if (array[i] < minValue) {
                minValue = array[i];
            }
        }
        return minValue;
    }

    // Returns the maximum value from the array
    public static int findMaxValue(int[] array) {
        if (array == null || array.length == 0) {
            throw new IllegalArgumentException("Array must not be null or empty");
        }
        
        int maxValue = array[0];
        for (int i = 1; i < array.length; i++) {
            if (array[i] > maxValue) {
                maxValue = array[i];
            }
        }
        return maxValue;
    }
}
