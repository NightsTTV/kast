class Triangle extends Shape {
    private double base; // Property for triangles
    private double height;

    // Constructor
    public Triangle(String color, double base, double height) {
        super(color); // Calls the base class constructor
        this.base = base;
        this.height = height;
    }

    public double getBase() {
        return base;
    }

    public void setBase(){
        this.base = base;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight() {
        this.height = height;
    }


    // Override draw method
    @Override
    public void draw() {
        System.out.println("Drawing a Triangle with color: " + getColor() + ", base: " + base + ", and height: " + height);
    }

    @Override
    public void calculateArea() {
        double area = (0.5*base*height);
        System.out.printf("The Triangle has an area of %.0f\n", area);
    }
}
