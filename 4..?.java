import java.util.*;
public class Main {
 public static void main(String[] args) {
  Scanner s = new Scanner(System.in);
  System.out.println("Enter shorthand production rule (format: nt->alpha#beta1|beta2$gamma1|gamma2):");
  String input = s.nextLine();
  String[] parts = input.split("->");
  String nt = parts[0].trim();
  String[] right = parts[1].split("#");
  String alpha = right[0].trim();
  String[] betaGamma = right[1].split("\\$");
  String[] betas = betaGamma[0].split("\\|");
  String[] gammas = betaGamma.length > 1 ? betaGamma[1].split("\\|") : new String[0];
  System.out.print(nt + " -> " + alpha + " " + nt + "'");
  for (String gamma : gammas) System.out.print(" | " + gamma.trim());
  System.out.println();
  System.out.print(nt + "' -> " + String.join(" | ", betas));
  s.close();
 }
}
