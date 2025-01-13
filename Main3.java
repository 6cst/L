import java.util.*;

public class Main3 {
    public static void main(String[] a) {
        var s = new Scanner(System.in);
        System.out.println("Enter number of productions:");
        int n = s.nextInt(); s.nextLine();
        var nts = new ArrayList<String>();
        var p = new ArrayList<List<String>>();
        for (int i = 0; i < n; i++) {
            var t = s.nextLine().split("->");
            nts.add(t[0]); p.add(List.of(t[1].split("\\|")));
        }
        if (n == 1) r(nts.get(0), p.get(0));
        else i(nts, p);
    }

    static void r(String nt, List<String> ps) {
        var a = new ArrayList<String>();
        var b = new ArrayList<String>();
        ps.forEach(x -> (x.startsWith(nt) ? a : b).add(x));
        System.out.println(nt + "->" + (a.isEmpty() ? String.join("|", ps) : String.join(nt + "'|", b) + nt + "'"));
        if (!a.isEmpty()) System.out.println(nt + "'->" + String.join(nt + "'|", a).substring(nt.length()) + "|ε");
    }

    static void i(List<String> nts, List<List<String>> ps) {
        System.out.println(nts.get(0) + "->" + String.join("|", ps.get(0)));
        var np = new ArrayList<String>();
        ps.get(1).forEach(x -> {
            if (x.startsWith("S")) ps.get(0).forEach(y -> np.add(y + x.substring(1)));
            else np.add(x);
        });
        r(nts.get(1), np);
    }
}
