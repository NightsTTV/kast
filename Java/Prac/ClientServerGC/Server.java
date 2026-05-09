import java.io.*;
import java.net.*;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

class Server {

    private static final Map<String, PrintWriter> clients = new ConcurrentHashMap<>();

    public static void main(String[] args) {
        int port = 1234;

        try (ServerSocket server = new ServerSocket(port)) {
            server.setReuseAddress(true);
            System.out.println("Server started. Listening on port " + port + "....");

            while (true) {
                Socket client = server.accept();
                ClientHandler handler = new ClientHandler(client);
                new Thread(handler).start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void broadcast(String message) {
        for (PrintWriter out : clients.values()) {
            out.println(message);
        }
    }

    private static String activeUsers() {
        return clients.isEmpty() ? "(none)" : String.join(", ", clients.keySet());
    }

    private static class ClientHandler implements Runnable {
        private final Socket socket;
        private PrintWriter out;
        private BufferedReader in;
        private String username = null;

        ClientHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                out = new PrintWriter(socket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                // Server prompts username; client types ONLY the name
                out.println("Server: Username =");

                String line;
                while ((line = in.readLine()) != null) {
                    line = line.trim();

                    // Gate: must set username first
                    if (username == null) {
                        if (line.isEmpty()) {
                            out.println("Server: Username cannot be empty.\nUsername =");

                            continue;
                        } 
                        if (clients.containsKey(line)) {
                            out.println("Server: Username already exists. Username =");
                            continue;
                        }

                        username = line;
                        clients.put(username, out);
                        broadcast("Server: Welcome " + username); // Welcome
                        System.out.println("Server: NEW USER: " + username + " joined");
                        continue;
                    }

 //    -------------------COMMANDS------------------------------ after username is set

                    if (line.equalsIgnoreCase("AllUsers")) {
                        out.println("Server: Active users -> " + activeUsers());
                        continue;
                    }

                    if (line.equalsIgnoreCase("Bye")) {
                        broadcast("Server: Goodbye " + username);
                        break;
                    }

                    // 🔹 LOG message sent from client to server
					System.out.println("Received from [" + username + "] : " + line);

                    // Broadcast message
                    broadcast(username + ": " + line);
                }

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (username != null) clients.remove(username);
                try { if (in != null) in.close(); } catch (IOException ignored) {}
                if (out != null) out.close();
                try { socket.close(); } catch (IOException ignored) {}
            }
        }
    }
}
