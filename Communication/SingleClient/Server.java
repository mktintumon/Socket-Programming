package Communication.SingleClient;

import java.io.*;
import java.net.*;

public class Server {
    public static void main(String[] args) throws Exception {
        try {
            ServerSocket ss = new ServerSocket(10001);
            System.out.println("Server ready for the communication!");

            Socket sock = ss.accept();

            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            OutputStream os = sock.getOutputStream();
            PrintWriter pwrite = new PrintWriter(os, true);

            InputStream is = sock.getInputStream();
            BufferedReader receiveRead = new BufferedReader(new InputStreamReader(is));

            String receiveMessage, sendMessage;

            boolean flag = true;
            while (flag) {
                if ((receiveMessage = receiveRead.readLine()) != null) {
                    System.out.println("Client : " + receiveMessage);
                }
                
                System.out.print("Server : ");
                sendMessage = br.readLine();
                if (sendMessage.equals("exit"))
                    flag = false;

                pwrite.println(sendMessage);
                pwrite.flush();
            }

            if (flag == false)
                ss.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

