package FileIO;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class FileCopy {
    public static void main(String[] args) {
        try {
            FileInputStream fis = new FileInputStream("pw_output.txt");
            FileOutputStream fos = new FileOutputStream("pw_output_copy.txt");

            int size = (int)fis.available();
            byte[] bytefile = new byte[size];

            int bytesRead;
            while((bytesRead = fis.read(bytefile)) != -1){
                fos.write(bytefile , 0 , bytesRead);
            }

            System.out.println("file copid sccessfully!!!");

            fos.close();
            fis.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
