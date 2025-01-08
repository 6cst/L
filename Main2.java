import java.util.Scanner;
import java.util.regex.*;

public class Main2 {

    private static final String K = "int|float|char|if|else|while|return|system";
    private static final String I = "[a-zA-Z_][a-zA-Z0-9_]*";
    private static final String N = "\\d+";
    private static final String O = "[+\\-*/=<>!]+";
    private static final String S = "[(),;{}\\[\\]]";
    private static final String LIT = "\"[^\"]*\"";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the input string:");
        String in = sc.nextLine();
        System.out.println("Input String: " + in);
        t(in);
    }

    public static void t(String in) {
        String p = String.format("(%s)|(%s)|(%s)|(%s)|(%s)|(%s)", K, I, N, O, S, LIT);
        Matcher m = Pattern.compile(p).matcher(in);
        int tC = 0;

        while (m.find()) {
            if (m.group(1) != null) System.out.println("Keyword: " + m.group(1));
            else if (m.group(2) != null) System.out.println("Identifier: " + m.group(2));
            else if (m.group(3) != null) System.out.println("Number: " + m.group(3));
            else if (m.group(4) != null) System.out.println("Operator: " + m.group(4));
            else if (m.group(5) != null) System.out.println("Separator: " + m.group(5));
            else if (m.group(6) != null) System.out.println("String Literal: " + m.group(6));
            tC++;
        }
        System.out.println("Total Tokens: " + tC);
    }
}
