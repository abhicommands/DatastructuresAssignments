package warehouse;

/*
 * Use this class to test to addProduct method.
 */
public class AddProduct {
    public static void main(String[] args) {
        StdIn.setFile(args[0]);
        StdOut.setFile(args[1]);
        Warehouse box= new Warehouse();
        int i = 0;
        int x = StdIn.readInt();
        while(i < x) {
            int day = StdIn.readInt();
            int id= StdIn.readInt();
            String name = StdIn.readString();
            int stock = StdIn.readInt();
            int demand = StdIn.readInt();
            box.addProduct(id, name, stock, day, demand);
            i++;
        }
        StdOut.println(box.toString());
    }
}
