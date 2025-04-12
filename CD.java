import java.util.*;
public class DFA{
  public static void main(String[]a){
    Scanner s=new Scanner(System.in);
    System.out.print("Enter string: ");
    String str=s.nextLine();
    int st=0;
    for(char c: str.toCharArray()){
      if(st==0) st = (c=='a'?1:0);
      else if(st==1) st = (c=='b'?2:(c=='a'?1:0));
      else if(st==2) st = (c=='c'?3:(c=='a'?1:0));
      else if(st==3) st = (c=='a'?1:0);
    }
    System.out.println("Result: "+(st==3?"acc":"reject"));
  }
}
------------
import java.util.*;
import java.util.regex.*;

public class Main {
    public static void main(String[] a) {
        Scanner s = new Scanner(System.in);
        System.out.print("Code: ");
        String i = s.nextLine();
        String x = "\"[^\"]*\"|\\d+(\\.\\d+)?|==|!=|<=|>=|[+\\-*/=<>]|[(),;{}\\[\\]]|[a-zA-Z_][a-zA-Z0-9_]*";
        Pattern y = Pattern.compile(x);
        Matcher z = y.matcher(i);
        int c = 0;
        while (z.find()) {
            String t = z.group();
            c++;
            if (t.matches("int|float|char|if|else|while|return|system"))
                System.out.println(t + " : Keyword");
            else if (t.matches("\"[^\"]*\""))
                System.out.println(t + " : String Literal");
            else if (t.matches("\\d+(\\.\\d+)?"))
                System.out.println(t + " : Num");
            else if (t.matches("==|!=|<=|>=|[+\\-*/=<>]"))
                System.out.println(t + " : Op");
            else if (t.matches("[(),;{}\\[\\]]"))
                System.out.println(t + " : Sep");
            else
                System.out.println(t + " : ID");
        }
        System.out.println("Tokens: " + c);
    }
}
----------------------------------------------------------------------------

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        System.out.print("Enter code: ");
        String input = s.nextLine();
        int count = 0;

        for (String token : input.split("(?=\\W)|(?<=\\W)")) {
            if (token.trim().isEmpty()) continue;
            count++;

            if (token.matches("int|float|if|else|while|for|return"))
                System.out.println(token + " : Keyword");
            else if (token.matches("\".*\""))
                System.out.println(token + " : String Literal");
            else if (token.matches("[a-zA-Z_][a-zA-Z0-9_]*"))
                System.out.println(token + " : Identifier");
            else if (token.matches("\\d+"))
                System.out.println(token + " : Number");
            else if (token.matches("==|!=|<=|>=|[+\\-*/=<>]"))
                System.out.println(token + " : Operator");
            else if (token.matches("[{}();,]"))
                System.out.println(token + " : Separator");
            else
                System.out.println(token + " : Unknown");
        }

        System.out.println("Total tokens: " + count);
    }
}
---------
import java.util.*;
public class LR {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("No. of PRules:");
        int n = sc.nextInt();
        sc.nextLine();
        List<String> nonTerminals = new ArrayList<>(), rules = new ArrayList<>();
        System.out.println("Productions: ");
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
            System.out.println("Result After Removing Left Recursion!");
            System.out.println(nt + "->" + String.join("|", beta));
            System.out.println(newNt + "->" + String.join("|", alpha) + "|ε");
        }
    }
}
// Program to the Greek letter epsilon (ε)
//public class Main {
//   public static void main(String[] args) {
//        System.out.println("\u03B5");
//   }
//}
------------
import java.util.*;

public class Main {
  public static void main(String[] args) {
    Scanner s = new Scanner(System.in);
    System.out.println("Enter production (e.g. A->alpha beta1|alpha beta2|gamma1|gamma2):");
    String pr = s.nextLine();
    String nt = pr.split("->")[0].trim();
    String[] a = pr.split("->")[1].split("\\|");
    String b = a[0].trim().split(" ")[0];
    List<String> p = new ArrayList<>(), c = new ArrayList<>();
    
    for (String d : a) {
      d = d.trim();
      if (d.startsWith(b))
        p.add(d.substring(b.length()).trim().isEmpty() ? "ε" : d.substring(b.length()).trim());
      else
        c.add(d);
    }
    
    if (p.size() < 2) {
      System.out.println(pr);
      return;
    }
    
    String newnt = nt + "'";
    System.out.println(nt + "->" + b + newnt + (c.isEmpty() ? "" : "|" + String.join("|", c)));
    System.out.println(newnt + "->" + String.join("|", p));
  }
}

