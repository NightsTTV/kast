public class Point2D{
	private double x;
	private double y;

	public Point2D(double x, double y){
		this.x = x;
		this.y = y;
	}
	public double getX() {
		return x;
	}
	public double getY() {
		return y;
	}

	public void moveTo(double x, double y) {
		this.x = x;
		this.y = y;
	}
	public void moveBy(double dx, double dy) {
		this.x += dx;
		this.y += dy;
	}
	public double distance(Point2D other) {
		double dx = this.x - other.getX();
		double dy = this.y - other.getY();
		return Math.sqrt(dx * dx + dy * dy);
	}

	@Override
	public String toString(){
		return String.format("(%s,%s)", x, y);
	}
}
