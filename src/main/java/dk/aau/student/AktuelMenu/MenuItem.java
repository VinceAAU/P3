package dk.aau.student.AktuelMenu;


import org.json.JSONObject;
import org.json.JSONString;

import java.util.ArrayList;
import java.util.List;

public class MenuItem implements JSONString {
    private int price;
    private String internalName;
    private String displayName;
    private Discount discount = null;

    public static MenuItem fromJSONObject(JSONObject itemJSON) {
        MenuItem item = new MenuItem(
                itemJSON.getString("name"),
                itemJSON.getString("displayName"),
                itemJSON.getInt("basePrice")
        );
        //TODO: Figure out if description exists or not

        item.setMaximumOptions(itemJSON.getInt("maxOptions"));
        item.setMinimumOptions(itemJSON.getInt("minOptions"));

        for (Object optionJSON : (itemJSON.getJSONArray("options").toList())){
            item.addOption(Option.fromJSONObject((JSONObject) optionJSON));
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