import java.io.*;
import java.net.Socket;

public class FtpClient {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost" , 10001);
            System.out.println("Connection Established!");

            System.out.println("Receiving file...");

            String path = "C:\\Users\\Mohit\\Desktop\\Socket Programming\\FTP client\\";
            InputStream is = socket.getInputStream();
            FileOutputStream fos = new FileOutputStream(path + "mohit.txt");
            byte[] byteFile = new byte[48];
            is.read(byteFile , 0 , byteFile.length);
            fos.write(byteFile , 0 , byteFile.length);
            System.out.println("File received successfully");

            fos.close();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
