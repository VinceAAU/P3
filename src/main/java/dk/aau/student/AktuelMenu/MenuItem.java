package dk.aau.student.AktuelMenu;


import org.json.JSONObject;
import org.json.JSONString;

import java.util.ArrayList;

public class MenuItem implements JSONString {
    private int price;
    private String internalName;
    private String displayName;
    private Discount discount = null;

    public String getInternalName() {
        return internalName;
    }

    public String getDisplayName() {return displayName;}
    public MenuItem (String internalName, String displayName, int price){
        this.internalName = internalName;
        this.displayName = displayName;
        this.price = price;
        options = new ArrayList<>();
        additions = new ArrayList<>();
    }

    public Option[] getOptions(){
        return options.toArray(Option[]::new);
    }

    private ArrayList<Option> additions;
    private ArrayList<Option> options;

    private int minimumOptions;
    private int maximumOptions;

    public void addOption(Option option){
        options.add(option);
    }

    public Option[] getAdditions(){
        return additions.toArray(new Option[0]);
    }

    @Override
    public String toJSONString() {
        JSONObject json = new JSONObject();
        json.put("name", displayName);
        json.put("basePrice", price);
        json.put("minOptions", minimumOptions);
        json.put("maxOptions", maximumOptions);
        json.put("options", getOptions());
        json.put("additions", getAdditions());
        json.put("discount", discount);
        return json.toString();
    }

}