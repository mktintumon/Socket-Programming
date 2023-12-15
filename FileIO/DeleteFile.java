package FileIO;
import java.io.File;

public class DeleteFile {
    public static void main(String[] args) {
        String path = "C:\\Users\\Mohit\\Desktop\\Socket Programming\\";
        String fileName = "demo.txt";

        try {
            File fileToDelete = new File(path+fileName);

            if (fileToDelete.exists()) {
                if (fileToDelete.delete()) {
                    System.out.println("File '" + fileName + "' has been deleted successfully.");
                } else {
                    System.out.println("Failed to delete the file '" + fileName + ".");
                }
            } else {
                System.out.println("File '" + fileName + "' does not exist.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
