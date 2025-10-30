public class LogicalUtilTester{

	public static void main(String[] args){

		boolean implies; 

		boolean thereExists = LogicalUtil.thereExists(false, false, true);
		System.out.println(thereExists);

		boolean forAll = LogicalUtil.forAll(true, true, true);
		System.out.println(forAll);

		boolean majority = LogicalUtil.majority(true, true, false);
		System.out.println(majority);

		boolean minority = LogicalUtil.minority(false, false, false);
		System.out.println(minority);

		implies = LogicalUtil.implies(true, false);
		System.out.println(implies);

		implies = LogicalUtil.implies(true, true, false);
		System.out.println(implies);
	}
}