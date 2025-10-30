public class GameObject {
    private double x;
    private double y;
    private double width;
    private double height;
    private String imagePath;

    // Constructor
    public GameObject(double x, double y, double width, double height, String imagePath) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.imagePath = imagePath;
    }

    // Getters
    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    // Setters
    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    // Draw method (Assuming StdDraw handles rendering)
    public void draw() {
        StdDraw.picture(x, y, imagePath);
    }
    public void move(double dx, double dy) {
        this.x += dx;
        this.y += dy;
    }
}
