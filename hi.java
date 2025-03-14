import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        // Create a scanner for input
        Scanner s = new Scanner(System.in);
        
        // Prompt the user for a production rule
        System.out.println("Enter production rule (e.g., S-> a|a bc|a bcd|a bcd):");
        String r = s.nextLine(); // r: rule
        
        // Split the input into non-terminal and alternatives
        String[] p = r.split("->"); // p: parts (production parts)
        String n = p[0].trim();     // n: nonTerminal
        String[] a = p[1].split("\\|"); // a: alternatives
        for (int i = 0; i < a.length; i++) { // i: index
            a[i] = a[i].trim();
        }
        
        // Determine the common prefix among all alternatives
        String x = a[0]; // x: common prefix (initially set to first alternative)
        for (String y : a) { // y: each alternative
            x = commonPrefix(x, y);
        }
        
        // Create a new non-terminal and build the factored production string
        String z = n + "'"; // z: newNonTerminal
        StringBuilder b = new StringBuilder(); // b: factored production string
        for (int i = 0; i < a.length; i++) { // i: index
            String u = a[i].substring(x.length()); // u: suffix of alternative after prefix
            b.append(u.isEmpty() ? "ε" : u)
             .append(i < a.length - 1 ? " | " : "");
        }
        
        // Display the factored grammar productions
        System.out.println(n + " -> " + x + z);
        System.out.println(z + " -> " + b);
        
        // Ask if the user wants to continue and repeat if they say yes
        System.out.println("Do you want to continue? (yes/no):");
        if (s.nextLine().equalsIgnoreCase("yes")) {
            main(args);
            return;
        }
        s.close();
    }
    
    // Function to compute the common prefix of two strings
    static String commonPrefix(String x, String y) { // x: first string, y: second string
        int i = 0, l = Math.min(x.length(), y.length()); // i: index, l: minimum length of x and y
        while (i < l && x.charAt(i) == y.charAt(i)) {
            i++;
        }
        return x.substring(0, i);
    }
}