-------
import java.util.*;

public class Main {
    static Map<String, List<List<String>>> a = new HashMap<>();
    static Set<String> b = new HashSet<>(), c = new HashSet<>();
    static Map<String, Set<String>> d = new HashMap<>(), e = new HashMap<>();

    public static void main(String[] f) {
        Scanner g = new Scanner(System.in);
        System.out.println("Enter Non-terminals (space separated):");
        String[] h = g.nextLine().split("\\s+");
        b.addAll(Arrays.asList(h));

        System.out.println("Enter Terminals (space separated):");
        String[] i = g.nextLine().split("\\s+");
        c.addAll(Arrays.asList(i));

        for (String j : h) {
            System.out.println("Enter number of production rules for " + j + ":");
            int k = Integer.parseInt(g.nextLine());
            List<List<String>> l = new ArrayList<>();
            while (k-- > 0) {
                System.out.println("Enter production rule (space separated symbols, use ∈ for epsilon):");
                l.add(Arrays.asList(g.nextLine().split("\\s+")));
            }
            a.put(j, l);
        }

        for (String j : b) d.put(j, F(j));
        for (String j : h) System.out.println("First(" + j + ") = " + fmt(d.get(j)));

        for (String j : b) e.put(j, new HashSet<>());
        e.get(h[0]).add("$");

        boolean m = true;
        while (m) {
            m = false;
            for (String n : a.keySet()) {
                for (List<String> o : a.get(n)) {
                    for (int p = 0; p < o.size(); p++) {
                        String q = o.get(p);
                        if (!b.contains(q)) continue;
                        Set<String> r = e.get(q), s = new HashSet<>();
                        boolean t = true;
                        for (int u = p + 1; u < o.size(); u++) {
                            String v = o.get(u);
                            Set<String> w = b.contains(v) ? d.get(v) : Set.of(v);
                            s.addAll(w);
                            if (!w.contains("∈")) { t = false; break; }
                            s.remove("∈");
                        }
                        if (r.addAll(s)) m = true;
                        if (p == o.size() - 1 || t)
                            if (r.addAll(e.get(n))) m = true;
                    }
                }
            }
        }

        for (String j : h) System.out.println("Follow(" + j + ") = " + fmt(e.get(j)));
    }

    static Set<String> F(String x) {
        if (!b.contains(x)) return Set.of(x);
        Set<String> y = new HashSet<>();
        for (List<String> z : a.get(x)) {
            if (z.size() == 1 && z.get(0).equals("∈")) { y.add("∈"); continue; }
            boolean aa = true;
            for (String ab : z) {
                Set<String> ac = b.contains(ab) ? F(ab) : Set.of(ab);
                y.addAll(ac);
                if (!ac.contains("∈")) { aa = false; break; }
                y.remove("∈");
            }
            if (aa) y.add("∈");
        }
        return y;
    }

    static String fmt(Set<String> ad) {
        return "{ " + String.join(", ", ad) + " }";
    }
}

Enter Non-terminals (space separated):
S A B
Enter Terminals (space separated):
a b
Enter number of production rules for S:
2
Enter production rule (space separated symbols, use ∈ for epsilon):
A a A b
Enter production rule (space separated symbols, use ∈ for epsilon):
B b B a
Enter number of production rules for A:
1
Enter production rule (space separated symbols, use ∈ for epsilon):
∈
Enter number of production rules for B:
1
Enter production rule (space separated symbols, use ∈ for epsilon):
∈
First(S) = { a, b }
First(A) = { ∈ }
First(B) = { ∈ }
Follow(S) = { $ }
Follow(A) = { a, b }
Follow(B) = { a, b }

S → aBDh
B → cC
C → bC / ∈
D → EF
E → g / ∈
F → f / ∈
First(S) = { a }
First(B) = { c }
First(C) = { b , ∈ }
First(D) = { First(E) – ∈ } ∪ First(F) = { g , f , ∈ }
First(E) = { g , ∈ }
First(F) = { f , ∈ }
Follow Functions-
Follow(S) = { $ }
Follow(B) = { First(D) – ∈ } ∪ First(h) = { g , f , h }
Follow(C) = Follow(B) = { g , f , h }
Follow(D) = First(h) = { h }
Follow(E) = { First(F) – ∈ } ∪ Follow(D) = { f , h }
Follow(F) = Follow(D) = { h }
---------------------------------------------------------------------------------
import java.util.*;

