import javax.swing.*;
import java.awt.*;

public class Start extends JFrame {

    private JTextField hostField;
    private JTextField portField;
    private JTextField usernameField;

    public Start() {
        setTitle("Chatty - Start");
        setSize(520, 360);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(null);
        setLocationRelativeTo(null);
    
        // 1. Setup the Background Image
        // Use the image you just generated!
        ImageIcon icon = new ImageIcon("bck.png"); 
        // Scale it to fit the window exactly
        Image img = icon.getImage().getScaledInstance(520, 360, Image.SCALE_SMOOTH);
        JLabel background = new JLabel(new ImageIcon(img));
        background.setBounds(0, 0, 520, 360);
    
        // 2. ===== Title =====
        JLabel title = new JLabel("Chatty", SwingConstants.CENTER); // Centered text looks cleaner
        title.setForeground(Color.GREEN); // Hacker green!
        title.setFont(new Font("Courier New", Font.BOLD, 28));
        title.setBounds(30, 25, 460, 40);
        title.setBorder(BorderFactory.createLineBorder(Color.GREEN, 1));
        add(title);
    
        // 3. ===== Card panel (Make it semi-transparent) =====
        JPanel card = new JPanel(null);
        // The 4th number (150) is alpha/transparency (0-255)
        card.setBackground(new Color(20, 20, 20, 150)); 
        card.setBounds(30, 85, 460, 170);
        add(card);

        // Host
        JLabel hostLabel = new JLabel("Server IP:");
        hostLabel.setForeground(Color.WHITE);
        hostLabel.setBounds(20, 20, 100, 25);
        card.add(hostLabel);

        hostField = new JTextField("localhost");
        hostField.setBounds(130, 18, 300, 30);
        card.add(hostField);

        // Port
        JLabel portLabel = new JLabel("Port:");
        portLabel.setForeground(Color.WHITE);
        portLabel.setBounds(20, 65, 100, 25);
        card.add(portLabel);

        portField = new JTextField("1234");
        portField.setBounds(130, 63, 300, 30);
        card.add(portField);

        // Username
        JLabel userLabel = new JLabel("Username:");
        userLabel.setForeground(Color.WHITE);
        userLabel.setBounds(20, 110, 100, 25);
        card.add(userLabel);

        usernameField = new JTextField();
        usernameField.setBounds(130, 108, 300, 30);
        card.add(usernameField);

        // ===== Connect button =====
        JButton connectBtn = new JButton("Connect");
        connectBtn.setBounds(200, 270, 120, 35);
        add(connectBtn);
        add(background);

        connectBtn.addActionListener(e -> connect());

        // ENTER key triggers connect
        usernameField.addActionListener(e -> connect());
        portField.addActionListener(e -> connect());
        hostField.addActionListener(e -> connect());

        setVisible(true);
    }

    private void connect() {
        String host = hostField.getText().trim();
        String portText = portField.getText().trim();
        String username = usernameField.getText().trim();

        if (host.isEmpty() || portText.isEmpty() || username.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please fill in Server IP, Port, and Username.",
                    "Missing Info",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        int port;
        try {
            port = Integer.parseInt(portText);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "Port must be a number (example: 1234).",
                    "Invalid Port",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            ChatClient client = new ChatClient(host, port);
            client.connect();

            // server expects username first
            client.send(username);

            dispose();
            new Dashboard(username, client);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Couldn't connect:\n" + ex.getClass().getSimpleName() + ": " + ex.getMessage(),
                    "Connection Error",
                    JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Start::new);
    }
}
