package SocketDemo;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer {
    public static void main(String[] args) {
        try {
            System.out.println("waiting for clients...");

            ServerSocket ss = new ServerSocket(9806);
            Socket socket = ss.accept(); // returns a socket object

            // get message string from client side
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String str = in.readLine();

            // sending back the received message to client
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            out.println("server says : " + str);
            ss.close();

            System.out.println("Connection established...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
