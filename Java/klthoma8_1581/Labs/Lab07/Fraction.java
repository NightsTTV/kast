public class Fraction{
	private int numerator;
	private int denominator;

	public Fraction(int numerator, int denominator) {
		this.numerator = Math.abs(numerator);
		if (numerator * denominator < 0){
			this.numerator *= -1;
		}
		this.denominator = Math.abs(denominator);
	}

	public int getNumerator() {
		return numerator;
	}
	public int getDenominator(){
		return denominator;
	}

	public Fraction add(Fraction other) {
		//the numerator of the sum is numerator1 * denominator2 + numerator2*denominator1
		int numerator = (this.getNumerator() * other.getDenominator()) + (other.getNumerator() * this.getDenominator());
		int denominator = this.getDenominator() * other.getDenominator();

		//create a new simplified fraction
		Fraction sum = this.simplify(numerator, denominator);
		return sum;
	}
	public Fraction simplify(int numerator, int denominator) {
			for (int i = denominator; i>0; i--) {
			if (numerator % i  == 0 && denominator % i == 0){
				numerator /= i;
				denominator /= i;
			}
		}
		return new Fraction(numerator, denominator);
	}
	public Fraction subtract(Fraction other) {
		int numerator = (this.getNumerator() * other.getDenominator()) - (other.getNumerator() * this.getDenominator());
		int denominator = this.getDenominator() * other.getDenominator();

		Fraction sum = this.simplify(numerator, denominator);
		return sum;
	}
	public Fraction multiply(Fraction other) {
		int numerator = this.getNumerator() * other.getNumerator();
		int denominator = this.getDenominator() * other.getDenominator();

		Fraction product = this.simplify(numerator, denominator);
		return product;
	}
	public Fraction divide(Fraction other) {
		int numerator = this.getNumerator() * other.getDenominator();
		int denominator = this.getDenominator() * other.getNumerator();

		Fraction multiply = this.simplify(numerator, denominator);
		return multiply;
	}
	@Override
	public String toString(){
		return String.format("(%d/%d)", numerator, denominator);
	}
}