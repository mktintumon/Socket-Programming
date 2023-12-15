package ExceptionHandling;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Exp1 {
    public static void main(String[] args) {
        int a = 50;
        int b = 0;
        int ans = 0;
        BufferedReader br = null;
        int[] arr = {0,3,5,1,1};


        try {
            br = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("Enter some text: ");
            // System.in.close(); ---> simulates IOException

            String input = br.readLine();
            System.out.println("You entered : " + input);

            ans = a / b;
            System.out.println("Array value : " + arr[2]);

        } 
        catch (ArithmeticException e) {
            System.out.println("Error : Can't divide by Zero");
            ans = 0;
        } 
        catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Error : Array index does not exists");
        } 
        catch (IOException e) {
            System.err.println("Error : An IO error occurred");
        } 
        catch (Exception e) {
            System.out.println("Error : Unknown Exception");
        }


        System.out.println("Value of division : " + ans);
        System.out.println("End of the code!!!");
    }
}
