public class RelationalUtilTester{

	public static void main(String[] args){

		boolean isIncreasing = RelationalUtil.isIncreasing(1, 2, 3);
		System.out.println(isIncreasing);

		boolean isDecreasing = RelationalUtil.isDecreasing(3, 2, 1);
		System.out.println(isDecreasing);

		boolean isBetween = RelationalUtil.isBetween(-1, 0, 1);
		System.out.println(isBetween);

		boolean isPositive = RelationalUtil.isPositive(1);
		System.out.println(isPositive);

		boolean isNegative = RelationalUtil.isNegative(-1);
		System.out.println(isNegative);

		boolean overlaps1 = RelationalUtil.overlaps(0, 1, -1, 2);
		System.out.println(overlaps1);

		boolean overlaps2 = RelationalUtil.overlaps(0, 1, 2, 3);
		System.out.println(overlaps2);
	}
}