import java.util.Scanner;

public class Main1 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter a string to validate if it ends with 'abc' or not:");
        String str = sc.nextLine();
        int q = 0;
        for (char c : str.toCharArray()) {
            if (c == 'a') q = (q == 0 || q == 3) ? 1 : (q == 1) ? 1 : 0;
            else if (c == 'b') q = (q == 1) ? 2 : 0;
            else if (c == 'c') q = (q == 2) ? 3 : 0;
            else {
                System.out.println("Given string is invalid.");
                return;
            }
        }
        System.out.println(q == 3 ? "ACCEPTED" : "NOT ACCEPTED");
    }
}
