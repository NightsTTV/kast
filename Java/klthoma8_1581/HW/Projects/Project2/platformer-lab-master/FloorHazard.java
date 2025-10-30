public class FloorHazard extends Block {

    public FloorHazard(double x, double y) {
        super(x, y, "Assets/tile-spikes-floor.png");
    }

    // Check if the player is touching the hazard
    public boolean isTouching(GameObject player) {
        return super.isTouchingX(player, 0.75) && this.isTouchingY(player);
    }

    // Check if the player is within the Y bounds of the floor hazard (below the player)
    public boolean isTouchingY(GameObject player) {
        // Check if the player's feet (bottom) are touching the floor hazard
        return this.getY() + this.getHeight() / 2 <= player.getY() + player.getHeight()
                && player.getY() <= this.getY() + this.getHeight();
    }
}
