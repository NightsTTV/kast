public class ShapeDrawingTester {
    public static void main(String[] args) {
        // Create instances of different shapes
        Circle circle = new Circle("Red", 5.0);
        Square square = new Square("Blue", 4.0);
        Triangle triangle = new Triangle("Green", 3.0, 6.0);

        // Draws shapes
        circle.draw();
        square.draw();
        triangle.draw();
        circle.calculateArea();
        triangle.calculateArea();
        square.calculateArea();
    }
}