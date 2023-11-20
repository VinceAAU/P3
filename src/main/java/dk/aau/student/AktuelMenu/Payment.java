package dk.aau.student.AktuelMenu;

import java.util.ArrayList;

public class Payment {
    ArrayList<OrderItem> list = new ArrayList<>();
    private int price;


    public void payment(ArrayList<OrderItem> orderItems) {

        this.list = orderItems; // Constructs List
        this.price = 0; // Initializes price
        for (int i = 0; i < list.size(); i++) { // for every i in the list the code does the following:
            this.price = this.price + orderItems.get(i).getPrice(); //gets the price and combines the price
        }

    }

    public int getPrice() {
        return price;
    }


}




