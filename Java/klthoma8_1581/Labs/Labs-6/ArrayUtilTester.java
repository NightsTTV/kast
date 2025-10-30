import java.util.Arrays;

public class ArrayUtilTester{
	public static void main(String[] args){
		String[] result3 = ArrayUtil.add("a", new String[]{null});
		System.out.println(Arrays.toString(result3)); // Expected: ["a"]
	}
}