public class Main {
    static Map<String, List<List<String>>> a = new HashMap<>();
    static Set<String> b = new HashSet<>(), c = new HashSet<>();
    static Map<String, Set<String>> d = new HashMap<>(), e = new HashMap<>();

    public static void main(String[] f) {
        Scanner g = new Scanner(System.in);
        System.out.println("Enter Non-terminals (space separated):");
        String[] h = g.nextLine().split("\\s+");
        b.addAll(Arrays.asList(h));

        System.out.println("Enter Terminals (space separated):");
        String[] i = g.nextLine().split("\\s+");
        c.addAll(Arrays.asList(i));

        for (String j : h) {
            System.out.println("Enter number of production rules for " + j + ":");
            int k = Integer.parseInt(g.nextLine());
            List<List<String>> l = new ArrayList<>();
            while (k-- > 0) {
                System.out.println("Enter production rule (space separated symbols, use 9 for epsilon):");
                l.add(Arrays.asList(g.nextLine().split("\\s+")));
            }
            a.put(j, l);
        }

        for (String j : b) d.put(j, F(j));
        for (String j : h) System.out.println("First(" + j + ") = " + fmt(d.get(j)));

        for (String j : b) e.put(j, new HashSet<>());
        e.get(h[0]).add("$");

        boolean m = true;
        while (m) {
            m = false;
            for (String n : a.keySet()) {
                for (List<String> o : a.get(n)) {
                    for (int p = 0; p < o.size(); p++) {
                        String q = o.get(p);
                        if (!b.contains(q)) continue;
                        Set<String> r = e.get(q), s = new HashSet<>();
                        boolean t = true;
                        for (int u = p + 1; u < o.size(); u++) {
                            String v = o.get(u);
                            Set<String> w = b.contains(v) ? d.get(v) : Set.of(v);
                            s.addAll(w);
                            if (!w.contains("9")) { 
                                t = false; 
                                break; 
                            }
                            s.remove("9");
                        }
                        if (r.addAll(s)) m = true;
                        if (p == o.size() - 1 || t)
                            if (r.addAll(e.get(n))) m = true;
                    }
                }
            }
        }

        for (String j : h) System.out.println("Follow(" + j + ") = " + fmt(e.get(j)));
    }

    static Set<String> F(String x) {
        if (!b.contains(x)) return Set.of(x);
        Set<String> y = new HashSet<>();
        for (List<String> z : a.get(x)) {
            if (z.size() == 1 && z.get(0).equals("9")) { 
                y.add("9"); 
                continue; 
            }
            boolean aa = true;
            for (String ab : z) {
                Set<String> ac = b.contains(ab) ? F(ab) : Set.of(ab);
                y.addAll(ac);
                if (!ac.contains("9")) { 
                    aa = false; 
                    break; 
                }
                y.remove("9");
            }
            if (aa) y.add("9");
        }
        return y;
    }

    static String fmt(Set<String> ad) {
        return "{ " + String.join(", ", ad) + " }";
    }
}

