import java.util.*;

public class Main{

  static class R{String l;List<String> r;R(String ll,List<String> rr){l=ll;r=rr;} }

  public static void main(String[]a){
    Scanner s=new Scanner(System.in);
    int n=Integer.parseInt(s.nextLine().trim());
    List<R> p=new ArrayList<>();
    for(int i=0;i<n;i++){
      String[]t=s.nextLine().split("->");
      p.add(new R(t[0].trim(),Arrays.asList(t[1].trim().split(" "))));
    }
    LinkedList<String> in=new LinkedList<>(Arrays.asList(s.nextLine().split(" "))); in.add("$");
    Stack<String> st=new Stack<>(); st.push("$");
    System.out.format("%-24s %-24s %-24s\n","Stack","Input Buffer","Action");
    while(true){
      String stRep=(st.size()>1?"$ "+String.join(" ",st.subList(1,st.size()))+" $":"$");
      String inRep="$ "+String.join(" ",in)+" $";
      System.out.format("%-24s %-24s ",stRep,inRep);
      if(st.size()==2 && st.get(1).equals(p.get(0).l) && in.peek().equals("$")){
        System.out.println("Accept"); break;}
      boolean rdc=false;
      for(R pr:p){
        int sz=pr.r.size();
        if(st.size()>=sz+1 && st.subList(st.size()-sz,st.size()).equals(pr.r)){
          for(int j=0;j<sz;j++) st.pop();
          st.push(pr.l);
          System.out.println("Reduce "+pr.l+"->"+String.join(" ",pr.r));
          rdc=true; break;}
      }
      if(rdc) continue;
      if(!in.isEmpty()){
        String tk=in.poll();
        if(tk.equals("$")) continue;
        st.push(tk);
        System.out.println("Shift");
      }else{System.out.println("Error");break;}
    }
  }
}
