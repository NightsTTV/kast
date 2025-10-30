public class Controller {
    private Player player;

    public Controller(Player player) {
        this.player = player;
    }

    public void keyboard() {
        if (StdDraw.isKeyPressed(38) || StdDraw.isKeyPressed(32)) {
            player.jump();
        }
        if (StdDraw.isKeyPressed(37) ){
            player.moveLeft();
        }
        if (StdDraw.isKeyPressed(39) ) {
            player.moveRight();
        }
    }

    public void update() {
        keyboard();
    }

    public Player getPlayer() {
        return this.player;
    }
}
