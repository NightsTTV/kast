public class GameObject {
    private double x;
    private double y;
    private double width;
    private double height;
    private String image;

    // Constructor
    public GameObject(double x, double y, double width, double height, String image) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.image = image;
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
        double screenX = x + width/2;
        double screenY = y + height/2;
        StdDraw.picture(screenX, screenY, image, width, height);
    }
    public void move(double dx, double dy) {
        this.x += dx;
        this.y += dy;
    }
    public void setImage(String image) {
        this.image = image;
    }
    public int getWidth() {
        return (int) this.width;
    }
    public int getHeight() {
        return (int) this.height;
    }
}