---------
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        System.out.print("Enter non-terminals: ");
        String[] N = s.nextLine().split("\\s+");
        System.out.print("Enter terminals: ");
        String[] T = s.nextLine().split("\\s+");

        String[][] M = new String[N.length][T.length];
        for (int i = 0; i < N.length; i++)
            for (int j = 0; j < T.length; j++) {
                System.out.print("M[" + N[i] + "][" + T[j] + "]: ");
                M[i][j] = s.nextLine();
                if (M[i][j].contains("|")) {
                    System.out.println("E");
                    s.close();
                    return;
                }
            }

        System.out.print("Enter input (end with $): ");
        String[] I = s.nextLine().split("\\s+");
        Stack<String> S = new Stack<>();
        S.push("$");
        S.push(N[0]);
        int p = 0;
        boolean e = false;

        while (!(S.peek().equals("$") && I[p].equals("$"))) {
            String x = S.pop();
            if (!Arrays.asList(N).contains(x)) {
                if (!x.equals(I[p])) { e = true; break; }
                p++;
            } else {
                int i = Arrays.asList(N).indexOf(x);
                int j = Arrays.asList(T).indexOf(I[p]);
                if (i == -1 || j == -1) { e = true; break; }
                String y = M[i][j];
                if (!(y.equals("ε") || y.equals("''") || y.isEmpty())) {
                    String[] z = y.contains(" ") ? y.split("\\s+") : y.split("");
                    for (int k = z.length - 1; k >= 0; k--) S.push(z[k]);
                }
            }
        }

        System.out.println(e ? "E" : "A");
        s.close();
    }
}
Enter the number of rules to skip: 5
Enter rule 1: S->aABb
Enter rule 2: A->c
Enter rule 3: A->ε
Enter rule 4: B->d
Enter rule 5: B->ε
Enter nonterminals (space separated): S A B 
Enter terminals (space separated): a b c d
Enter the parsing table entries:
Entry for nonterminal 'S', terminal 'a': aABb
Entry for nonterminal 'S', terminal 'b': 
Entry for nonterminal 'S', terminal 'c': 
Entry for nonterminal 'S', terminal 'd': 
Entry for nonterminal 'A', terminal 'a': 
Entry for nonterminal 'A', terminal 'b': ε
Entry for nonterminal 'A', terminal 'c': c
Entry for nonterminal 'A', terminal 'd': ε
Entry for nonterminal 'B', terminal 'a': 
Entry for nonterminal 'B', terminal 'b': ε
Entry for nonterminal 'B', terminal 'c': 
Entry for nonterminal 'B', terminal 'd': d
Enter input tokens (space separated, end with $): a c d b $
String Accepted


E->TX
X->+TX|9
T->FY
Y->*FY|9
F->(E)|i



TX x x TX x x
x =TX x x 9 9
FY x x FY x x
x 9 *FY x 9 9
i x x (E) x x

--------
import java.util.Scanner;

class R {
    String l, r;
    R(String l, String r) {
        this.l = l;
        this.r = r;
    }
}

public class E {
    public static void main(String[] a) {
        Scanner s = new Scanner(System.in);
        System.out.print("Enter number of rules: ");
        int n = s.nextInt();
        s.nextLine();
        R[] rs = new R[n];
        System.out.println("Enter rules (left->right):");
        for (int i = 0; i < n; i++) {
            String[] t = s.nextLine().split("->");
            rs[i] = new R(t[0], t[1]);
        }
        System.out.print("Enter input: ");
        String in = s.nextLine(), st = "";
        int i = 0;
        System.out.println("Stack\tInput\t\tAction");
        while (true) {
            if (i < in.length()) {
                char c = in.charAt(i++);
                st += c;
                System.out.printf("%s\t%s\t\tShift %c\n", st, in.substring(i), c);
            }
            boolean d = false;
            for (R r : rs) {
                int x = st.indexOf(r.r);
                if (x != -1) {
                    st = st.substring(0, x) + r.l + st.substring(x + r.r.length());
                    System.out.printf("%s\t%s\t\tReduce %s->%s\n", st, in.substring(i), r.l, r.r);
                    d = true;
                    break;
                }
            }
            if (st.equals(rs[0].l) && i == in.length()) {
                System.out.println("\nAccepted");
                break;
            }
            if (i == in.length() && !d) {
                System.out.println("\nNot Accepted");
                break;
            }
        }
        s.close();
    }
}

Enter number of rules: 3
Enter rules (left->right):
S->0S0
S->1S1
S->2
Enter input: 10201
Stack   Input           Action
1       0201            Shift 1
10      201             Shift 0
102     01              Shift 2
10S     01              Reduce S->2
10S0    1               Shift 0
1S      1               Reduce S->0S0
1S1                     Shift 1
S                       Reduce S->1S1

Accepted

Enter number of rules: 3
Enter rules (left->right):
E->E-E
E->E*E
E->i
Enter input: i-i*i
Stack   Input           Action
i       -i*i            Shift i
E       -i*i            Reduce E->i
E-      i*i             Shift -
E-i     *i              Shift i
E-E     *i              Reduce E->i
E-E*    i               Shift *
E*      i               Reduce E->E-E
E*i                     Shift i
E*E                     Reduce E->i
E                       Reduce E->E*E

Accepted

