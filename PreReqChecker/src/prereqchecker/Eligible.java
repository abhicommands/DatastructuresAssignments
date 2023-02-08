package prereqchecker;

import java.util.*;

public class Eligible {
    public static void main(String[] args) {

        if ( args.length < 3 ) {
            StdOut.println("Execute: java -cp bin prereqchecker.Eligible <listacency list INput file> <eligible INput file> <eligible OUTput file>");
            return;
        }
        StdIn.setFile(args[0]);
        int a = Integer.parseInt(StdIn.readLine());
        ArrayList<ArrayList<String>> list = new ArrayList<ArrayList<String>>();
        for(int i = 0; i < a ; i++){
            ArrayList<String> t = new ArrayList<String>();
            t.add(StdIn.readLine());
            list.add(t);
        }
        a = Integer.parseInt(StdIn.readLine());
        for(int i = 0; i < a; i++){
            list.get(find(StdIn.readString(),list)).add(StdIn.readString());
        }
        Set<String> classes = new HashSet<>();
        StdIn.setFile(args[1]);
        a = Integer.parseInt(StdIn.readLine());
        for(int i = 0; i < a; i++){
            addAllprereq(StdIn.readLine(), classes, list);
        }
        StdOut.setFile(args[2]);
        for(int i = 0; i < list.size(); i++){
            if(!classes.contains(list.get(i).get(0))){
                int z = 1;
                boolean temp = true;
                while(z < list.get(i).size()){
                    if(!classes.contains(list.get(i).get(z))){
                        temp = false;
                    }
                    z++;
                }
                if(temp){
                    StdOut.println(list.get(i).get(0));
                }
            }
        }
	    
    }

    private static void addAllprereq(String course, Set<String> ClassesTaken, ArrayList<ArrayList<String>> list){
          if(list.get(find(course,list)).size() == 1){
              if(!ClassesTaken.contains(course)){
                ClassesTaken.add(course);
              }
          }
          else{
            if(!ClassesTaken.contains(course)){
                ClassesTaken.add(course);
              }
              for(int i = 1;i < list.get(find(course,list)).size(); i++){
                addAllprereq(list.get(find(course,list)).get(i), ClassesTaken, list);
              }
          }
    }

    private static int find(String course, ArrayList<ArrayList<String>> list){
        for(int i = 0; i < list.size(); i++){
            if(list.get(i).get(0).equals(course)){
                return i;
            }
        }
        return -1;
    }

}
