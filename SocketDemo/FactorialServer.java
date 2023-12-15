package SocketDemo;
import java.io.*;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class FactorialServer {
    public static void main(String[] args) {
        try {
            System.out.println("waiting for clients...");

            ServerSocket ss = new ServerSocket(10001);
            Socket socket = ss.accept(); // returns a socket object
            System.out.println("Connection established...");

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            int num = Integer.parseInt(in.readLine());

            PrintWriter out = new PrintWriter(socket.getOutputStream() , true);
            out.println("Factorial of " + num + " is : " + calcFactorial(num));

            ss.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static int calcFactorial(int num){
        return num == 0 ? 1 : num*calcFactorial(num-1);
    }
}
