import java.util.*;

public class Main {
    static Map<String, List<String>> grm = new HashMap<>();
    
    public static void main(String[] args) {
        Scanner scn = new Scanner(System.in);
        System.out.print("Enter the number of production rules: ");
        int n = scn.nextInt(); scn.nextLine();
        
        System.out.println("Enter each production rule in the format NonTerminal -> Production:");
        while (n-- > 0) {
            String[] rle = scn.nextLine().split("->");
            grm.put(rle[0].trim(), Arrays.asList(rle[1].trim().split("\\|")));
        }
        
        System.out.print("Enter the string to parse: ");
        String tgt = scn.nextLine();
        
        if (!cnd("S", tgt)) {
            System.out.println("\nString cannot be formed");
            return;
        }
        
        System.out.println("\nDerived String: " + tgt);
        System.out.println("\nLeftmost Derivation Steps:");
        der(tgt, true).forEach(System.out::println);
        
        System.out.println("\nRightmost Derivation Steps:");
        der(tgt, false).forEach(System.out::println);
    }
    
    private static boolean cnd(String cur, String tgt) {
        if (cur.equals(tgt)) return true;
        if (cur.length() > tgt.length()) return false;
        
        for (int i = 0; i < cur.length(); i++) {
            if (Character.isUpperCase(cur.charAt(i))) {
                String nT = cur.substring(i, i + 1);
                for (String prd : grm.getOrDefault(nT, Collections.emptyList())) {
                    if (cnd(cur.substring(0, i) + prd + cur.substring(i + 1), tgt)) 
                        return true;
                }
                return false;
            }
        }
        return false;
    }
    
    private static List<String> der(String tgt, boolean lft) {
        String cur = "S";
        List<String> stp = new ArrayList<>(List.of(cur));
        
        while (!cur.equals(tgt)) {
            for (String nT : grm.keySet()) {
                int idx = lft ? cur.indexOf(nT) : cur.lastIndexOf(nT);
                if (idx == -1) continue;
                
                for (String prd : grm.get(nT)) {
                    String nxt = cur.substring(0, idx) + prd + cur.substring(idx + 1);
                    if (vld(nxt, tgt)) {
                        cur = nxt;
                        stp.add(cur);
                        break;
                    }
                }
                break;
            }
        }
        return stp;
    }
    
    private static boolean vld(String cur, String tgt) {
        if (cur.length() > tgt.length()) return false;
        for (int i = 0; i < cur.length(); i++)
            if (Character.isLowerCase(cur.charAt(i)) && cur.charAt(i) != tgt.charAt(i)) 
                return false;
        return true;
    }
}
