package Communication.FileBased;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.nio.file.StandardOpenOption.APPEND;

public class FileServer {
    public static void main(String[] args) {
        int port = 10001;
        String baseDirectory = "TestFolder/";

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

                System.out.println("command -> " + command);

                if (command.equalsIgnoreCase("exit")) {
                    break;
                }

                String[] commandParts = command.split("\\:" , 3);

                switch (commandParts[0].trim()) {
                    case "/files":

                        listFiles(dos, baseDirectory);
                        break;
                    case "/read":
                        if (commandParts.length == 2) {
                            String safe = commandParts[1].trim();
                            if (safe.startsWith(".") || safe.startsWith("/")) {
                                dos.writeInt(0);
                            } else {
                                readFile(dos, baseDirectory, commandParts[1]);

                            }

                        } else {
                            dos.writeUTF("Invalid command");
                        }
                        break;
                    case "/append":
                        if (commandParts.length == 2) {
                            String safeFilename = commandParts[1];
                            System.out.println(safeFilename);
                            String[] extension = safeFilename.split("\\.");

                            if (safeFilename.startsWith(".") || safeFilename.startsWith("/")) {
                                dos.writeUTF("Invalid filename!!!");
                                break;
                            }
                            if (extension.length > 2) {
                                dos.writeUTF("Multiple Extension");
                                break;
                            }
                            writeFile(dis, dos, baseDirectory, commandParts[1]);
                        } else {
                            dos.writeUTF("Invalid command");
                        }
                        break;
                    case "/delete":
                        if (commandParts.length == 2) {
                             String safeFilename = commandParts[1];
                            if (safeFilename.startsWith(".") || safeFilename.startsWith("/")) {
                                dos.writeUTF("Invalid command format!!!");
                                break;
                            }
                            deleteFile(dos, baseDirectory, commandParts[1]);
                        } else {
                            dos.writeUTF("Invalid command");
                        }
                        break;
                    case "/rename":
                        if (commandParts.length == 3) {
                            String safeFilename = commandParts[1];
                            if (safeFilename.startsWith(".") || safeFilename.startsWith("/")) {
                                dos.writeUTF("Invalid command format!!!");
                                break;
                            }
                            renameFile(dis, dos, baseDirectory, commandParts[1],commandParts[2]);
                        } else {
                            dos.writeUTF("Invalid command");
                        }
                        break;
                    case "/info":
                        if (commandParts.length == 2) {
                            String safeFilename = commandParts[1];
                            if (safeFilename.startsWith(".") || safeFilename.startsWith("/")) {
                                dos.writeUTF("Invalid command format!!!");
                                break;
                            }
                            fileInfo(dos, baseDirectory, commandParts[1]);
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
            System.out.println("Client left!!!");
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

        Path file = Paths.get(filePath);
        // Check if the file exists, create it if not
        if (Files.exists(file) == false) {
            dos.writeInt(0);
        } else {
            byte[] fileContent = Files.readAllBytes(Paths.get(filePath));

            dos.writeInt(fileContent.length);
            dos.write(fileContent);
        }
    }


    // WRITES TO THE EXISTING FILE ELSE CREATES THE NEW ONE
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
        Path file = Paths.get(filePath);

        // Check if the file exists, create it if not
        if (!Files.exists(file)) {
            Files.createFile(file);
        }

        Files.write(Paths.get(filePath), contentBuilder.toString().getBytes(), APPEND);

        dos.writeUTF("File appended successfully\n");
    }


    // DELETES THE FILE SPECIFIED
    private static void deleteFile(DataOutputStream dos, String baseDirectory, String fileName) throws IOException {
        String filePath = Paths.get(baseDirectory, fileName).toString();
        Path file = Paths.get(filePath);

        if (Files.exists(file)) {
            Files.delete(file);
            dos.writeUTF("File deleted successfully\n");
        } else {
            dos.writeUTF("File not found\n");
        }
    }


    // RENAMES THE FILE SPECIFIED
    private static void renameFile(DataInputStream dis, DataOutputStream dos, String baseDirectory, String oldName , String newName)
            throws IOException {

        String oldFilePath = Paths.get(baseDirectory, oldName).toString();
        String newFilePath = Paths.get(baseDirectory, newName).toString();

        File oldFile = new File(oldFilePath);
        File newFile = new File(newFilePath);

        if (oldFile.renameTo(newFile)) {
            dos.writeUTF("File renamed successfully\n");
        } else {
            dos.writeUTF("Error renaming file\n");
        }
    }

    
    // GET INFO OF SPECIFIED FILE
    private static void fileInfo(DataOutputStream dos, String baseDirectory, String fileName) throws IOException {
        String filePath = Paths.get(baseDirectory, fileName).toString();
        Path file = Paths.get(filePath);

        if (Files.exists(file)) {
            dos.writeUTF("File information:\n size:" + Files.size(file) + " bytes\n Last Modified: " + Files.getLastModifiedTime(file) + "\n");
        } else {
            dos.writeUTF("File not found\n");
        }
    }
}
