package FileIO;
import java.io.*;

public class Print_Writer {
    public static void main(String[] args) {
        try{ 
            PrintWriter pw = new PrintWriter("pw_output.txt");
            pw.println("Hello, World!");
            pw.println("This is a sample text.");
            pw.println("Numbers are : 10 20 30 40 50");
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
