package warehouse;

/*
 * Use this class to put it all together.
 */ 
public class Everything {
    public static void main(String[] args) {
        StdIn.setFile(args[0]);
        StdOut.setFile(args[1]);
        Warehouse box = new Warehouse();
        int i = 0;
        int x = StdIn.readInt();
        while (i < x) {
            String whatToDo = StdIn.readString();
            if (whatToDo.equals("add")) {
                int day = StdIn.readInt();
                int id = StdIn.readInt();
                String name = StdIn.readString();
                int stock = StdIn.readInt();
                int demand = StdIn.readInt();
                box.addProduct(id, name, stock, day, demand);
            }
            else if(whatToDo.equals("delete")){
                box.deleteProduct(StdIn.readInt());
            }
            else if(whatToDo.equals("purchase")) {
                int day = StdIn.readInt();
                int id = StdIn.readInt();
                int amount = StdIn.readInt();
                box.purchaseProduct(id, day, amount);
            }
            else {
                box.restockProduct(StdIn.readInt(), StdIn.readInt());
            }
                
            i++;
        }
        StdOut.println(box.toString());
    }
}
