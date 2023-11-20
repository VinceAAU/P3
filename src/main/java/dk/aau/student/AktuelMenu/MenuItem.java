package dk.aau.student.AktuelMenu;

import javax.swing.*;

public class MenuItem{
    private int price;
    private String internalName;
    private String displayName;
    private Discount discount = null;

    private OptionList options;

    public String getInternalName() {
        return internalName;
    }
    public MenuItem (String internalName, String displayName, int price){
        this.internalName = internalName;
        this.displayName = displayName;
        this.price = price;
        options = new OptionList();
    }
    public void addOption(Option option){
        options.addOption(option);
    }

    public Option[] getOptions(){
        return options.getOptions();
    }

    public int getPrice(){
        return price;
    }

    public String getDisplayName() {
        return displayName;
    }
}