package dk.aau.student.AktuelMenu;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONString;

import java.util.ArrayList;

public class Menu implements JSONString {
    private String name;
    private TimeAvailability availabilities;
    private ArrayList<MenuItem> menuItems;

    public Menu(String name, TimeAvailability availability){
        this.name = name;
        this.availabilities = availability;
        menuItems = new ArrayList<>();
    }

    public void addItem(MenuItem item){
        menuItems.add(item);
    }


    public MenuItem getMenuItemByDisplayName(String displayName){
        for (MenuItem item : menuItems) {
            if (item.getDisplayName().equals(displayName)){
                return item;
            }
        }
        return null;
    }

    public Option getOptionByDisplayName(MenuItem menuItem, String displayName) {
            for (Option option : menuItem.getOptions()) {
                if (option.getDisplayName().equals(displayName)) {
                    return option;
                }
            }
        return null;
    }

    public Option getAdditionByDisplayName(MenuItem menuItem, String displayName) {
            for (Option addition : menuItem.getAdditions()) {
                if (addition.getDisplayName().equals(displayName)) {
                    return addition;
                }
            }
        return null;
    }

    public boolean isAvailable(){
        return availabilities.isAvailable();
    }

    public String getName(){
        return name;
    }

    //TODO: implement
    @Override
    public String toJSONString() {
        JSONObject menuJson = new JSONObject();
        menuJson.put("menuId", name);

        // Convert TimeAvailability to JSONString
        menuJson.put("availableTimes", availabilities.toJSONString());

        // Convert MenuItems to JSONString
        JSONArray itemsJsonArray = new JSONArray();
        for (MenuItem item : menuItems) {
            JSONObject itemJson = new JSONObject(item.toJSONString()); // Convert string to JSONObject
            itemsJsonArray.put(itemJson);
        }
        menuJson.put("items", itemsJsonArray);
        System.out.println("JSON Representation of Menu: " + menuJson);

        return menuJson.toString();
    }

    public static Menu fromJSONObject(JSONObject menuJSON) {
        Menu menu = new Menu(
                menuJSON.getString("menuId"),
                TimeAvailability.fromJSONObject(
                        menuJSON.getJSONObject("availableTimes")
                )
        );

        //TODO: Move discount from menu to menuitem

        JSONArray itemsJSON = menuJSON.getJSONArray("items");

        for (int i = 0; i < itemsJSON.length(); i++) {
            menu.addItem(MenuItem.fromJSONObject(itemsJSON.getJSONObject(i)));
        }

        return menu;
    }
}