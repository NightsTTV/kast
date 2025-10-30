import java.util.ArrayList;

public class Physics {
    private int speed;
    private double gravity;
    private double terminal;
    private double velocityX;
    private double velocityY;  // Vertical velocity
    private double friction;
    private boolean isOnGround;  // New variable to track if the player is on the ground

    public Physics(int speed) {
        this.speed = speed;
        this.gravity = 0.3;
        this.terminal = 8.0;
        this.velocityX = 0.0;
        this.velocityY = 0.0;
        this.isOnGround = true;  // Start on the ground
        this.friction = 0.8;
    }

    // Apply gravity to velocityY
    public void applyGravity() {
        if (!isOnGround && velocityY < terminal) {
            velocityY += gravity;  // Increase downward speed
        }
    }
    public void applyFriction() {
        velocityX *= friction;
    }
    // Jump method (only works if player is on the ground)
    public void jump() {
        if (isOnGround) {
            velocityY = -speed * 2;  // Jump with initial upward speed
            isOnGround = false;  // Player is now in the air
        }
    }

    // Handle landing when the player reaches the ground
    public void land() {
        if (velocityY <= 0) {
            velocityY = 0;  // Stop falling
            isOnGround = true;  // Player is back on the ground
        }
    }

    // applies gravity and updates movement
    public void update(ArrayList<Block> blocks, Player player) {
        applyGravity();
        checkCollisions(blocks, player);
        applyFriction();
    }

    // (landing)
    public void checkCollisionsFloor(Block block, Player player) {
        if (player.getY() < block.getY() && velocityY > 0) {
            if (block.isTouchingX(player, 0.5)) {
                this.velocityY = 0;  // Stop vertical movement
                player.setJumping(false);  // Stop jumping
                isOnGround = true;  // Player is now on the ground
            }
        }
    }

    // (when jumping)
    public void checkCollisionsCeiling(Block block, Player player) {
        if (player.getY() > block.getY() && velocityY < 0) {
            this.velocityY *= -0.5;  // Revert vertical velocity (bounce or stop)
            player.setJumping(true);  // Continue jumping
        }
    }
    public void checkCollisionsRight(Block block, Player player) {
        if (player.getX() < block.getX() && velocityX > 0) {
            if (block.isTouchingY(player, 0.5)) {
                this.velocityX *= -1;
            }
        }
    }
    public void checkCollisionsLeft(Block block, Player player) {
        if (player.getX() > block.getX() && velocityX < 0) {
            if (block.isTouchingY(player, 0.5)) {
                this.velocityX *= -1;
            }
        }
    }
    // all collisions
    public void checkCollisions(ArrayList<Block> blocks, Player player) {
        for (Block block : blocks) {
            if (block.isTouching(player)) {
                checkCollisionsFloor(block, player);  // Check if on the ground
                checkCollisionsCeiling(block, player);  // Check if hitting ceiling
                checkCollisionsRight(block, player);
                checkCollisionsLeft(block,player);
            }
        }
    }

    public double getVelocityX() {
        return this.velocityX;
    }

    public double getVelocityY() {
        return this.velocityY;
    }

    // check if the player is on the ground
    public boolean isOnGround() {
        return isOnGround;
    }

    // Movement controls (left and right)
    public void moveLeft() {
        if (velocityX > -speed) {
            velocityX--;  // Decrease X velocity for left movement
        }
    }

    public void moveRight() {
        if (velocityX < speed) {
            velocityX++;  // Increase X velocity for right movement
        }
    }
}
