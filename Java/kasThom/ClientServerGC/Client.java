import java.io.*;
import java.net.*;
import java.util.*;

// Client Side
class Client {
	// driver code
	public static void main(String[] args){
		if (args.length != 2) {
			System.out.println("Usage: java client <serve_ip> <port>");
			return;
		}

		String host = args[0];
		int port = Integer.parseInt(args[1]);
		
		Scanner scanner = new Scanner(System.in);

		// establish a connection (provide the host and port number)
		try (Socket socket = new Socket("localhost", 1234)) {

			// writing to server
			PrintWriter output = new PrintWriter(
				socket.getOutputStream(), true);

			// reading from server
			BufferedReader input = new
				BufferedReader(new InputStreamReader(
					socket.getInputStream()));

			// Reads ALL server broadcasts/prompts
			Thread reader = new Thread(() -> {
				try {
					String msg;
					while ((msg = input.readLine()) != null) {
						System.out.println(msg);
					}
				} catch (IOException ignored) {}
			});
			reader.setDaemon(true);
			reader.start();

			// Client types username when prompted, then messages
			while (true) {
				String line = scanner.nextLine();
				output.println(line);

				// optional local cost
				if (line.equalsIgnoreCase("exit")) break;
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		} finally {
			scanner.close();
		}
	} 
}