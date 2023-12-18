package Communication.SingleClient;

import java.io.*;
import java.net.*;

public class Client {
    public static void main(String[] args) throws Exception {
        try {
            Socket socket = new Socket("localhost", 5000);

            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

            OutputStream os = socket.getOutputStream();
            PrintWriter pwrite = new PrintWriter(os, true);

            InputStream is = socket.getInputStream();
            BufferedReader receiveRead = new BufferedReader(new InputStreamReader(is));

            System.out.println("Start the communication, Type and press Enter key");

            String receiveMessage, sendMessage;
            boolean flag = true;

            while (flag) {
                System.out.print("Client(YOU) : ");
                sendMessage = br.readLine();
                if (sendMessage.equals("exit"))
                    flag = false;
                pwrite.println(sendMessage);
                pwrite.flush();

                if ((receiveMessage = receiveRead.readLine()) != null) { // receive from server
                    System.out.println("Server : " + receiveMessage);
                }
            }

            if (flag == false)
                socket.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
