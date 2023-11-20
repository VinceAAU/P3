package dk.aau.student.AktuelMenu;

import org.json.JSONObject;
import org.json.JSONString;

import java.util.ArrayList;

public class Option implements JSONString {
    private String internalName;
    private String displayName;

    private ArrayList<Label> labels;
    private int price;

    String getInternalName(){
        return internalName;
    }

    public Option(String internalName, String displayName, int price){
        this.internalName = internalName;
        this.displayName = displayName;
        this.price = price;
        this.labels = new ArrayList<>();
    }
    public void addLabel(Label label){
        labels.add(label);
    }

    @Override
    public String toJSONString() {
        JSONObject json = new JSONObject();

        json.put("name", displayName);
        json.put("price", price);
        json.put("labels", labels.toArray());

        return json.toString();
    }
}