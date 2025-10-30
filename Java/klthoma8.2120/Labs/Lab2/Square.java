class Square extends Shape {
    private double sideLength; // Property for squares

    // Constructor
    public Square(String color, double sideLength) {
        super(color); // Call base class constructor
        this.sideLength = sideLength;
    }

    public double getSidelength() {
        return sideLength;
    }

    public void setSidelength() {
        this.sideLength = sideLength;
    }
    @Override
    public void draw() {
        System.out.println("Drawing a Square with color: " + getColor() + " and side length: " + sideLength);
    }
    @Override
    public void calculateArea() {
        double area = (sideLength*sideLength);
        System.out.printf("The Triangle has an area of %.0f\n", area);
    }
}
