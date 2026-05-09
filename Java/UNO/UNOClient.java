import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class UNOClient extends JFrame {
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private String playerName;
    private List<String> hand;
    private boolean myTurn;
    
    // GUI Components
    private JTextArea gameLog;
    private JPanel handPanel;
    private JLabel statusLabel;
    private JLabel topCardLabel;
    private JButton drawButton;
    
    public UNOClient(String serverAddress, int port, String playerName) {
        this.playerName = playerName;
        this.hand = new ArrayList<>();
        this.myTurn = false;
        
        setupGUI();
        connectToServer(serverAddress, port);
    }
    
    private void setupGUI() {
        setTitle("UNO Game - " + playerName);
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        
        // Top panel - Status
        JPanel topPanel = new JPanel(new GridLayout(2, 1));
        statusLabel = new JLabel("Connecting to server...", SwingConstants.CENTER);
        statusLabel.setFont(new Font("Arial", Font.BOLD, 16));
        topCardLabel = new JLabel("Top Card: -", SwingConstants.CENTER);
        topCardLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        topPanel.add(statusLabel);
        topPanel.add(topCardLabel);
        add(topPanel, BorderLayout.NORTH);
        
        // Center panel - Hand
        handPanel = new JPanel();
        handPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        handPanel.setBackground(new Color(34, 139, 34));
        JScrollPane handScroll = new JScrollPane(handPanel);
        handScroll.setBorder(BorderFactory.createTitledBorder("Your Hand"));
        add(handScroll, BorderLayout.CENTER);
        
        // Bottom panel - Controls and Log
        JPanel bottomPanel = new JPanel(new BorderLayout());
        
        // Controls
        JPanel controlPanel = new JPanel();
        drawButton = new JButton("Draw Card");
        drawButton.setEnabled(false);
        drawButton.addActionListener(e -> drawCard());
        controlPanel.add(drawButton);
        bottomPanel.add(controlPanel, BorderLayout.NORTH);
        
        // Game log
        gameLog = new JTextArea(10, 40);
        gameLog.setEditable(false);
        gameLog.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane logScroll = new JScrollPane(gameLog);
        logScroll.setBorder(BorderFactory.createTitledBorder("Game Log"));
        bottomPanel.add(logScroll, BorderLayout.CENTER);
        
        add(bottomPanel, BorderLayout.SOUTH);
        
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    private void connectToServer(String serverAddress, int port) {
        try {
            socket = new Socket(serverAddress, port);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            
            // Send player name
            out.println(playerName);
            
            log("Connected to server!");
            
            // Start receiver thread
            new Thread(new MessageReceiver()).start();
            
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, 
                "Cannot connect to server: " + e.getMessage(), 
                "Connection Error", 
                JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }
    
    private void playCard(int index) {
        if (!myTurn) {
            log("Not your turn!");
            return;
        }

        if (index < 0 || index >= hand.size()) {
            log("Invalid card index!");
            return;
        }

        String cardStr = hand.get(index);
        log("Attempting to play: " + cardStr + " at index " + index);

        // Check if it's a wild card
        if (cardStr.equals("W") || cardStr.equals("W+4")) {
            // Prompt for color choice
            String[] colors = {"RED", "YELLOW", "GREEN", "BLUE"};
            String chosenColor = (String) JOptionPane.showInputDialog(
                this,
                "Choose a color:",
                "Wild Card",
                JOptionPane.QUESTION_MESSAGE,
                null,
                colors,
                colors[0]
            );

            if (chosenColor != null) {
                out.println("PLAY_CARD|" + index + "|" + chosenColor);
                myTurn = !myTurn;
                drawButton.setEnabled(false);
            }
        } else {
            out.println("PLAY_CARD|" + index);
            myTurn = !myTurn;
            drawButton.setEnabled(false);
    }
}
    
    private void drawCard() {
        if (!myTurn) {
            log("Not your turn!");
            return;
        }

        // Disable immediately to prevent spam
        drawButton.setEnabled(false);
        myTurn = false; 

        out.println("DRAW_CARD");
        log("Drawing card..");
    }
    
    private void updateHand() {
        handPanel.removeAll();
        
        for (int i = 0; i < hand.size(); i++) {
            // FIX 1: Create a final variable for the lambda to use
            final int index = i; 
            String cardStr = hand.get(index); // Expected: "R5", "YS", "W4"

            //System.out.println("DEBUG - Rendering button for card string: [" + cardStr + "]");

            // Logic to determine what text to show
            String displayText = "";
            if (cardStr.startsWith("W")) {
                displayText = cardStr; // Show "W" or "W4"
            } else if (cardStr.length() > 1) {
                // Only use substring if it's a number. 
                // If it's an action card like "R+2", we want "+2".
                if (Character.isDigit(cardStr.charAt(1))) {
                    displayText = cardStr.substring(1); 
                } else {
                    // This handles "+2", "S", and "R"
                    displayText = cardStr.substring(1); 
                }
            }

            // FIX 2: Use 'displayText' instead of 'cardStr' for the button label
            JButton cardButton = new JButton("<html><center>" + displayText + "</center></html>");
            cardButton.setPreferredSize(new Dimension(80, 120));
            cardButton.setFont(new Font("Arial", Font.BOLD, 16));

            // Color logic (Keep your existing switch)
            char colorChar = cardStr.charAt(0);
            switch (colorChar) {
                case 'R': cardButton.setBackground(Color.RED); cardButton.setForeground(Color.BLACK); break;
                case 'Y': cardButton.setBackground(Color.YELLOW); cardButton.setForeground(Color.BLACK); break;
                case 'G': cardButton.setBackground(Color.GREEN); cardButton.setForeground(Color.BLACK); break;
                case 'B': cardButton.setBackground(Color.BLUE); cardButton.setForeground(Color.BLACK); break;
                case 'W': cardButton.setBackground(Color.BLACK); cardButton.setForeground(Color.BLACK); break;
                default: cardButton.setBackground(Color.GRAY);
            }

            cardButton.setOpaque(true);
            cardButton.setBorderPainted(true);
            
            // Use the final 'index' variable here
            cardButton.addActionListener(e -> playCard(index)); 
            handPanel.add(cardButton);
        }

        handPanel.revalidate();
        handPanel.repaint();
    }
    
    private void log(String message) {
        SwingUtilities.invokeLater(() -> {
            gameLog.append(message + "\n");
            gameLog.setCaretPosition(gameLog.getDocument().getLength());
        });
    }
    
    class MessageReceiver implements Runnable {
        @Override
        public void run() {
            try {
                String message;
                while ((message = in.readLine()) != null) {
                    handleServerMessage(message);
                }
            } catch (IOException e) {
                log("Disconnected from server");
            }
        }
    }
    
    private void handleServerMessage(String message) {
        String[] parts = message.split("\\|");
        String command = parts[0];
        
        SwingUtilities.invokeLater(() -> {
            try {
                switch (command) {
                    case "WELCOME":
                        log("Welcome " + parts[1] + "!");
                        statusLabel.setText("Connected as: " + parts[1]);
                        break;
                        
                    case "WAITING":
                        log(parts[1]);
                        statusLabel.setText(parts[1]);
                        break;
                        
                    case "JOINED_ROOM":
                        log("Joined room: " + parts[1] + " with " + parts[2] + " players");
                        statusLabel.setText("Room: " + parts[1] + " | Players: " + parts[2]);
                        break;
                        
                    case "YOUR_HAND":
                       hand.clear();
                       if (parts.length > 1 && !parts[1].isEmpty()) {
                           String[] cards = parts[1].split(",");
                           for (String card : cards) {
                               if (card.contains(":")) {
                                   // Format is "0:R5" - extract just "R5"
                                   String[] cardParts = card.split(":");
                                   if (cardParts.length == 2) {
                                       hand.add(cardParts[1]);
                                   }
                               } else {
                                   hand.add(card);
                               }
                           }
                       }
                       updateHand();
                       log("Your hand updated (" + hand.size() + " cards)");
                       break;

                    case "PLAYER_JOINED":
                        if (parts.length >= 3) {
                            String joinedPlayer = parts[1];
                            String totalPlayers = parts[2];
                            log(joinedPlayer + " joined the room. Total players: " + totalPlayers);
                            statusLabel.setText("Players in lobby: " + totalPlayers + "/4");
                        }
                        break;
                    
                    case "TIMER_RESET":
                        if (parts.length >= 2) {
                            String seconds = parts[1];
                            log("Game starting in " + seconds + " seconds...");
                            statusLabel.setText("Game starting in " + seconds + " seconds | Waiting for max players...");
                        }
                        break;
                    
                    case "TIMER_TICK":
                        if (parts.length >= 2) {
                            String seconds = parts[1];
                            statusLabel.setText("Game starting in " + seconds + " seconds...");
                        }
                        break;
                    
                    case "TIMER_CANCELLED":
                        log("Timer cancelled - waiting for more players");
                        statusLabel.setText("Waiting for players...");
                        break;
                        
                    case "GAME_STATE":
                       if (parts.length >= 4) {
                           String currentPlayer = parts[1];
                           String topCard = parts[2];
                           String currentColor = parts[3];

                           topCardLabel.setText("Top Card: " + topCard + " | Color: " + currentColor);
                           log("Current player: " + currentPlayer + " | Top: " + topCard);

                           // Update status if not your turn
                           if (!myTurn) {
                               statusLabel.setText("Waiting... Current: " + currentPlayer);
                           }

                           // Parse player card counts
                           if (parts.length > 4 && !parts[4].isEmpty()) {
                               String[] playerInfo = parts[4].split(",");
                               for (String info : playerInfo) {
                                   if (!info.isEmpty()) {
                                       log("  " + info);
                                   }
                               }
                           }
                       }
                       break;
                        
                    case "YOUR_TURN":
                        myTurn = !myTurn;
                        statusLabel.setText("YOUR TURN!");
                        statusLabel.setForeground(Color.GREEN);
                        drawButton.setEnabled(true);
                        log(">>> IT'S YOUR TURN! <<<");
                        break;
                        
                    case "SUCCESS":
                        statusLabel.setForeground(Color.BLACK);
                        log("Success: " + parts[1]);
                        break;
                        
                    case "ERROR":
                        log("Error: " + parts[1]);
                        JOptionPane.showMessageDialog(UNOClient.this, 
                            parts[1], "Error", JOptionPane.ERROR_MESSAGE);
                        break;
                        
                    case "GAME_WON":
                        log("*** GAME OVER! Winner: " + parts[1] + " ***");
                        statusLabel.setText("Game Over! Winner: " + parts[1]);
                        drawButton.setEnabled(false);
                        JOptionPane.showMessageDialog(UNOClient.this, 
                            parts[1] + " won the game!", 
                            "Game Over", 
                            JOptionPane.INFORMATION_MESSAGE);
                        break;
                        
                    case "GAME_ENDED":
                        log("Game ended: " + parts[1]);
                        statusLabel.setText("Game ended");
                        drawButton.setEnabled(false);
                        break;
                        
                    default:
                        log("Unknown message: " + message);
                }
            } catch (Exception e) {
                log("Error handling message: " + e.getMessage());
                e.printStackTrace();
            }
        });
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            String name = JOptionPane.showInputDialog("Enter your name:");
            if (name == null || name.trim().isEmpty()) {
                name = "Player" + System.currentTimeMillis();
            }
            
            String server = JOptionPane.showInputDialog("Enter server address:", "localhost");
            if (server == null || server.trim().isEmpty()) {
                server = "localhost";
            }
            
            new UNOClient(server, 8888, name);
        });
    }
}