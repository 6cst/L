import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter number of rules: ");
        int n = sc.nextInt();
        sc.nextLine();

        Map<String, String> rules = new LinkedHashMap<>();
        System.out.println("Enter rules (e.g., A->Ab|c):");
        for (int i = 0; i < n; i++) {
            String[] parts = sc.nextLine().split("->");
            rules.put(parts[0].trim(), parts[1].trim());
        }

        String target = new ArrayList<>(rules.keySet()).get(n - 1);
        String result = rules.get(target);
        for (String key : rules.keySet()) {
            if (!key.equals(target)) result = substitute(result, key, rules.get(key));
        }
        rules.put(target, result);

        System.out.println("Rules after substitution:");
        rules.forEach((k, v) -> System.out.println(k + " -> " + v));

        System.out.println("\nRemoving Left Recursion:");
        rules.forEach((k, v) -> removeRecursion(k, v.split("\\|")));
    }

    private static String substitute(String prod, String nonTerm, String repl) {
        List<String> results = new ArrayList<>();
        for (String opt : prod.split("\\|")) {
            if (opt.startsWith(nonTerm)) {
                String rest = opt.substring(nonTerm.length()).trim();
                for (String r : repl.split("\\|")) results.add(r.trim() + rest);
            } else results.add(opt.trim());
        }
        return String.join(" | ", results);
    }

    private static void removeRecursion(String nt, String[] prods) {
        List<String> alpha = new ArrayList<>(), beta = new ArrayList<>();
        for (String p : prods) {
            if (p.startsWith(nt)) alpha.add(p.substring(nt.length()));
            else beta.add(p);
        }
        if (alpha.isEmpty()) System.out.println(nt + " -> " + String.join(" | ", prods));
        else {
            String newNt = nt + "'";
            beta.replaceAll(b -> b + newNt);
            alpha.replaceAll(a -> a + newNt);
            System.out.println(nt + " -> " + String.join(" | ", beta));
            System.out.println(newNt + " -> " + String.join(" | ", alpha) + " | ε");
        }
    }
}
