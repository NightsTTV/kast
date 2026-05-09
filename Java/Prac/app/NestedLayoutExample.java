/*package app;
import javax.swing.*;
import java.awt.*;

public class NestedLayoutExample {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Nested Layout Strategy");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 400);

        // 1. Main (Parent) Panel ---> BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout());

        // 2. Create the North "Array" Contatiner
        JPanel northArrayPanel = new JPanel();
        northArrayPanel.setLayout(new BoxLayout(northArrayPanel, BoxLayout.X_AXIS));

        // 3. Add individual "NWSE" Boxes to the North Array
        northArrayPanel.add(createSubBox("Box A", Color.RED));
        northArrayPanel.add(createSubBox("Box B", Color.GREEN));
        northArrayPanel.add(createSubBox("Box C", Color.BLUE));
        northArrayPanel.add(createSubBox("Box D", Color.BLACK));
        northArrayPanel.add(createSubBox("Box f", Color.ORANGE));
        northArrayPanel.add(createSubBox("Box C", Color.BLUE));
        northArrayPanel.add(createSubBox("Box C", Color.BLUE));
        
        // 4. Assemble Main Layout
        mainPanel.add(northArrayPanel, BorderLayout.NORTH);
        mainPanel.add(new JLabel("Main Center"), BorderLayout.CENTER);
        mainPanel.add(new JButton("WEST"), BorderLayout.WEST);
        mainPanel.add(new JButton("EAST"), BorderLayout.EAST);
        mainPanel.add(new JButton("SOUTH"), BorderLayout.SOUTH);

        frame.add(mainPanel);
        frame.setVisible(true);
    }

    public static JPanel createSubBox(String title, Color centerColor) {
        JPanel box = new JPanel(new BorderLayout());
        box.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // Inner componens for the box
        JLabel label = new JLabel(title, SwingConstants.CENTER);
        label.setOpaque(true);
        label.setBackground(centerColor);

        box.add(label, BorderLayout.CENTER);
        box.add(new JLabel("n"), BorderLayout.NORTH);
        box.add(new JLabel("s"), BorderLayout.SOUTH);
        box.add(new JLabel("w"), BorderLayout.WEST);
        box.add(new JLabel("e"), BorderLayout.EAST);

        return box;
    }
}
*/
package app;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class NestedLayoutExample {
    // CardLayout and mainPanel are static here to work with your main method structure
    private static CardLayout cardLayout = new CardLayout();
    private static JPanel centerCardPanel = new JPanel(cardLayout);

    public static void main(String[] args) {
        JFrame frame = new JFrame("Utility Dashboard");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 500);

        // 1. Main (Parent) Panel
        JPanel mainPanel = new JPanel(new BorderLayout());

        // 2. The North Container (Our Navigation Bar)
        JPanel northArrayPanel = new JPanel();
        northArrayPanel.setLayout(new BoxLayout(northArrayPanel, BoxLayout.X_AXIS));

        // Create the Action Listener for the buttons
        ActionListener navListener = e -> {
            String cmd = e.getActionCommand();
            cardLayout.show(centerCardPanel, cmd);
        };

        // 3. Add individual utility boxes to the North Array
        northArrayPanel.add(createUtilityBox("Dashboard", Color.CYAN, navListener));
        northArrayPanel.add(createUtilityBox("Menu", Color.PINK, navListener));
        northArrayPanel.add(createUtilityBox("Rules", Color.YELLOW, navListener));
        northArrayPanel.add(createUtilityBox("Login", Color.ORANGE, navListener));

        // 4. Set up the Center Card Panel (The "Views")
        centerCardPanel.add(createView("DASHBOARD CONTENT"), "Dashboard");
        centerCardPanel.add(createView("MENU SELECTION"), "Menu");
        centerCardPanel.add(createView("GAME RULES"), "Rules");
        centerCardPanel.add(createView("LOGIN FORM"), "Login");

        // 5. Assemble
        mainPanel.add(northArrayPanel, BorderLayout.NORTH);
        mainPanel.add(centerCardPanel, BorderLayout.CENTER);
        
        // Placeholders for your other areas
        mainPanel.add(new JButton("Quick Tools"), BorderLayout.WEST);
        mainPanel.add(new JLabel(" Notifications "), BorderLayout.EAST);
        mainPanel.add(new JLabel("Status Bar: System Ready", SwingConstants.CENTER), BorderLayout.SOUTH);

        frame.add(mainPanel);
        frame.setVisible(true);
    }

    // Modified helper to create boxes containing buttons
    public static JPanel createUtilityBox(String title, Color btnColor, ActionListener listener) {
        JPanel box = new JPanel(new BorderLayout());
        box.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));

        JButton btn = new JButton(title);
        btn.setBackground(btnColor);
        btn.setFocusable(false);
        btn.addActionListener(listener); // Links button to the view switcher

        box.add(btn, BorderLayout.CENTER);
        
        // Small directional labels as per your original design
        box.add(new JLabel("n", SwingConstants.CENTER), BorderLayout.NORTH);
        box.add(new JLabel("s", SwingConstants.CENTER), BorderLayout.SOUTH);
        box.add(new JLabel("w", SwingConstants.CENTER), BorderLayout.WEST);
        box.add(new JLabel("e", SwingConstants.CENTER), BorderLayout.EAST);

        return box;
    }

    // Simple helper to create the screens that appear in the center
    private static JPanel createView(String text) {
        JPanel p = new JPanel(new GridBagLayout()); // Centers the text
        p.add(new JLabel(text));
        return p;
    }
}