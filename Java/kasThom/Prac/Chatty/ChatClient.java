import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.util.function.Consumer;

public class ChatClient {
    private final String host;
    private final int port;

    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    public ChatClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void connect() throws IOException {
        socket = new Socket(host, port);
        out = new PrintWriter(socket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    public void send(String msg) {
        if (out != null) out.println(msg);
    }

    // Listen for server messages and push them into the UI safely
    public void startListening(Consumer<String> onMessage) {
        Thread t = new Thread(() -> {
            try {
                String line;
                while ((line = in.readLine()) != null) {
                    String msg = line;
                    SwingUtilities.invokeLater(() -> onMessage.accept(msg));
                }
            } catch (IOException ignored) {}
        });
        t.setDaemon(true);
        t.start();
    }

    public void close() {
        try { if (in != null) in.close(); } catch (IOException ignored) {}
        if (out != null) out.close();
        try { if (socket != null) socket.close(); } catch (IOException ignored) {}
    }
}
