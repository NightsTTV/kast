public class Block extends GameObject {
    public static final int SIZE = 32;

    // Constructor
    public Block(double x, double y) {
        super(x * SIZE, y * SIZE, SIZE, SIZE, "Assets/tile-brick.png");
    }
    public boolean isTouchingX(GameObject GameObject, double ratio) {
        double overlap = this.getWidth() * ratio;
        return (Math.abs(this.getX()-GameObject.getX() ) < overlap);
    }
    public boolean isTouchingY(GameObject GameObject, double ratio) {
        double overlap = this.getWidth() * ratio;
        return (Math.abs(this.getY()-GameObject.getY() ) < overlap);
    }
    public boolean isTouching(GameObject GameObject) {
        return isTouchingY(GameObject, 1.0) && isTouchingX(GameObject, 0.75);
    }
    public Block(double x, double y, String image) {
        super( x*SIZE, y*SIZE, SIZE, SIZE, image);
    }
}
