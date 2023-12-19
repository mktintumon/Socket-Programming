package Communication.ProtocolBased;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientThread {
    public static void main(String[] args) {
        String hostname = "localhost";
        int port = 10001;

        try (Socket socket = new Socket(hostname, port)) {
            System.out.println("Connected to the server");

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            // Start a new thread to read incoming messages
            new Thread(() -> {
                try {
                    while (true) {
                        String message = in.readLine();
                        if (message == null) {
                            break;
                        }
                        System.out.println(message);
                    }
                } catch (IOException e) {
                    System.out.println("Read error: " + e.getMessage());
                }
            }).start();


            // Read user input and send messages to the server
            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
            while (true) {
                String message = userInput.readLine();
                out.println(message);
            }
        } 
        catch (UnknownHostException e) {
            System.out.println("Server not found: " + e.getMessage());
        } 
        catch (IOException e) {
            System.out.println("I/O error: " + e.getMessage());
        }
    }
}
