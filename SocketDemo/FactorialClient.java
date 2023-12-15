package SocketDemo;
import java.io.*;
import java.io.PrintWriter;
import java.net.Socket;

public class FactorialClient {
    public static void main(String[] args) {
        try {
            System.out.println("client connected...");

            Socket socket = new Socket("localhost" , 10001);
            System.out.println("Connection established...");

            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Enter a number : ");
            int num = Integer.parseInt(br.readLine());

            PrintWriter out = new PrintWriter(socket.getOutputStream() , true);
            out.println(num);

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            System.out.println(in.readLine());

            socket.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }   
}
