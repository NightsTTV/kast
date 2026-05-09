package uno;
import java.awt.*;
import javax.swing.*;

public class GameBoard extends JFrame {

    public GameBoard () {

    // INITIALIZE
    this.setTitle("UNO with Friends");
    this.setSize(1920,1080);
    this.pack();
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setLocationRelativeTo(null);

    // ROOT (the default general background)
    JPanel root = new JPanel(new BorderLayout());
    // This ensures the image is found even if you move the project or export to JAR
    try {
        // The "/" starts the search from the root of your 'src' folder
        java.net.URL imgURL = getClass().getResource("/uno/boardImg.png");
    
        if (imgURL != null) {
            ImageIcon img = new ImageIcon(imgURL);
            JLabel imgLabel = new JLabel(img);
            // Using BorderLayout.CENTER ensures it fills the space properly
            root.add(imgLabel, BorderLayout.CENTER); 
        } else {
            // This will tell you if the file name is misspelled or in the wrong folder
            System.err.println("Error: Could not find boardImg.png at the specified path.");
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    
    this.add(root); // add root to GameBoard
    this.setVisible(true);
    }


    public static void main(String[] args) {
        // It is best practice to run Swing apps on the Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            new GameBoard();
        });
    }

}
