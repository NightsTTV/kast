package app;
import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {

    private JPasswordField password;
    private JTextField username;
    private JLabel label_password, label_username, message, title;
    private JButton btn;

    public LoginFrame() {
        // Initialize the window
        this.setTitle("Login Screen");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1920,1080);
        this.setLocationRelativeTo(null);
        this.setLayout(null);

        message = new JLabel("Message here");
        message.setBounds(600, 700, 300, 40);

        btn = new JButton("Sign in");
        btn.setBounds(600, 600, 100, 40);

        username = new JTextField();
        username.setBounds(600, 500, 300, 40);

        label_username = new JLabel("Username");
        label_username.setBounds(500, 500, 100, 40);

        label_password = new JLabel("Password");
        label_password.setBounds(500,550,100,40);

        password = new JPasswordField();
        password.setBounds(600,550,300,40);

        this.add(username);
        this.add(label_username);
        this.add(label_password);
        this.add(password);
        this.add(btn);
        this.add(message);

        this.setVisible(true);

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LoginFrame login = new LoginFrame();
            login.setVisible(true);
        });
    }
}
