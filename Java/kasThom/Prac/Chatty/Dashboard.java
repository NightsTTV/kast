import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Dashboard extends JFrame {

    private JPanel sidebarPanel, userListPanel;
    private JTextArea chatArea;
    private JTextField inputField;

    private final ChatClient client;
    private boolean isVisible = false;
    private final String username;

    public Dashboard(String username, ChatClient client) {
        this.username = username;
        this.client = client;

        setTitle("Chatty - " + username);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // ===== Top bar =====
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setPreferredSize(new Dimension(0, 55));
        topBar.setBackground(new Color(20, 25, 30));

        JLabel title = new JLabel("  Welcome, " + username);
        title.setForeground(Color.WHITE);
        title.setFont(new Font("SansSerif", Font.BOLD, 18));

        topBar.add(title, BorderLayout.WEST);
        add(topBar, BorderLayout.NORTH);

        // ===== Sidebar (left) =====
        sidebarPanel = new JPanel();
        sidebarPanel.setBackground(new Color(12, 45, 50));
        sidebarPanel.setPreferredSize(new Dimension(180, 0));
        sidebarPanel.setLayout(new BoxLayout(sidebarPanel, BoxLayout.Y_AXIS));

        JButton allUsersBtn = new JButton("All Users");
        allUsersBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        // ✅ FIX: show panel + request list from server
        allUsersBtn.addActionListener(e -> {
            toggleUserList();
            if (isVisible) {
                client.send("AllUsers"); // ask server for active users
            }
        });

        userListPanel = new JPanel();
        userListPanel.setBackground(new Color(40, 40, 40));
        userListPanel.setLayout(new BoxLayout(userListPanel, BoxLayout.Y_AXIS));
        userListPanel.setVisible(false);

        sidebarPanel.add(Box.createVerticalStrut(10));
        sidebarPanel.add(allUsersBtn);
        sidebarPanel.add(Box.createVerticalStrut(10));
        sidebarPanel.add(userListPanel);

        // ===== Chat area (center) =====
        chatArea = new JTextArea();
        chatArea.setBackground(Color.BLACK);
        chatArea.setForeground(Color.WHITE);
        chatArea.setEditable(false);
        chatArea.setLineWrap(true);
        chatArea.setWrapStyleWord(true);

        JScrollPane chatScroll = new JScrollPane(chatArea);

        // ===== Input area (bottom) =====
        inputField = new JTextField();
        inputField.addActionListener(e -> sendMessage());

        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.setPreferredSize(new Dimension(0, 45));
        inputPanel.add(inputField, BorderLayout.CENTER);

        // ===== Add to frame =====
        add(sidebarPanel, BorderLayout.WEST);
        add(chatScroll, BorderLayout.CENTER);
        add(inputPanel, BorderLayout.SOUTH);

        // ✅ FIX: intercept "Active users" response and update user list panel
        client.startListening(msg -> {
            if (msg == null) return;

            if (msg.startsWith("Server: Active users ->")) {
                updateUserListFromServerLine(msg);
                chatArea.append(msg + "\n"); // so it “does something” even if panel is hidden
            } else {
                chatArea.append(msg + "\n");
            }
        });

        // Close socket when window closes
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                client.close();
            }
        });

        setVisible(true);
    }

    private void toggleUserList() {
        isVisible = !isVisible;
        userListPanel.setVisible(isVisible);
        sidebarPanel.revalidate();
        sidebarPanel.repaint();
    }

    // Parses: "Server: Active users -> kast, paige"
    private void updateUserListFromServerLine(String line) {
        String prefix = "Server: Active users -> ";
        if (!line.startsWith(prefix)) return;

        String users = line.substring(prefix.length()).trim();

        userListPanel.removeAll();

        if (!users.equals("(none)") && !users.isEmpty()) {
            String[] split = users.split(",\\s*");
            for (String u : split) {
                if (u == null) continue;
                u = u.trim();
                if (u.isEmpty()) continue;

                JLabel userLabel = new JLabel(u);
                userLabel.setForeground(Color.WHITE);
                userLabel.setBorder(BorderFactory.createEmptyBorder(8, 10, 8, 10));
                userListPanel.add(userLabel);
            }
        } else {
            JLabel none = new JLabel("(no users online)");
            none.setForeground(Color.LIGHT_GRAY);
            none.setBorder(BorderFactory.createEmptyBorder(8, 10, 8, 10));
            userListPanel.add(none);
        }

        userListPanel.revalidate();
        userListPanel.repaint();
    }

    private void sendMessage() {
        String msg = inputField.getText().trim();
        if (msg.isEmpty()) return;

        if (msg.equalsIgnoreCase("AllUsers")) {
            if (!isVisible) {          // panel hidden?
             toggleUserList();      // show it
            }
        }

        client.send(msg);      // send to server
        inputField.setText(""); // clear input
    }
}
