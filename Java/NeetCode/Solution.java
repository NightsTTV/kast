import java.util.*;

class twoSum {
    public int[] twoSum(int[] nums, int target) {
        HashMap<Integer, Integer> map = new HashMap<>(); // new hashmap
        for (int i = 0; i < nums.length; i++){ // iterate through each instance of the array
            int cur = nums[i]; // give the current value at index i
            int complement = target - cur; // calculate the complement
            if (map.containsKey(complement)) { // if our map already has the complement
                return new int[] { map.get(complement), i }; // return its location
            }
            // else 
            map.put(cur, i); // add current val into the map
        }
        return null;
    }

    public static void main(String[] args) {
    twoSum sol = new twoSum();
    int[] nums = {3, 4, 5, 6};
    int target = 10;
    int[] result = sol.twoSum(nums, target);
    
    System.out.println("Indices: [" + result[0] + ", " + result[1] + "]");
}
}
