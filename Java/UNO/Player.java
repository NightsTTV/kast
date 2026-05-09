// Player model with socket I/O 
import java.util.ArrayList; // Cards need ArrayList
import java.util.List; // Cards need a List
import java.io.*; // communication from user with script
import java.net.Socket; // used to connect client/server architechure


public class Player {
    // instance variables & data structs (what does user need)
    private List<Card> hand; // List of cards in hand
    private String id; // unique identifier
    private String name; // user identifier
    private Socket socket; // a way to connect the the player to the client and server
    private PrintWriter out; // formats output 
    private BufferedReader in; // reads input

    // Constructor
    public Player (String name, Socket socket) throws IOException {
        this.name = name; 
        this.id = java.util.UUID.randomUUID().toString(); // assigns unique random ID
        this.hand = new ArrayList<>(); // ArrayList for fast indexing access
        this.socket = socket;
        this.out = new PrintWriter(socket.getOutputStream(), true);
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    // Getters 
    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public List<Card> getHand() {
        return hand;
    }

    public Socket getSocket() {
        return socket;
    }

    // Helper methods
    // Adds Card to Player hand
    public void addCard(Card card) {
        hand.add(card); 
    }
    // Removes card (w/ Validation)
    public Card removeCard(int index) {
        if (index >= 0 && index < hand.size()) { // IF card pos >= 0 and within the number of total cards (in players hand)
            return hand.remove(index); // REMOVE card pos
        }
        return null; // OTHERWISE return null
    }
    // Get hand size
    public int getHandSize() {
        return hand.size();
    }
    // Sends Message to output
    public void sendMessage(String message) {
        out.println(message);
    }
    // Recieves input from user
    public String receiveMessage() throws IOException {
        return in.readLine();
    }
    // Close Socket
    public void close() {
        try {
            if (socket != null && !socket.isClosed()) { // if sockets not closed or null
                socket.close(); // close socket
            }
        } catch (IOException e) {
            System.err.println("Error closing player socket: " + e.getMessage());
        }
    }
    // Override toString
    @Override 
    public String toString() {
        return name + " (" + hand.size() + " cards)"; // e.g. Bob (5 cards)
    }
}
