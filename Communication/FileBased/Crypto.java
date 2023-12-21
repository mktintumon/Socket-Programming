package Communication.FileBased;

import java.util.Scanner;

public class Crypto {

    // ENCRYPTION
    static String encryption(char[] s) {
        int l = s.length;
        int b = (int) Math.ceil(Math.sqrt(l));
        int a = (int) Math.floor(Math.sqrt(l));

        String encrypted = "";
        if (b * a < l) {
            if (Math.min(b, a) == b) {
                b = b + 1;
            } else {
                a = a + 1;
            }
        }

        char[][] arr = new char[a][b];
        int k = 0;

        // Fill the matrix row-wise
        for (int j = 0; j < a; j++) {
            for (int i = 0; i < b; i++) {
                if (k < l) {
                    arr[j][i] = s[k];
                }
                k++;
            }
        }

        // Read column-wise
        for (int j = 0; j < b; j++) {
            for (int i = 0; i < a; i++) {
                encrypted = encrypted + arr[i][j];
            }
        }
        return encrypted;
    }


    //DECRYPTION
    static String decryption(char[] s) {
        int l = s.length;
        int b = (int) Math.ceil(Math.sqrt(l));
        int a = (int) Math.floor(Math.sqrt(l));
        
        String decrypted = "";

        if (b * a < l) {
            if (Math.min(b, a) == b) {
                b = b + 1;
            } else {
                a = a + 1;
            }
        }

        char[][] arr = new char[a][b];
        int k = 0;

        // Fill the matrix column-wise
        for (int j = 0; j < b; j++) {
            for (int i = 0; i < a; i++) {
                if (k < l) {
                    arr[j][i] = s[k];
                }
                k++;
            }
        }

        // Read row-wise
        for (int j = 0; j < a; j++) {
            for (int i = 0; i < b; i++) {
                decrypted = decrypted + arr[i][j];
            }
        }
        return decrypted;
    }

    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter string to encrypt : ");
        String strToEncrypt = sc.nextLine();

        // Encryption of String
        String encrypted = encryption(strToEncrypt.toCharArray());
        System.out.print("Encrypted string : " + encrypted + "\n");


        System.out.print("Enter string to decrypt : ");
        String strToDecrypt = sc.nextLine();

        // Decryption of String
        String decrypted = decryption(strToDecrypt.toCharArray());
        System.out.print("Decrypted string : " + decrypted);
        sc.close();
    }
}
