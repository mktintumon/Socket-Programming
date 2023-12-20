package Communication.FileBased;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileServer {
    public static void main(String[] args) {
        int port = 10001;
        String baseDirectory = "C:\\Users\\Mohit\\Desktop\\Socket programming";

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server listening on port " + port);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket);

                Thread clientThread = new Thread(() -> handleClient(clientSocket, baseDirectory));
                clientThread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static void handleClient(Socket clientSocket, String baseDirectory) {
        try (DataOutputStream dos = new DataOutputStream(clientSocket.getOutputStream());
                DataInputStream dis = new DataInputStream(clientSocket.getInputStream())) {

            while (true) {
                String command = dis.readUTF();
                if (command.equalsIgnoreCase("exit")) {
                    break;
                }

                String[] commandParts = command.split(":", 2);

                switch (commandParts[0]) {
                    case "/files":
                        listFiles(dos, baseDirectory);
                        break;
                    case "/read":
                        if (commandParts.length == 2) {
                            readFile(dos, baseDirectory, commandParts[1]);
                        } else {
                            dos.writeUTF("Invalid command");
                        }
                        break;
                    case "/append":
                        if (commandParts.length == 2) {
                            writeFile(dis, dos, baseDirectory, commandParts[1]);
                        } else {
                            dos.writeUTF("Invalid command");
                        }
                        break;
                    default:
                        dos.writeUTF("Invalid command");
                        break;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // SHOWS LIST OF FILES OR FOLDERS
    private static void listFiles(DataOutputStream dos, String baseDirectory) throws IOException {
        File directory = new File(baseDirectory);
        String[] fileList = directory.list();

        if (fileList != null) {
            dos.writeInt(fileList.length);
            for (String fileName : fileList) {
                dos.writeUTF(fileName);
            }
        } else {
            dos.writeInt(0);
        }
    }

    // READ THE CONTENTS OF FILE
    private static void readFile(DataOutputStream dos, String baseDirectory, String fileName) throws IOException {
        String filePath = Paths.get(baseDirectory, fileName).toString();
        byte[] fileContent = Files.readAllBytes(Paths.get(filePath));

        dos.writeInt(fileContent.length);
        dos.write(fileContent);
    }


    // WRITES TO THE EXISTING FILE
    private static void writeFile(DataInputStream dis, DataOutputStream dos, String baseDirectory, String fileName)
            throws IOException {
        dos.writeUTF("Enter content to append (type 'done' to finish):");

        StringBuilder contentBuilder = new StringBuilder("\n");
        while (true) {
            String line = dis.readUTF();
            System.out.println(line);
            if (line.equalsIgnoreCase("done")) {
                break;
            }
            contentBuilder.append(line).append("\n");
        }

        String filePath = Paths.get(baseDirectory, fileName).toString();
        Files.write(Paths.get(filePath), contentBuilder.toString().getBytes(), java.nio.file.StandardOpenOption.APPEND);

        dos.writeUTF("File appended successfully\n");
    }
}
