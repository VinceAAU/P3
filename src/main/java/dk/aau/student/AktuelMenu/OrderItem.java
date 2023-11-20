package dk.aau.student.AktuelMenu;

import java.util.ArrayList;
import java.util.Optional;

public class OrderItem {
    private MenuItem menuItem;

    private ArrayList<Option> options;
    private ArrayList<Option> additions;

    private String comment;
    public OrderItem (MenuItem menuItem, ArrayList<Option> options, ArrayList<Option> additions,String comment){
        this.menuItem = menuItem;
        this.options = options;
        this.additions = additions;
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
    public int getPrice(){
        return menuItem.getPrice();
    }
}