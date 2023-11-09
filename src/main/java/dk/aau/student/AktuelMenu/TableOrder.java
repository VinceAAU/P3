package dk.aau.student.AktuelMenu;

import java.util.ArrayList;

public class TableOrder{
    private int tableId;
    private int orderId;
    private boolean delivered = false;

    private ArrayList<OrderItem> items;

    public TableOrder(int tableId, int orderId, ArrayList<OrderItem> listOfOrder){
        this.tableId = tableId;
        this.orderId = orderId;
        this.items = listOfOrder;
    }
    public String printForKitchen(){
        StringBuilder order = new StringBuilder("Order " + orderId + " for table " + tableId + "\n");

        for (OrderItem item : items){
            order.append(item.printForKitchen());
        }

        return order.toString();
    }
}