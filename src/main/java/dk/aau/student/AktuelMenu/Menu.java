package dk.aau.student.AktuelMenu;

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

    public boolean isAvailable(){
        return availabilities.isAvailable();
    }

    public String getName(){
        return name;
    }

    //TODO: implement
    @Override
    public String toJSONString() {
        return "{\"hi\": \"hi\"}";
    }
}