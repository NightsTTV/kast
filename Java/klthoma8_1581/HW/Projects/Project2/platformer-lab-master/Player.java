import java.util.ArrayList;
public class Player extends GameObject {
    private Physics physics;
    private boolean isJumping;
    private Pose currentPose;

    public Player(double x, double y) {
        super(x * Block.SIZE, y * Block.SIZE, Block.SIZE, Block.SIZE, "Assets/me-down.png");
        this.physics = new Physics(4);  // Initialize physics
        this.isJumping = false;
        this.currentPose = Pose.RIGHT;
        super.setImage(currentPose.getImage());
    }

    // Jump method (only works if player is on the ground)
    public void jump() {
        if (this.isJumping == false) {
            physics.jump();  // Call jump only if player is on the ground
            isJumping = true;
        }
    }

    public void land() {
        isJumping = false;
        physics.land();  // Call the landing logic when the player touches the ground
    }

    // Move method (updates the position of the player)
    public void move() {
        double dx = physics.getVelocityX();  // Horizontal movement based on velocityX
        double dy = physics.getVelocityY();  // Vertical movement based on velocityY
        super.move(dx, dy);  // Update the position using the inherited move method
    }

    // Update method (called each frame to update the player's physics and movement)
    public void update(ArrayList<Block> blocks) {
        physics.update(blocks, this);  // Apply gravity or other physics updates
        this.move();  // Move the player based on the updated physics

        // Check if the player landed after falling (this should be done in the game loop)
        if (this.getY() <= 0) {  // Assuming y = 0 is the ground level
            this.land();  // Call land() when the player reaches the ground level
        }
    }

    public void moveLeft() {
        physics.moveLeft();
        this.currentPose = Pose.LEFT;
    }

    public void moveRight() {
        physics.moveRight();
        this.currentPose = Pose.RIGHT;
    }

    // Make sure this method is present in the Player class
    public void setJumping(boolean isJumping) {
        this.isJumping = isJumping;
    }
    public void draw() {
        super.setImage(currentPose.getImage());
        super.draw();
    }
}
