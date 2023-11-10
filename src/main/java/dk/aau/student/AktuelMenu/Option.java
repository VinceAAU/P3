package dk.aau.student.AktuelMenu;

import java.util.ArrayList;

public class Option{
    private String internalName;
    private String displayName;

    private ArrayList<Allergen> allergens;
    private int price;

    String getInternalName(){
        return internalName;
    }

    public Option(String internalName, String displayName, int price){
        this.internalName = internalName;
        this.displayName = displayName;
        this.price = price;
        this.allergens = new ArrayList<>();
    }
    public void addAllergen(Allergen allergen){
        allergens.add(allergen);
    }
}