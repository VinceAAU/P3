package dk.aau.student.AktuelMenu;

import java.util.ArrayList;

public class OrderItem {
    private MenuItem menuItem;

    private ArrayList<Option> options;
    private ArrayList<Option> additions;

    private int price;

    private String comment;
    public OrderItem (MenuItem menuItem, ArrayList<Option> options, ArrayList<Option> additions,String comment){
        this.menuItem = menuItem;
        this.options = options;
        this.additions = additions;
        this.price = menuItem.getPrice();
        this.comment = comment;
    }
    String printForKitchen(){
        StringBuilder output = new StringBuilder();

        output.append(menuItem.getInternalName()).append("\n");

        for (Option o : options){
            output.append("\t").append(o.getInternalName()).append("\n");
        }

        for (Option a : additions){
            output.append("\t+").append(a.getInternalName()).append("\n");
        }

        output.append("Comments:\n");

        output.append(comment);

        return output.toString();
    }
    public int calcPrice(){
        if (menuItem.getDiscount() != null){
            int calcedPrice = menuItem.getPrice();
            int calcDiscount = menuItem.getDiscountInt();
            calcedPrice = Math.addExact(calcedPrice,calcDiscount);
            this.price = calcedPrice;
            return calcedPrice;
        }
        return menuItem.getPrice();

    }
}