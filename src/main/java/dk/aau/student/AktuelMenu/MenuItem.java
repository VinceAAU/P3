package dk.aau.student.AktuelMenu;


import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONString;

import java.util.ArrayList;

public class MenuItem implements JSONString {
    private int price;
    private String internalName;
    private String displayName;
    private Discount discount = null;

    public static MenuItem fromJSONObject(JSONObject itemJSON) {
        int basePrice = 0;
        try{
            basePrice = itemJSON.getInt("basePrice"); //We have to do it like this in case the basePrice is ""
        } catch(JSONException ignored){}

        MenuItem item = new MenuItem(
                itemJSON.getString("internalName"),
                itemJSON.getString("displayName"),
                basePrice
        );
        //TODO: Test for blank discount
        item.setDiscount(Discount.fromJSONObject(itemJSON.getJSONObject("discount")));

        item.setMaximumOptions(itemJSON.getInt("maxOptions"));
        item.setMinimumOptions(itemJSON.getInt("minOptions"));

        for (Object optionJSON : (itemJSON.getJSONArray("options"))){
            item.addOption(Option.fromJSONObject((JSONObject) optionJSON));
        }
        for (Object additionJSON : (itemJSON.getJSONArray("additions"))){
            item.addAddition(Option.fromJSONObject((JSONObject) additionJSON));
        }
        return item;
    }

    public String getInternalName() {
        return internalName;
    }

    public MenuItem (String internalName, String displayName, int price){
        this.internalName = internalName;
        this.displayName = displayName;
        this.price = price;
        options = new ArrayList<>();
        additions = new ArrayList<>();

        minimumOptions = -1;
        maximumOptions = -1;
    }

    public Option[] getOptions(){
        return options.toArray(Option[]::new);
    }

    private ArrayList<Option> additions;
    private ArrayList<Option> options;

    private int minimumOptions;
    private int maximumOptions;

    public void setMinimumOptions(int minimumOptions) {
        this.minimumOptions = minimumOptions;
    }

    public void setMaximumOptions(int maximumOptions) {
        this.maximumOptions = maximumOptions;
    }

    public void addOption(Option option){
        options.add(option);
    }

    public Option[] getAdditions(){
        return additions.toArray(new Option[0]);
    }

    public void addAddition(Option addition){
        additions.add(addition);
    }

    public void setDiscount(Discount discount){
        this.discount = discount;
    }
    public void removeDiscount(){
        discount = null;
    }

    @Override
    public String toJSONString() {
        JSONObject json = new JSONObject();
        json.put("displayName", displayName);
        json.put("internalName",internalName);
        json.put("basePrice", price);
        json.put("minOptions", minimumOptions);
        json.put("maxOptions", maximumOptions);
        json.put("options", getOptions());
        json.put("additions", getAdditions());
        json.put("discount", discount);
        return json.toString();
    }


    public int getPrice(){
        return price;
    }

    public String getDisplayName() {
        return displayName;
    }
}