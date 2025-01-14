import java.util.*;

public class Main {
    public static void main(String[] a) {
        var s = new Scanner(System.in);
        var r = new ArrayList<Map<String, String>>();
        System.out.println("Enter number of productions:");
        int n = s.nextInt(); s.nextLine();
        for (int i = 1; i <= n; i++) {
            var m = new LinkedHashMap<String, String>();
            System.out.println("Enter production" + i + ":");
            f(new P(s.nextLine()), m, r);
            r.add(new LinkedHashMap<>(m));
        }
        System.out.println("\nFinal Production Rules:");
        r.forEach(x -> x.values().forEach(System.out::println));
    }

    static class P {
        String n; List<String> l;
        P(String p) { var t = p.split("->"); n = t[0]; l = List.of(t[1].split("\\|")); }
    }

    static void f(P p, Map<String, String> m, List<Map<String, String>> r) {
        var g = new HashMap<Character, List<String>>();
        p.l.forEach(x -> g.computeIfAbsent(x.charAt(0), k -> new ArrayList<>()).add(x));
        g.forEach((k, v) -> {
            if (v.size() < 2) m.put(p.n, p.n + "->" + String.join("|", p.l));
            else {
                String c = cp(v), nn = p.n + "'";
                List<String> b = new ArrayList<>(), rm = new ArrayList<>(p.l);
                v.forEach(x -> b.add(x.substring(c.length()).isEmpty() ? "ε" : x.substring(c.length())));
                rm.removeAll(v);
                m.put(p.n, p.n + "->" + c + nn + (rm.isEmpty() ? "" : "|" + String.join("|", rm)));
                m.put(nn, nn + "->" + String.join("|", b));
                f(new P(nn + "->" + String.join("|", b)), m, r);
            }
        });
    }

    static String cp(List<String> l) {
        String p = l.get(0);
        for (String s : l) while (!s.startsWith(p)) p = p.substring(0, p.length() - 1);
        return p;
    }
}
