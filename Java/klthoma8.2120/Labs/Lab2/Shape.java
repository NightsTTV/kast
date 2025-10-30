abstract class Shape {
    /* Shapes have unique properites aswell as commmon ones such as shapes. 
    When calling the draw method it has to be overriden in order to display the proper properties*/

    private String color; // varaible for shapes

    // Constructor
    public Shape(String color) {
        this.color = color;
    }

    // Get color
    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    // abstract methods
    // draw shape 
    public abstract void draw();

    public abstract void calculateArea();
}

