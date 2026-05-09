package app;
import java.awt.*;
import javax.swing.*;

public class Dashboard extends JFrame {

    public Dashboard() {
        // INITIALIZE
        this.setTitle("Dashboard");
        this.setSize(1920, 1080);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

        // ROOT (the default background)
        JPanel root = new JPanel(new BorderLayout()); // root is JPanel w/ BorderLayout
        root.setBackground(Color.GRAY); // root is also GRAY
        this.setContentPane(root); // sets Frame's Content Pane to root 

        //Topbar (dark)
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setPreferredSize(new java.awt.Dimension(60,60));
        topBar.setBackground(new Color(12,45,59)); // custom rgb
        topBar.setOpaque(true); // must add this if you want the changes to background to happen

        // Top Header
        JPanel topHeader = new JPanel(new BorderLayout());
        topHeader.setBackground(new Color(12,45, 59));
        topHeader.setOpaque(true);

        JButton allUsersBtn = new JButton("AllUsers");
        topHeader.add(allUsersBtn,BorderLayout.WEST);

        // Users list (stacked)
        JPanel usersLists = new JPanel();
        usersLists.setLayout(new BoxLayout(usersLists, BoxLayout.X_AXIS));
        usersLists.setBackground(new java.awt.Color(16,55,72));

        topBar.add(topHeader, BorderLayout.NORTH);
        //topBar.add(usersScroll, BorderLayout.CENTER);
        

        root.add(topBar, BorderLayout.NORTH);
        this.setVisible(true);

    }
    public static void main(String[] args){
        SwingUtilities.invokeLater(() -> {
             new Dashboard();
        });
    }
}