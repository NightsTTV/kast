public class Game {
    private World world;
    private boolean isOver;
    private int level;
    private Scene scene;
    private Controller controller;

    public Game(String filename) {
        this.isOver = false;
        this.level = 0;
        world = new World(filename);  // Pass the filename here
        String[][] map = world.getLevel(level);
        this.scene = new Scene(map);
        Player player = scene.getPlayer();
        this.controller = new Controller(player);
    }

    public void update() {
        scene.update();  // Update the scene, which updates the player
        controller.update();  // Handle input for jumping or other controls
        this.isOver = scene.isPlayerDead();
        if ( scene.getExit().isTouching( scene.getPlayer() ) ) {
                this.level++;
                if (this.level < world.getLength() ) {
                    String[][] map = world.getLevel(this.level);
                    this.scene = new Scene(map);
                    this.controller = new Controller(this.scene.getPlayer() );
                }
                else {
                    this.isOver = true;
                 }
            }
    }

    public void render() {
        scene.draw();  // Call render on the scene
        StdDraw.show(10);
    }

    public static void main(String[] args) {
        Game game = new Game("worldData.txt");
        while (!game.isOver) {
            game.update();
            game.render();
        }
    }
}
