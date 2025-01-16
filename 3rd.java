import java.util.*;
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter number of productions: ");
        int n = sc.nextInt(); sc.nextLine();
        while (n-- > 0) {
            String[] p = sc.nextLine().split("->");
            List<String> alpha = new ArrayList<>(), beta = new ArrayList<>();
            for (String r : p[1].split("\\|"))
                if (r.startsWith(p[0])) alpha.add(r.substring(p[0].length()));
                else beta.add(r);
            if (alpha.isEmpty()) 
                System.out.println(p[0] + "->" + p[1]);
            else {
                beta.replaceAll(b -> b + p[0] + "'");
                alpha.replaceAll(a -> a + p[0] + "'");
                System.out.println(p[0] + "->" + String.join("|", beta));
                System.out.println(p[0] + "'->" + String.join("|", alpha) + "|ε");
            }
        }
    }
}

import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter number of production rules:");
        sc.nextLine();  // consume n (we only need 2 rules anyway)
        System.out.println("Enter production rules in format (X->Y):");
        String[] pr1 = sc.nextLine().split("->");
        String[] pr2 = sc.nextLine().split("->");
        System.out.println("\nFinal Production Rules:");
        System.out.println(pr1[0] + "->" + pr1[1]);
        String result = pr2[1].contains(pr1[0]) ? 
            pr2[1].replace(pr1[0], pr1[1].contains("|") ? pr1[1].split("\\|")[0] : pr1[1]) + "|" +
            pr2[1].replace(pr1[0], pr1[1].contains("|") ? pr1[1].split("\\|")[1] : pr1[1]) :
            pr2[1];
        System.out.println(pr2[0] + "->" + result);
        sc.close();
    }
}
