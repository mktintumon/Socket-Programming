package SocketDemo;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class EchoClient {
    public static void main(String[] args) {
        try {
            System.out.println("client connected...");

            Socket socket = new Socket("localhost" , 9806);

            // reading input from console
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Enter a string : ");
            String str = br.readLine();

            // sending data to server socket
            PrintWriter out = new PrintWriter(socket.getOutputStream() , true); 
            out.println(str);

            // getting message from server
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            System.out.println(in.readLine());
            socket.close();

            System.out.println("Connection established...");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }    
}