package Communication.ProtocolBased;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

public class ServerThread extends Thread {
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private String name;
    private static Set<ServerThread> clientHandlers = new HashSet<>();

    public ServerThread(Socket socket) {
        this.socket = socket;
        clientHandlers.add(this);  
    }

    public static int getConnectedClientsCount() {
        return clientHandlers.size();
    }

    public void run() {
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            System.out.println("Total clients : " + getConnectedClientsCount());

            while (true) {
                String message = in.readLine();
                if (message == null) {
                    break;
                }

                String[] parts = message.split(">", 3);
                if (parts.length != 3) {
                    continue;
                }

                String messageType = parts[0];
                String sender = parts[1];
                String content = parts[2];
                name = sender;

                if (messageType.equals("/chat")) {
                    System.out.println(sender + ": " + content);
                    out.println(sender + ": " + content);
                }

                if (messageType.equals("/broadcast")) {
                    System.out.println("Message delivered to all clients.");

                    broadcast(sender + ": " + content);
                }

                if (messageType.equals("/help")) {

                    System.out.println();
                    System.out.println("Showing HELP to client " + name);
                    out.println("Follow the protocol as --> [/command]>[your_name]>[message]\n"
                            + "\nWe have following list of commands : "
                            + "\n/chat --> Send Message to the server!"
                            + "\n/broadcast --> Chat with all connected clients!"
                            + "\n/help --> Shows lists of available commands! (Message can be left empty)"
                            + "\n/exit --> Getting yourself disconnected! (Message can be left empty)");
                }    

                if (messageType.equals("/exit")) {
                    System.out.println();
                    System.out.println("Client " + name + " left the chat!!!");
                    out.println("Client " + name + " left the chat!!!");
                    disconnect(name);
                }

            }
        } 
        catch (IOException e) {
            System.out.println("ServerThread error: " + e.getMessage());
        } 
    }

    private void broadcast(String message) {
        for (ServerThread clientHandler : clientHandlers) {
            if (clientHandler.name != null && !clientHandler.name.equals(name)) {
                clientHandler.out.println(message);
            }
        }
    }

    private void disconnect(String name) {
        if (name != null) {
            clientHandlers.remove(this);    
        }
        try {
            socket.close();
        } 
        catch (IOException e) {
            System.out.println("Client " + name + " left the chat!!!");
        }
    }
}
