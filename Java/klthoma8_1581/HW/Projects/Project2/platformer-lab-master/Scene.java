import java.util.ArrayList;

public class Scene {
    private int rows;
    private int cols;
    private GameObject background;
    private ArrayList<Block> blocks;
    private ArrayList<Block> monsters;
    private Player player;
    private Exit exit;

    // Constructor
    public Scene(String[][] map) {
        this.rows = map.length;
        this.cols = map[0].length;
        int width = cols * 32;
        int height = rows * 32;
        this.background = new GameObject(0, 0, width, height, "Assets/background.png");
        this.blocks = new ArrayList<Block>();
        this.monsters = new ArrayList<Block>();

        // Set up canvas size and scale
        StdDraw.setCanvasSize(width, height);
        StdDraw.setXscale(0.0, width);
        StdDraw.setYscale(height, 0.0);

        // Display the map for verification
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                String tile = map[y][x];
                setTile(x, y, tile);
            }
        }
    }
      public Exit getExit() {
        return this.exit;
    }

    private void setTile(int x, int y, String tile) {
        if (tile.equals("#")) {
            Block block = new Block(x, y);
            this.blocks.add(block);
        } else if (tile.equals("@")) {
            this.player = new Player(x, y);
        }
        else if (tile.equals("A")) {
            FloorHazard spike = new FloorHazard(x,y);
            this.monsters.add(spike);
        }
        else if (tile.equals("V")) {
            CeilingHazard spike = new CeilingHazard(x,y);
            this.monsters.add(spike);
        }
        else if (tile.equals("!") ) {
            this.exit = new Exit(x, y);
        }
    }

    // Add the getPlayer() method to return the player instance
    public Player getPlayer() {
        return this.player;
    }


    // Draw method
    public void draw() {
        background.draw();
        for (Block block : this.blocks) {
            block.draw();
        }
        for (Block block : this.monsters) {
            block.draw();
        }
        exit.draw();
        player.draw();
    }

    // Player update Method
    public void update() {
        player.update(blocks); // Call update on the player object, which will call physics.update() and move()
    }

    // Render method
    public void render() {
        draw();
        StdDraw.show(10); // Shows the rendered scene
    }
    public boolean isPlayerDead() {
        for (Block hazard : monsters) {
            if (hazard.isTouching(player)) {
                return true;
            }
        }
        return false;
    }
   
}
