import java.util.*;
public class Main{
 static class R{
     String l,r; 
     R(String l,String r){
         this.l=l; 
         this.r=r;
     }
 }
 
 public static void main(String[]a){
  Scanner s=new Scanner(System.in);
  System.out.print("Enter Number of rules: "); 
  int n=Integer.parseInt(s.nextLine());
  List<R> rules=new ArrayList<>(); 
  System.out.println("Enter Rules (format: LHS->RHS):");
  for(int i=0;i<n;i++){
    String[] p=s.nextLine().replaceAll("\\s*->\\s*","->").split("->");
    if(p.length<2) continue; 
    rules.add(new R(p[0].trim(),p[1].trim()));
  }
  
  System.out.print("Enter Input String (ending with $): ");
  String input=s.nextLine(); 
  if(!input.endsWith("$")) 
  input+="$";
  String stack="$"; 
  System.out.println("\n------------------------------------------------------------");
  System.out.printf("%-20s%-20s%-20s\n","Stack","Input Buffer","Parsing Action");
  System.out.println("------------------------------------------------------------");
  String start=rules.get(0).l;
  
  while(true){
    String act="";
    if(input.equals("$") && stack.equals("$"+start)){
      act="Accept"; 
      System.out.printf("%-20s%-20s%-20s\n",stack,input,act);
      System.out.println("Accepted"); 
      break;
    }
    
    boolean done=false;
    for(R r:rules){
      if(stack.endsWith(r.r)){
        stack=stack.substring(0,stack.length()-r.r.length())+r.l;
        act="Reduce by "+r.l+"->"+r.r; 
        done=true; 
        break;
      }
    }
    
    if(!done){
      if(!input.equals("$") && !input.isEmpty()){
        char c=input.charAt(0); 
        input=input.substring(1);
        stack+=c; 
        act="Shift "+c; 
        done=true;
      } else { act="Parsing Error"; 
        System.out.printf("%-20s%-20s%-20s\n",stack,input,act); 
        break; 
      }
    }
    System.out.printf("%-20s%-20s%-20s\n",stack,input,act);
  }
  s.close();
 }
}

 S –> S + S 
 S –> S * S 
 S –> id
 id + id + id

   Example 2 – Consider the grammar 
        E –> 2E2 
        E –> 3E3 
        E –> 4 
Perform Shift Reduce parsing for input string “32423”. 

Example 3 – Consider the grammar
                         S –>  ( L ) | a        
                         L –>  L , S | S    
Perform Shift Reduce parsing for input string ( a, ( a, a ) ) 
  

  


