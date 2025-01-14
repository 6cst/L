import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter number of productions: ");
        int n = sc.nextInt();
        sc.nextLine();

        List<String> nonTerminals = new ArrayList<>(), rules = new ArrayList<>();
        System.out.println("Enter productions (e.g., A->Ab|c):");
        while (n-- > 0) {
            String[] parts = sc.nextLine().split("->");
            nonTerminals.add(parts[0].trim());
            rules.add(parts[1]);
        }

        for (int i = 0; i < nonTerminals.size(); i++)
            removeRecursion(nonTerminals.get(i), rules.get(i).split("\\|"));
    }

    static void removeRecursion(String nt, String[] prods) {
        List<String> alpha = new ArrayList<>(), beta = new ArrayList<>();
        for (String p : prods) {
            if (p.startsWith(nt)) alpha.add(p.substring(nt.length()));
            else beta.add(p);
        }
        if (alpha.isEmpty()) {
            System.out.println(nt + "->" + String.join("|", prods));
        } else {
            String newNt = nt + "'";
            beta.replaceAll(b -> b + newNt);
            alpha.replaceAll(a -> a + newNt);
            System.out.println(nt + "->" + String.join("|", beta));
            System.out.println(newNt + "->" + String.join("|", alpha) + "|ε");
        }
    }
}
