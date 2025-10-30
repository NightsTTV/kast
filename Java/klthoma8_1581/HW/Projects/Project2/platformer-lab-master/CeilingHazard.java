public class CeilingHazard extends Block {

    public CeilingHazard(double x, double y) {
        super(x, y, "Assets/tile-spikes-ceiling.png");
    }

    // Check if the player is touching the hazard
    public boolean isTouching(GameObject player) {
        return super.isTouchingX(player, 0.75) && this.isTouchingY(player);
    }

    // Check if the player is within the Y bounds of the ceiling hazard (above the player)
    public boolean isTouchingY(GameObject player) {
        // Check if the player is above the ceiling hazard (so it can be a hazard when falling down)
        return this.getY() <= player.getY() + player.getHeight()
                && player.getY() <= this.getY() + this.getHeight() / 2;
    }
}
