import java.util.*;

public class GrammarCalculator {
    // Grammar: non-terminal -> list of productions (each production is a list of symbols)
    static Map<String, List<List<String>>> grammar = new HashMap<>();
    static Set<String> nonTerminals = new HashSet<>();
    static Set<String> terminals = new HashSet<>();
    static Map<String, Set<String>> firstSets = new HashMap<>();
    static Map<String, Set<String>> followSets = new HashMap<>();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Input non-terminals
        System.out.println("Enter Non-terminals (space separated):");
        String[] ntArray = sc.nextLine().split("\\s+");
        for (String nt : ntArray) {
            nonTerminals.add(nt);
        }

        // Input terminals
        System.out.println("Enter Terminals (space separated):");
        String[] tArray = sc.nextLine().split("\\s+");
        for (String t : tArray) {
            terminals.add(t);
        }

        // Input productions for each non-terminal (in reverse order)
        for (int i = ntArray.length - 1; i >= 0; i--) {
            String nt = ntArray[i];
            System.out.println("Enter number of production rules for " + nt + ":");
            int numProd = sc.nextInt();
            sc.nextLine();
            List<List<String>> prodList = new ArrayList<>();
            for (int j = 0; j < numProd; j++) {
                System.out.println("Enter production rule (space separated symbols, use ∈ for epsilon):");
                String[] prodSymbols = sc.nextLine().split("\\s+");
                prodList.add(Arrays.asList(prodSymbols));
            }
            grammar.put(nt, prodList);
        }

        // Compute FIRST sets for all non-terminals
        for (String nt : nonTerminals) {
            firstSets.put(nt, computeFirst(nt));
        }
        for (String nt : ntArray) {
            System.out.println("First(" + nt + ") = " + formatSet(firstSets.get(nt)));
        }

        // Initialize FOLLOW sets for all non-terminals
        for (String nt : nonTerminals) {
            followSets.put(nt, new HashSet<>());
        }
        // Rule 1: For the start symbol, add '$'
        followSets.get(ntArray[0]).add("$");

        // Iteratively compute FOLLOW sets until no change
        boolean changed = true;
        while (changed) {
            changed = false;
            for (String A : grammar.keySet()) {
                for (List<String> production : grammar.get(A)) {
                    for (int i = 0; i < production.size(); i++) {
                        String B = production.get(i);
                        if (nonTerminals.contains(B)) {
                            Set<String> followB = followSets.get(B);
                            // Compute FIRST(beta) for symbols after B
                            Set<String> firstBeta = new HashSet<>();
                            boolean betaDerivesEpsilon = true;
                            for (int j = i + 1; j < production.size(); j++) {
                                String symbol = production.get(j);
                                Set<String> firstSymbol = nonTerminals.contains(symbol)
                                        ? firstSets.get(symbol)
                                        : new HashSet<>(Arrays.asList(symbol));
                                firstBeta.addAll(firstSymbol);
                                if (!firstSymbol.contains("∈")) {
                                    betaDerivesEpsilon = false;
                                    break;
                                }
                                firstBeta.remove("∈");
                            }
                            // Add FIRST(beta) (excluding ∈) to FOLLOW(B)
                            if (!firstBeta.isEmpty()) {
                                if (followB.addAll(firstBeta)) {
                                    changed = true;
                                }
                            }
                            // If B is at end or beta derives ∈, add FOLLOW(A) to FOLLOW(B)
                            if (i == production.size() - 1 || betaDerivesEpsilon) {
                                if (followB.addAll(followSets.get(A))) {
                                    changed = true;
                                }
                            }
                        }
                    }
                }
            }
        }
        for (String nt : ntArray) {
            System.out.println("Follow(" + nt + ") = " + formatSet(followSets.get(nt)));
        }
        sc.close();
    }

    // Recursive computation of FIRST for a symbol
    static Set<String> computeFirst(String symbol) {
        Set<String> first = new HashSet<>();
        if (!nonTerminals.contains(symbol)) {
            first.add(symbol);
            return first;
        }
        for (List<String> production : grammar.get(symbol)) {
            // If production is epsilon
            if (production.size() == 1 && production.get(0).equals("∈")) {
                first.add("∈");
                continue;
            }
            boolean allEpsilon = true;
            for (String sym : production) {
                Set<String> firstSym = nonTerminals.contains(sym) ? computeFirst(sym)
                        : new HashSet<>(Arrays.asList(sym));
                first.addAll(firstSym);
                if (!firstSym.contains("∈")) {
                    allEpsilon = false;
                    break;
                } else {
                    first.remove("∈");
                }
            }
            if (allEpsilon) {
                first.add("∈");
            }
        }
        return first;
    }

    // Format a set into a string with curly braces
    static String formatSet(Set<String> set) {
        StringBuilder sb = new StringBuilder();
        sb.append("{ ");
        boolean first = true;
        for (String s : set) {
            if (!first) {
                sb.append(", ");
            }
            sb.append(s);
            first = false;
        }
        sb.append(" }");
        return sb.toString();
    }
}
