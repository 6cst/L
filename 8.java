Enter number of production rules: 1
Enter production rules (format: Nonterminal->production1|production2|...):
E->E + E|E x E|id
Enter string to validate: id + id x id
Enter operators separated by space: id + x $
Enter the operator precedence table:
For each row, enter 4 values separated by space.
Row for operator 'id': - > > >
Row for operator '+': < - < >
Row for operator 'x': < > - >
Row for operator '$': < < < -

Operator Precedence Table:
        id      +       x       $
id      -       >       >       >
+       <       -       <       >
x       <       >       -       >
$       <       <       <       -

After inserting '$' at both ends:
$ id + id x id $

After inserting precedence operators between symbols:
$ < id > + < id > x < id > $

Parsing process:
$ < id > + < id > x < id > $
$ E + < id > x < id > $
$ E + E x < id > $
$ E + E x E $

Enter the starting nonterminal: E

After removing starting nonterminal (E):
$ + x $

Operator-only string after re-inserting precedence markers:
$ < + < x > $
$ < +  $
$ < +   > $
$  $

String Accepted.
-------------------------------------
import java.util.*;
import java.util.regex.*;

public class DynamicOperatorPrecedenceParser {
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        // 1. Read production rules.
        System.out.print("Enter number of production rules: ");
        int numRules = Integer.parseInt(sc.nextLine());
        Map<String, List<String>> prodRules = new LinkedHashMap<>();
        System.out.println("Enter production rules (format: Nonterminal->production1|production2|...):");
        for (int i = 0; i < numRules; i++) {
            String rule = sc.nextLine().trim();
            String[] parts = rule.split("->");
            if (parts.length != 2) {
                System.out.println("Invalid rule format. Skipping rule.");
                continue;
            }
            String lhs = parts[0].trim();
            String[] alternatives = parts[1].split("\\|");
            List<String> alts = new ArrayList<>();
            for (String alt : alternatives) {
                alts.add(alt.trim());
            }
            prodRules.put(lhs, alts);
        }
        
        // 2. Read input string.
        System.out.print("Enter string to validate: ");
        String inputStr = sc.nextLine().trim();
        
        // 3. Read operators (terminals).
        System.out.print("Enter operators separated by space: ");
        String opLine = sc.nextLine().trim();
        String[] ops = opLine.split("\\s+");
        int opCount = ops.length;
        
        // 4. Read operator precedence table.
        String[][] opMatrix = new String[opCount][opCount];
        System.out.println("Enter the operator precedence table:");
        System.out.println("For each row, enter " + opCount + " values separated by space.");
        for (int i = 0; i < opCount; i++) {
            System.out.print("Row for operator '" + ops[i] + "': ");
            String[] rowVals = sc.nextLine().trim().split("\\s+");
            if (rowVals.length != opCount) {
                System.out.println("Invalid number of values. Exiting.");
                return;
            }
            for (int j = 0; j < opCount; j++) {
                opMatrix[i][j] = rowVals[j];
            }
        }
        
        // 5. Print operator precedence table.
        System.out.println("\nOperator Precedence Table:");
        System.out.print("\t");
        for (String op : ops) {
            System.out.print(op + "\t");
        }
        System.out.println();
        for (int i = 0; i < opCount; i++) {
            System.out.print(ops[i] + "\t");
            for (int j = 0; j < opCount; j++) {
                System.out.print(opMatrix[i][j] + "\t");
            }
            System.out.println();
        }
        
        // 6. Augment the input string with '$' at both ends.
        String augmented = "$ " + inputStr + " $";
        System.out.println("\nAfter inserting '$' at both ends:");
        System.out.println(augmented);
        
        // 7. Insert precedence markers between adjacent tokens.
        String[] tokens = augmented.split("\\s+");
        StringBuilder strWithPrec = new StringBuilder();
        strWithPrec.append(tokens[0]);
        for (int i = 0; i < tokens.length - 1; i++) {
            String t1 = tokens[i];
            String t2 = tokens[i + 1];
            String relation = "";
            int idx1 = indexOf(ops, t1);
            int idx2 = indexOf(ops, t2);
            if (idx1 != -1 && idx2 != -1) {
                relation = opMatrix[idx1][idx2];
            }
            strWithPrec.append(" " + relation + " " + t2);
        }
        String fullString = strWithPrec.toString();
        System.out.println("\nAfter inserting precedence operators between symbols:");
        System.out.println(fullString);
        
