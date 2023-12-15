import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class FtpServer {
    public static void main(String[] args) {
        try {
            System.out.println("Waiting for clients...");
            ServerSocket ss = new ServerSocket(10001);
            Socket socket = ss.accept();
            System.out.println("Client Onboarded!");

            String path = "C:\\Users\\Mohit\\Desktop\\Socket Programming\\FTP server\\";
            FileInputStream fis = new FileInputStream(path + "mohit.txt");
            
            int size = (int)fis.available();
            byte[] byteFile = new byte[size];
            fis.read(byteFile , 0 , byteFile.length);
            OutputStream os = socket.getOutputStream();
            os.write(byteFile , 0 , byteFile.length);
            System.out.println("File sent");            
            
            fis.close();
            ss.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
