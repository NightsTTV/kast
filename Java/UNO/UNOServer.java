import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;

public class UNOServer {
    private static final int PORT = 8888;
    private static Map<String, GameRoom> gameRooms = new ConcurrentHashMap<>();
    private static ExecutorService threadPool = Executors.newCachedThreadPool();
    private static Map<Player, ClientHandler> playerHandlers = new ConcurrentHashMap<>();
    private static int roomCounter = 0;

    public static void main(String[] args) {
        System.out.println("UNO Server starting on port " + PORT);

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server is running. Waiting for players..");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New connection from: " + clientSocket.getInetAddress());

                // Handle each client in a separate thread
                threadPool.execute(new ClientHandler(clientSocket));
            }
        } catch (IOException e) {
            System.err.println("Server error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            threadPool.shutdown();
        }
    }

    static class ClientHandler implements Runnable {
        private Socket socket;
        private Player player;
        private GameRoom gameRoom;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                // Read Players Name
                BufferedReader in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

                String playerName = in.readLine();
                if (playerName == null || playerName.trim().isEmpty()) {
                    playerName = "Player" + System.currentTimeMillis();
                }

                player = new Player(playerName, socket);
                playerHandlers.put(player, this); // Track this handler
                System.out.println("Player connected: " + playerName);

                // Send welcome message
                player.sendMessage("WELCOME|" + playerName);

                // Add to waiting queue and try to match
                matchPlayer();

                // Main message loop
                String message;
                while((message = in.readLine()) != null) {
                    System.out.println("Recieved from " + playerName + ": " + message);
                    handleMessage(message);
                }
            } catch (IOException e) {
                System.err.println("Client handler error: " + e.getMessage());
            } finally {
                cleanup();
            }
        }

        private synchronized void matchPlayer() {
            // Look for an existing room that isn't full and hasn't started
            GameRoom room = null;
            for (GameRoom existingRoom : gameRooms.values()) {
                if (!existingRoom.isGameFull() && !existingRoom.isGameStarted()) {
                    room = existingRoom;
                    break;
                }
            }

            // If no available room, create a new one
            if (room == null) {
                String roomId = "ROOM_" + (++roomCounter);
                room = new GameRoom(roomId);
                gameRooms.put(roomId, room);
            }

            // Add this player to the room (GameRoom.addPlayer handles timer + auto-start)
            if (room.addPlayer(player)) {
                this.gameRoom = room;
                playerHandlers.put(player, this);
                player.sendMessage("JOINED_ROOM|" + room.getRoomId() + "|" + room.getPlayers().size());

                // If the game has started (addPlayer triggered it via max players or timer),
                // send hands to all players in the room
                if (room.isGameStarted()) {
                    sendHandsToAllPlayers(room);
                }
            } else {
                player.sendMessage("ERROR|Could not join room");
            }
        }

        private void sendHandsToAllPlayers(GameRoom room) {
            for (Player p : room.getPlayers()) {
                // Make sure each player's handler knows the room
                ClientHandler handler = playerHandlers.get(p);
                if (handler != null) {
                    handler.gameRoom = room;
                }

                StringBuilder handMsg = new StringBuilder("YOUR_HAND|");
                for (int i = 0; i < p.getHand().size(); i++) {
                    if (i > 0) handMsg.append(",");
                    handMsg.append(i).append(":").append(p.getHand().get(i).toShortString());
                }
                p.sendMessage(handMsg.toString());
            }
        }

        private void handleMessage(String message) {
            String[] parts = message.split("\\|");
            String command = parts[0];

            try {
                switch (command) {
                    case "PLAY_CARD":
                        if (gameRoom != null && parts.length >= 2) {
                            int cardIndex = Integer.parseInt(parts[1]);
                            Card.Color chosenColor = Card.Color.RED; // DEFAULT

                            if (parts.length >= 3) {
                                chosenColor = Card.Color.valueOf(parts[2]);
                            }

                            String result = gameRoom.playCard(player, cardIndex, chosenColor);
                            player.sendMessage(result);

                            // Update hand
                            if (!result.startsWith("ERROR")) {
                                sendHand();
                            } else {
                                // If it WAS an error, we tell the client it's STILL their turn
                                player.sendMessage("YOUR_TURN"); 
                            }
                        }
                        break;

                    case "DRAW_CARD":
                        if (gameRoom != null) {
                            String result = gameRoom.drawCard(player);
                            player.sendMessage(result);

                            // Update hand
                            sendHand();
                        }
                        break;

                    case "GET_HAND":
                        sendHand();
                        break;
                    case "GET_STATE":
                        if (gameRoom != null) {
                            // Game state is broadcast automatically
                        }
                        break;

                        default:
                            player.sendMessage("ERROR|Unknown command: " + command);
                    } 
                } catch (Exception e) {
                    player.sendMessage("ERROR|" + e.getMessage());
                    e.printStackTrace();
            }
        }

        private void sendHand() {
            StringBuilder handMsg = new StringBuilder("YOUR_HAND|");
            for (int i = 0; i < player.getHandSize(); i++) {
                if (i > 0) handMsg.append(",");
                handMsg.append(i).append(":").append(player.getHand().get(i).toShortString());
            }
            //System.out.println("DEBUG - Sending to " + player.getName() + ": " + handMsg.toString());

            player.sendMessage(handMsg.toString());
        }

        private void cleanup() {
            System.out.println("Player disconnected: " +
                (player != null ? player.getName() : "Unknown"));
            
            if (gameRoom != null) {
                gameRoom.removePlayer(player);
            }

            if (player != null) {
                player.close();
            }
        }
    }
}