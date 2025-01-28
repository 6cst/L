import java.io.*;
import java.util.*;

public class Main {
    static char a[], b[];
    static int c, d;
    static String e[][], f[], g[];

    public static void main(String args[]) throws IOException {
        BufferedReader h = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter the non-terminals");
        String i = h.readLine();
        c = i.length();
        a = i.toCharArray();
        System.out.println("Enter the terminals");
        String j = h.readLine();
        d = j.length();
        b = j.toCharArray();
        System.out.println("Specify the grammar(Enter 9 for epsilon production)");
        e = new String[c][];
        for (int k = 0; k < c; k++) {
            System.out.println("Enter the number of productions for " + a[k]);
            int l = Integer.parseInt(h.readLine());
            e[k] = new String[l];
            System.out.println("Enter the productions");
            for (int m = 0; m < l; m++) e[k][m] = h.readLine();
        }
        f = new String[c];
        for (int k = 0; k < c; k++) f[k] = first(k);
        System.out.println("First Set");
        for (int k = 0; k < c; k++) System.out.println(removeDuplicates(f[k]));
        g = new String[c];
        for (int k = 0; k < c; k++) g[k] = follow(k);
        System.out.println("Follow Set");
        for (int k = 0; k < c; k++) System.out.println(removeDuplicates(g[k]));
    }

static String first(int k) {
    StringBuilder n = new StringBuilder();
    for (String o : e[k]) {
        for (int p = 0; p < o.length(); p++) {
            char q = o.charAt(p);
            boolean r = false;
            for (int s = 0; s < c; s++) {
                if (q == a[s]) {
                    String t = first(s);
                    n.append(t.equals("9") ? "" : t);
                    r = true;
                    break;
                }
            }
            if (!r) {
                n.append(q);
                break;
            }
            if (r && n.toString().contains("9")) continue;
        }
    }
    return n.toString();
}

static String follow(int k) {
    StringBuilder n = new StringBuilder();
    if (k == 0) n.append("$");
    for (int l = 0; l < c; l++) {
        for (String o : e[l]) {
            char[] p = o.toCharArray();
            for (int q = 0; q < p.length; q++) {
                if (p[q] == a[k]) {
                    if (q == p.length - 1) {
                        n.append(l < k ? g[l] : "");
                    } else {
                        boolean r = false;
                        for (int s = 0; s < c; s++) {
                            if (p[q + 1] == a[s]) {
                                for (char t : f[s].toCharArray()) {
                                    n.append(t == '9' ? (q + 1 == p.length - 1 ? follow(l) : follow(s)) : String.valueOf(t));
                                }
                                r = true;
                            }
                        }
                        if (!r) n.append(p[q + 1]);
                    }
                }
            }
        }
    }
    return n.toString();
}

    static String removeDuplicates(String str) {
        boolean[] seen = new boolean[256];
        StringBuilder sb = new StringBuilder();
        for (char c : str.toCharArray()) {
            if (!seen[c]) {
                seen[c] = true;
                sb.append(c);
            }
        }
        return sb.toString();
    }
}