        // 8. Phase 1 reduction: Reduce handles (like < id >) to nonterminals.
        String current = fullString;
        System.out.println("\nParsing process:");
        System.out.println(current);
        boolean phase1 = true;
        while (phase1) {
            boolean replaced = false;
            // Look for the leftmost handle of the form "< ... >"
            Pattern p = Pattern.compile("<\\s*([^<>]+?)\\s*>");
            Matcher m = p.matcher(current);
            if (m.find()) {
                String content = m.group(1).trim();
                // Check if this content matches any production alternative.
                for (Map.Entry<String, List<String>> entry : prodRules.entrySet()) {
                    if (entry.getValue().contains(content)) {
                        String lhs = entry.getKey();
                        current = current.substring(0, m.start()) + lhs + current.substring(m.end());
                        System.out.println(current);
                        replaced = true;
                        break;
                    }
                }
            }
            if (!replaced) {
                phase1 = false;
            }
        }
        
        // 9. After Phase 1, remove all occurrences of the starting nonterminal.
        System.out.print("\nEnter the starting nonterminal: ");
        String startSymbol = sc.nextLine().trim();
        current = current.replace(startSymbol, "").replaceAll("\\s+", " ").trim();
        System.out.println("\nAfter removing starting nonterminal (" + startSymbol + "):");
        System.out.println(current);
        
        // 10. Phase 2: Process the operator-only string.
        // Re-insert precedence markers into the remaining operator string.
        String[] opTokens = current.split("\\s+");
        StringBuilder opStrBuilder = new StringBuilder();
        opStrBuilder.append(opTokens[0]);
        for (int i = 0; i < opTokens.length - 1; i++) {
            String token1 = opTokens[i];
            String token2 = opTokens[i + 1];
            String rel = "";
            int index1 = indexOf(ops, token1);
            int index2 = indexOf(ops, token2);
            if (index1 != -1 && index2 != -1) {
                rel = opMatrix[index1][index2];
            }
            opStrBuilder.append(" " + rel + " " + token2);
        }
        String opString = opStrBuilder.toString();
        System.out.println("\nOperator-only string after re-inserting precedence markers:");
        System.out.println(opString);
        
        // 11. Phase 2 reduction: Process the operator string.
        // If a handle encloses an operator, remove it.
        // Also, if a handle is incomplete (i.e. a '<' without a matching '>'),
        // add the missing '>' before the trailing '$'.
        boolean phase2 = true;
        while (phase2) {
            boolean changed = false;
            // First, try to find a complete handle of the form "< ... >"
            Pattern completeHandle = Pattern.compile("<\\s*([^<>]+?)\\s*>");
            Matcher matcher = completeHandle.matcher(opString);
            if (matcher.find()) {
                String content = matcher.group(1).trim();
                if (isOperator(content, ops)) {
                    // Remove the entire handle since it encloses an operator.
                    opString = opString.substring(0, matcher.start()) + opString.substring(matcher.end());
                    System.out.println(opString);
                    changed = true;
                    continue;
                }
            }
            // Next, check for an incomplete handle (a '<' without a matching '>').
            int pos = opString.indexOf("<");
            if (pos != -1 && opString.indexOf(">", pos) == -1) {
                // Append a '>' right before the last '$'
                int posDollar = opString.lastIndexOf("$");
                if (posDollar != -1) {
                    opString = opString.substring(0, posDollar) + " > " + opString.substring(posDollar);
                    System.out.println(opString);
                    changed = true;
                    continue;
                }
            }
            if (!changed) {
                phase2 = false;
            }
        }
        
        // 12. Final check: After removing '$' symbols, if nothing remains then accept.
        String finalStr = opString.replace("$", "").trim();
        if (finalStr.isEmpty()) {
            System.out.println("\nString Accepted.");
        } else {
            System.out.println("\nParsing Error.");
        }
        
        sc.close();
    }
    
    // Utility method to find the index of a token in an array.
    private static int indexOf(String[] arr, String target) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i].equals(target))
                return i;
        }
        return -1;
    }
    
    // Utility to check if a string is one of the operators.
    private static boolean isOperator(String s, String[] ops) {
        for (String op : ops) {
            if (op.equals(s)) {
                return true;
            }
        }
        return false;
    }
}
