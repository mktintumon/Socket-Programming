package Communication.FileBased;

import java.io.*;
import java.net.Socket;

public class FileClient {
    public static void main(String[] args) {
        String serverAddress = "localhost";
        int serverPort = 10001;

        try (Socket socket = new Socket(serverAddress, serverPort);
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {

            // PENDIING -->  COPY , MOVE , CLEAR , HELP

            while (true) {
                System.out.println("Enter command to run application\n[ /files , /read:filename , /append:filename , /delete:filename, /rename:oldFileName:newFileName, /info:filename , exit ]:");
                String command = reader.readLine();
                
                dos.writeUTF(command);

                String[] commandParts = command.split(":", 2);

                if (command.equalsIgnoreCase("exit")) {
                    break;
                }

                if (command.trim().equals("/files")) {
                    int fileListSize = dis.readInt();

                    if (fileListSize > 0) {
                        System.out.println("\nFiles in the directory:");

                        for (int i = 0; i < fileListSize; i++) {
                            String fileName = dis.readUTF();
                            System.out.println(fileName);
                        }
                        System.out.println("\n");
                    } 
                    else {
                        System.out.println("No files in the directory.");
                    }
                } 
                else if (command.startsWith("/read") && commandParts[0].equals("/read")) {
                    int fileSize = dis.readInt();

                    if (fileSize > 0) {
                        byte[] fileContent = new byte[fileSize];
                        dis.readFully(fileContent);
                        String content = new String(fileContent);
                        System.out.println("\nFile content:\n" + content);
                    }
                    else {
                        System.out.println("File not found or empty.\n");
                    }
                } 
                else if (command.startsWith("/append") && commandParts[0].equals("/append")) {
                    String prompt = dis.readUTF();
                    if(prompt.equalsIgnoreCase("Invalid filename!!!")){
                        System.out.println("Invalid Filename format\n");
                        continue;
                    }
                    if(prompt.equalsIgnoreCase("Multiple Extension")){
                        System.out.println("Multiple Extension Not Allowed!!!\n");
                        continue;
                    }

                    System.out.println(prompt);

                    while (true) {
                        String line = reader.readLine();
                        dos.writeUTF(line);

                        if (line.equalsIgnoreCase("done")) {
                            break;
                        }
                    }

                    String response = dis.readUTF();
                    System.out.println(response);
                }
                else if (command.startsWith("/delete") && commandParts[0].equals("/delete")) {

                    String response = dis.readUTF();
                    System.out.println(response);
                }
                else if (command.startsWith("/rename") && commandParts[0].equals("/rename")) {
                    String response = dis.readUTF();
                    System.out.println(response);
                }
                else if (command.startsWith("/info") && commandParts[0].equals("/info")) {
                    String response = dis.readUTF();
                    System.out.println(response);

                } 
                else {
                    dis.readUTF();
                    System.out.println("Invalid command\n");
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
