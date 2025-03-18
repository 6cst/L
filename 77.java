import java.util.Scanner;
public class Main { // SRP: Shift Reduce Parser
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in); // s: Scanner for input
        System.out.print("Enter the number of production rules: ");
        int n = Integer.parseInt(s.nextLine().trim()); // n: number of productions
        
        String[] L = new String[n]; // L: left-hand sides of productions
        String[] R = new String[n]; // R: right-hand sides of productions
        System.out.println("Enter the production rules (in the form 'left->right'):");
        for (int i = 0; i < n; i++) { // loop over production rules
            String[] t = s.nextLine().split("->"); // t: temporary array for rule parts
            L[i] = t[0].trim(); // store left-hand side
            R[i] = t[1].trim(); // store right-hand side
        }
        
        System.out.print("Enter the input string: ");
        String I = s.nextLine().trim(); // I: input string
        
        StringBuilder st = new StringBuilder("$"); // st: stack initialized with '$'
        String b = I + "$"; // b: input buffer with trailing '$'
        System.out.println("\nStack\tInputBuffer\tAction");
        System.out.println(st + "\t" + b + "\tInitial");
        
        int p = 0; // p: pointer for b (input buffer)
        String start = L[0]; // start: starting symbol from the first production
        
        // Process each character in the buffer (except the final '$')
        while (p < b.length() - 1) {
            char c = b.charAt(p); // c: current character from buffer
            st.append(c); // shift: add c to the stack
            p++; // move pointer forward
            System.out.println(st + "\t" + b.substring(p) + "\tShift " + c);
            
            boolean red; // red: flag to check if a reduction occurs
            do {
                red = false;
                // Try each production for possible reduction
                for (int i = 0; i < n; i++) {
                    int idx = st.indexOf(R[i]); // idx: index of R[i] in stack
                    if (idx != -1) { // if production's RHS is found in the stack
                        System.out.println(st + "\t" + b.substring(p) + "\tReduce " + L[i] + "->" + R[i]);
                        st.replace(idx, idx + R[i].length(), L[i]); // perform reduction
                        System.out.println(st + "\t" + b.substring(p) + "\tAfter Reduction");
                        red = true; // indicate that a reduction happened
                        break; // break to re-check all productions from the beginning
                    }
                }
            } while (red); // continue reducing as long as possible
        }
        
        // Final reduction phase after all shifts
        while (true) {
            boolean flag = false;
            for (int i = 0; i < n; i++) {
                int idx = st.indexOf(R[i]);
                if (idx != -1) {
                    System.out.println(st + "\t$\tReduce " + L[i] + "->" + R[i]);
                    st.replace(idx, idx + R[i].length(), L[i]);
                    System.out.println(st + "\t$\tAfter Reduction");
                    flag = true;
                    break;
                }
            }
            if (!flag) break;
        }
        
        // Acceptance condition: stack should equal "$" + start symbol, and buffer should be "$"
        System.out.println(st.toString().equals("$" + start) ? "Accepted!" : "Error!");
        s.close();
    }
}
