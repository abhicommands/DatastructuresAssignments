package prereqchecker;
import java.util.*;

public class ValidPrereq {
    public static void main(String[] args) {

        if ( args.length < 3 ) {
            StdOut.println("Execute: java -cp bin prereqchecker.ValidPrereq <listacency list INput file> <valid prereq INput file> <valid prereq OUTput file>");
            return;
        }
        StdIn.setFile(args[0]);
        int a = Integer.parseInt(StdIn.readLine());
        ArrayList<ArrayList<String>> list = new ArrayList<ArrayList<String>>();
        for(int i = 0; i < a ; i++){
            ArrayList<String> temp = new ArrayList<String>();
            temp.add(StdIn.readLine());
            list.add(temp);
        }
        a = Integer.parseInt(StdIn.readLine());
        for(int i = 0; i < a; i++){
            list.get(find(StdIn.readString(),list)).add(StdIn.readString());
        }

        StdIn.setFile(args[1]);
        String course1 =  StdIn.readLine();
        String course2 =  StdIn.readLine();
        list.get(find(course1,list)).add(course2);


        StdOut.setFile(args[2]);
        if(isValid(list) == true){
            StdOut.print("YES");
        }
        else{
            StdOut.print("NO");
        }
    }
    
    private static boolean isValid(ArrayList<ArrayList<String>> list){
        Set<String> set = new HashSet<>();
        boolean valid = true;
        for(int i = 0; i < list.size(); i++){
            set.add(list.get(i).get(0));
            int f = 1;
            while(f < list.get(i).size()){
                valid = helper(list, set, list.get(i).get(f));
                if(valid == false){
                    return false;
                }
                f++; 
            }
            set.remove(list.get(i).get(0));
        }
        return true;
    }

    private static int find(String course, ArrayList<ArrayList<String>> list){
        for(int i = 0; i < list.size(); i++){
            if(list.get(i).get(0).equals(course)){
                return i;
            }
        }
        return -1;
    }
    private static boolean helper(ArrayList<ArrayList<String>> list, Set<String> set, String current){
       if(set.contains(current)){
           return false;
       }
       set.add(current);
       boolean valid = true;
       int i = 1;
       while(i < list.get(find(current, list)).size()){
         valid = helper(list,set,list.get(find(current, list)).get(i));
         if(valid == false){
            return false;
         }
         i++;
       }
       set.remove(current);
       return true;
    }
    
}