----------
import java.util.*;
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("No. of terminals: ");
        int n = sc.nextInt();
        sc.nextLine();
        System.out.print("Terminals: ");
        String t = sc.nextLine();
        String[][] p = new String[n][n];
        System.out.println("Precedence Table: ");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                p[i][j] = sc.next();
            }
        }
        System.out.println("OPERATOR PRECEDENCE TABLE:");
        System.out.print("\t");
        for (int i = 0; i < n; i++) {
            System.out.print(t.charAt(i) + "\t");
        }
        System.out.println();
        for (int i = 0; i < n; i++) {
            System.out.print(t.charAt(i) + "\t");
            for (int j = 0; j < n; j++) {
                System.out.print(p[i][j] + "\t");
            }
            System.out.println();
        }
        System.out.print("String : ");
        String inp=sc.next()+"$";
        String stk="$";
        System.out.println("STACK\t\tINPUT\t\tACTION");
        while(true){
            String c1=stk.charAt(stk.length()-1)+"";
            String c2=inp.charAt(0)+"";
            if(c1.equals("E")) c1=stk.charAt(stk.length()-2)+"";
            int i1=t.indexOf(c1);
            int i2=t.indexOf(c2);
            if (i1 == -1 || i2 == -1) {
                System.out.println("Rejected.");
                break;
            }
            if(p[i1][i2].equals("A")) {
                System.out.println("Accepted.");
                break;
            }
            if(p[i1][i2].equals("<")){
                stk+=c2;
                inp=inp.substring(1);
                System.out.println(stk+"\t\t"+inp+"\t\tSHIFT"+c2);
            }else{
                stk=stk.substring(0,stk.length()-1);
                if(!c1.equals("i")) stk=stk.substring(0,stk.length()-2);
                stk+="E";
                System.out.println(stk+"\t\t"+inp+"\t\tREDUCE");
            }
        }
    }
}

No. of terminals: 4
Terminals: +i*$
Precedence Table: 
> < < >
> > > >
> < > >
< < < A
OPERATOR PRECEDENCE TABLE:
        +       i       *       $
+       >       <       <       >
i       >       >       >       >
*       >       <       >       >
$       <       <       <       A
String : i+i*i
STACK           INPUT           ACTION
$i              +i*i$           SHIFTi
$E              +i*i$           REDUCE
$E+             i*i$            SHIFT+
$E+i            *i$             SHIFTi
$E+E            *i$             REDUCE
$E+E*           i$              SHIFT*
$E+E*i          $               SHIFTi
$E+E*E          $               REDUCE
$E+E            $               REDUCE
$E              $               REDUCE
Accepted.
-------------------------------------------------------------------------------------------

import java.util.*;

public class Main {
    public static String getTopTerminal(String stk, String terminals) {
        for (int i = stk.length() - 1; i >= 0; i--) {
            char c = stk.charAt(i);
            if (terminals.indexOf(c) != -1) return c + "";
        }
        return "$";
    }

    public static String attemptReduction(String stk) {
        if (stk.endsWith("a")) return stk.substring(0, stk.length() - 1) + "E";
        if (stk.endsWith("(E)")) return stk.substring(0, stk.length() - 3) + "E";
        if (stk.endsWith("E,E")) return stk.substring(0, stk.length() - 3) + "E";
        return stk;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("No. of terminals: ");
        int n = sc.nextInt();
        sc.nextLine();
        System.out.print("Terminals: ");
        String terminals = sc.nextLine();

        String[][] table = new String[n][n];
        System.out.println("Precedence Table: ");
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                table[i][j] = sc.next();

        System.out.println("OPERATOR PRECEDENCE TABLE:");
        System.out.print("\t");
        for (int i = 0; i < n; i++)
            System.out.print(terminals.charAt(i) + "\t");
        System.out.println();
        for (int i = 0; i < n; i++) {
            System.out.print(terminals.charAt(i) + "\t");
            for (int j = 0; j < n; j++)
                System.out.print(table[i][j] + "\t");
            System.out.println();
        }

        System.out.print("String : ");
        String input = sc.next() + "$";
        String stack = "$";

        System.out.println("STACK\t\tINPUT\t\tACTION");
        while (true) {
            String top = getTopTerminal(stack, terminals);
            String next = input.charAt(0) + "";
            int i = terminals.indexOf(top);
            int j = terminals.indexOf(next);
            if (i == -1 || j == -1) {
                System.out.println("Rejected.");
                break;
            }
            String rel = table[i][j];
            if (rel.equals("A")) {
                System.out.println("Accepted.");
                break;
            } else if (rel.equals("<") || rel.equals("=")) {
                stack += next;
                input = input.substring(1);
                System.out.println(stack + "\t\t" + input + "\t\tSHIFT " + next);
            } else if (rel.equals(">")) {
                String reduced = attemptReduction(stack);
                if (reduced.equals(stack)) {
                    System.out.println("Rejected.");
                    break;
                }
                stack = reduced;
                System.out.println(stack + "\t\t" + input + "\t\tREDUCE");
            } else {
                System.out.println("Rejected.");
                break;
            }
        }
        sc.close();
    }
}

