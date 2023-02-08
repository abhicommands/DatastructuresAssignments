package warehouse;

public class PurchaseProduct {
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
            else {
                int day = StdIn.readInt();
                int id = StdIn.readInt();
                int amount = StdIn.readInt();
                box.purchaseProduct(id, day, amount);

            }
                
            i++;
        }
        StdOut.println(box.toString());
    }
}
