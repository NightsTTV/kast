public class CookingTesting {

	public static void main(String[] args){

		double teaToTab = Cooking.teaspoonsToTablespoons(1.0);
		System.out.println(teaToTab);

		double tabToTea = Cooking.tablespoonsToTeaspoons(1.0);
		System.out.println(tabToTea);

		double tabToCup = Cooking.tablespoonsToCups(1.0);
		System.out.println(tabToCup);

		double cupToTab = Cooking.cupsToTablespoons(1.0);
		System.out.println(cupToTab);

		double ouncesToCups = Cooking.ouncesToCups(1.0);
		System.out.println(ouncesToCups);

		double cupsToOunces = Cooking.cupsToOunces(1.0);
		System.out.println(cupsToOunces);

		double cupsToPints = Cooking.cupsToPints(1.0);
		System.out.println(cupsToPints);

		double pintsToCups = Cooking.pintsToCups(1.0);
		System.out.println(pintsToCups);
	}
}