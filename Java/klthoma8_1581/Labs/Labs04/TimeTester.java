public class TimeTester {

	public static void main(String[] args) {
		double secondsToMinutes = Time.secondsToMinutes(1);
		System.out.println(secondsToMinutes);
	
		double secondsToHours = Time.secondsToHours(1);
		System.out.println(secondsToHours);

		double secondsToDays = Time.secondsToDays(1);
		System.out.println(secondsToDays);

		double secondsToYears = Time.secondsToYears(1);
		System.out.println(secondsToYears);

		double minutesToSeconds = Time.minutesToSeconds(1);
		System.out.println(minutesToSeconds);

		double hoursToSeconds = Time.hoursToSeconds(1.0);
		System.out.println(hoursToSeconds);

		double daysToSeconds = Time.daysToSeconds(1.0);
		System.out.println(daysToSeconds);

		double yearsToSeconds = Time.yearsToSeconds(1.0);
		System.out.println(yearsToSeconds);
	}
}