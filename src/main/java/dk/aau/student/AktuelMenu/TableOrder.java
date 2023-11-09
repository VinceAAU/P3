package dk.aau.student.AktuelMenu;

import java.util.ArrayList;

public class TableOrder{
    private int tableId;
    private int orderId;
    private boolean delivered = false;

    private ArrayList<OrderItem> items;

    public String printForKitchen(){
        StringBuilder order = new StringBuilder("Order " + orderId + " for table " + tableId + "\n");

        for (OrderItem item : items){
            order.append(item.printForKitchen());
        }

        return order.toString();
    }
}