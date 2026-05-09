package app;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent; 


public class LoginFrame extends JFrame implements ActionListener{

    private JPasswordField password;
    private JTextField username;
    private JLabel label_password, label_username, message, title;
    private JButton btn;
    private JCheckBox showPassword;

    public LoginFrame() {
        // Initialize the window
        this.setTitle("Login Screen");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1920,1080);
        this.setLocationRelativeTo(null);
        this.setLayout(null);

        title = new JLabel("App Login Form");
        title.setBounds(600,455,300,40);
        title.setFont(new Font("Arial",Font.ITALIC, 25));


        message = new JLabel("");
        message.setBounds(600, 730, 300, 40);

        showPassword = new JCheckBox("Show Password");
        showPassword.setBounds(600, 580, 200, 40);
        showPassword.addActionListener(this);

        btn = new JButton("Sign in");
        btn.setBounds(600, 620, 100, 40);
        btn.addActionListener(this);

        username = new JTextField();
        username.setBounds(610, 500, 300, 40);
        username.setFont(new Font("Arial",Font.BOLD,15));
        username.setForeground(Color.black);

        label_username = new JLabel("Username");
        label_username.setBounds(500, 500, 108, 40);
        label_username.setFont(new Font("Arial",Font.BOLD,20));
        label_username.setForeground(Color.white);
        label_username.setBackground(new Color(120, 129, 120));
        label_username.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
        label_username.setOpaque(true);

        label_password = new JLabel("Password");
        label_password.setBounds(500,550,108,40);
        label_password.setFont(new Font("Arial",Font.BOLD,20));
        label_password.setForeground(Color.white);
        label_password.setBackground(new Color(120, 129, 120));
        label_password.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
        label_password.setOpaque(true);

        password = new JPasswordField();
        password.setBounds(610,550,300,40);
        password.setFont(new Font("Arial",Font.BOLD,15));
        password.setForeground(Color.black);

        this.add(username);
        this.add(label_username);
        this.add(label_password);
        this.add(password);
        this.add(btn);
        this.add(message);
        this.add(showPassword);
        this.add(title);

        this.setVisible(true);

    }
    @Override
    public void actionPerformed(ActionEvent evt) {
        if(evt.getSource()==btn) {
            String userText = username.getText();
            String pwdText = new String(password.getPassword());

            if (userText.equalsIgnoreCase("Kas") && pwdText.equalsIgnoreCase("Password")) {
                JOptionPane.showMessageDialog(this, "You logged in successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "Invalid Login!!");
            }
        }

        if(evt.getSource()==showPassword) {
            if(showPassword.isSelected()) {
                password.setEchoChar((char) 0);
            } else {
                password.setEchoChar('*');
            }
        }

        //String msg = "Login Success!";
        //message.setText(msg);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
             new LoginFrame();
        });
    }
}