No. of terminals: 5
Terminals: (a,)$
Precedence Table: 
< < < = -
> > > > >
< < < > >
> > > > >
< < < < A
OPERATOR PRECEDENCE TABLE:
        (       a       ,       )       $
(       <       <       <       =       -
a       >       >       >       >       >
,       <       <       <       >       >
)       >       >       >       >       >
$       <       <       <       <       A
String : (a,(a,a))
STACK           INPUT           ACTION
$(              a,(a,a))$               SHIFT (
$(a             ,(a,a))$                SHIFT a
$(E             ,(a,a))$                REDUCE
$(E,            (a,a))$         SHIFT ,
$(E,(           a,a))$          SHIFT (
$(E,(a          ,a))$           SHIFT a
$(E,(E          ,a))$           REDUCE
$(E,(E,         a))$            SHIFT ,
$(E,(E,a                ))$             SHIFT a
$(E,(E,E                ))$             REDUCE
$(E,(E          ))$             REDUCE
$(E,(E)         )$              SHIFT )
$(E,E           )$              REDUCE
$(E             )$              REDUCE
$(E)            $               SHIFT )
$E              $               REDUCE
Accepted.
------------------
import java.util.*;
class Main{
  public static void main(String[] a){
    Scanner s=new Scanner(System.in);
    System.out.print("Enter the size of stack[MAX=100]:  ");
    int n=s.nextInt(), top=-1; int[] st=new int[n];
    System.out.println("\n   Stack Operations:\n   --------------------------\n   1. Push\n   2. Pop\n   3. Display\n   4. EXIT");
    while(true){
      System.out.print("\nEnter your choice:  ");
      switch(s.nextInt()){
        case 1:
          System.out.print("Enter a value to be pushed:  ");
          st[++top]=s.nextInt();
          break;
        case 2:
          if(top<0) System.out.println("Stack Underflow");
          else System.out.println("The popped element is "+st[top--]);
          break;
        case 3:
          if(top<0) System.out.println("Stack is empty");
          else {
            System.out.println("\nThe elements in the stack are:");
            for(int i=top;i>=0;i--) System.out.println(st[i]);
            System.out.println("\nSelect next choice");
          }
          break;
        case 4:
          System.out.println("EXIT");
          return;
        default:
          System.out.println("Invalid choice");
      }
    }
  }
}
------------
import java.util.*;

class Main {
    static int p(char c) {
        return c == '+' || c == '-' ? 1 : c == '*' || c == '/' ? 2 : c == '^' ? 3 : -1;
    }

    public static void main(String[] a) {
        Scanner sc = new Scanner(System.in);
        String i = sc.nextLine(), o = "";
        Stack<Character> ops = new Stack<>();

        for (char c : i.toCharArray()) {
            if (Character.isLetterOrDigit(c)) {
                o += c;
            } else if (c == '(') {
                ops.push(c);
            } else if (c == ')') {
                while (!ops.isEmpty() && ops.peek() != '(') o += ops.pop();
                if (!ops.isEmpty()) ops.pop();
            } else {
                while (!ops.isEmpty() && p(c) <= p(ops.peek())) o += ops.pop();
                ops.push(c);
            }
        }

        while (!ops.isEmpty()) o += ops.pop();

        Stack<String> stk = new Stack<>();
        int t = 0;
        for (char c : o.toCharArray()) {
            if (p(c) == -1) {
                stk.push(c + "");
            } else {
                String b = stk.pop(), a1 = stk.pop(), r = "t" + t++;
                System.out.println(r + " = " + a1 + " " + c + " " + b);
                stk.push(r);
            }
        }
    }
}

a*b+c/d-e/f+g*h 
