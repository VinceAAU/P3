package dk.aau.student.AktuelMenu;

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

    public boolean isAvailable(){
        return availabilities.isAvailable();
    }

    public String getName(){
        return name;
    }

    //TODO: implement
    @Override
    public String toJSONString() {
        JSONObject menu = new JSONObject();
        menu.put("menuId", name); //TODO: Implement an actual ID system
        menu.put("availableTimes", availabilities);
        menu.put("items", menuItems);

        return menu.toString();
    }
}