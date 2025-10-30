class Circle extends Shape {
    private double radius; // Property for circles

    // Constructor
    public Circle(String color, double radius) {
        super(color); // Call base class constructor
        this.radius = radius;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius() {
        this.radius = radius;
    }


    @Override
    public void draw() {
        System.out.println("Drawing a Circle with color: " + getColor() + " and radius: " + radius);
    }
    @Override
    public void calculateArea() {
        double area = (Math.PI * Math.pow(radius, 2));
        System.out.printf("The Triangle has an area of %.0f\n", area);
    }
}
