public class GeometryTester {
	// Run Debug
	public static void main(String[] args) {

		double rectangleArea = Geometry.getAreaRectangle(1,1);
		System.out.println(rectangleArea);

		double circleArea = Geometry.getAreaCircle(1);
		System.out.println(circleArea);

		double triangleArea = Geometry.getAreaTriangle(1,1);
		System.out.println(triangleArea);

		double perimeterRectangle = Geometry.getPerimeterRectangle(1,1);
		System.out.println(perimeterRectangle);

		double perimeterCircle = Geometry.getPerimeterCircle(1);
		System.out.println(perimeterCircle);

		double perimeterTriangle = Geometry.getPerimeterTriangle(1,2,3);
		System.out.println(perimeterTriangle);
	}
}