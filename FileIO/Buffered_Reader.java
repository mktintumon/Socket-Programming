package FileIO;
import java.io.BufferedReader;
import java.io.FileReader;

public class Buffered_Reader {
    public static void main(String[] args) {
        try {
            BufferedReader br = new BufferedReader(new FileReader("pw_output.txt"));
            String line;
            while((line = br.readLine()) != null){
                System.out.println(line);
            }

            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
