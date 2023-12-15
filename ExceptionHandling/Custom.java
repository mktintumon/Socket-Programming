package ExceptionHandling;

import java.util.Scanner;

class IllegalCourseException extends Exception{
    IllegalCourseException(String mssg){
        System.out.println(mssg);
    }
}

public class Custom {

    static void apply(String course) throws IllegalCourseException{
        if(!course.equals("MCA")){
            throw new IllegalCourseException("Courses other than MCA not eligible!!!");
        }
        else{
            System.out.println("You can apply for the JOB");
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter your course : ");
        String course = sc.nextLine();

        try {
            apply(course);
        } catch (IllegalCourseException e) {

        }
        finally{
            System.out.println("Hiring completed!!!");
        }

        sc.close();
    }
}
