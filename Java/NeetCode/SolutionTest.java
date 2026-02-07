import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class SolutionTest {
	
	@Test
	public void testTwoSum_StandardCase() {
		Solution sol = new Solution();

		int[] nums = {3, 4, 5, 6};
		int target = 7;
		int[] expected = {0, 1};

		int[] actual = sol.twoSum(nums, target);

		assertArrayEquals(expected, actual, "Should return indices 0 and 1");
	}

	@Test
	public void testTwoSum_SameValues() {
		Solution sol = new Solution();

		int[] nums = {5, 5};
		int target = 10;
		int[] expected = {0, 1};

		assertArrayEquals(expected, sol.twoSum(nums, target));
	}
}