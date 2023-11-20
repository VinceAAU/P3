package dk.aau.student.AktuelMenu;

import java.util.ArrayList;

public class Menu{
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
}