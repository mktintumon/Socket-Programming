package ExceptionHandling;

public class Exp2 {

     static void myFunction() throws IllegalAccessException{
        throw new IllegalAccessException();
    }

    public static void main(String[] args) {
        int a = 10;
        int b = 0;
        int c = 0;
        try {
            if(b == 0){
                throw new ArithmeticException();
            }
            else{
                c = a/b;
            }
            
            myFunction();
            
        } catch (ArithmeticException e) {
            System.out.println("Error : Can't divide by zero");
        }
        catch(IllegalAccessException e){
            System.out.println("Error : Not able to access data");
        }

        System.out.println("value of c : " + c);
    }
}
