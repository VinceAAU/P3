package dk.aau.student.AktuelMenu;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.IntFunction;

public class Restaurant{
    //I THINK THIS IS A HORRIBLE WAY OF DOING THINGS
    //PLEASE TELL ME IF THIS IS AS HORRIBLE AS I THINK IT IS
    //I FEEL SHAME FOR EVEN HAVING CONSIDERED WRITING THIS
    //Love, Vincent xoxo
    public final static ArrayList<Restaurant> allRestaurants = new ArrayList<>();

    private String name;
    private ArrayList<Menu> menus;
    private ArrayList<Order> orders;

    public ArrayList<Menu> getMenus() {
        return menus;
    }

    public String getName() {
        return name;
    }

    public Menu[] availableMenus(){
        return menus.stream().filter(Menu::isAvailable).toArray(Menu[]::new); //I DON'T KNOW WHAT THIS DOES OR WHY THIS WORKS AND WHYU IT WASN'T WORKING BEFORE. PLEASE DO NOT ASK ABOUT THIS FOR THE EXAM BECAUSE IT IS A PIECE OF DEVILRY AND I WILL NEVER KNOW THE TRUTH ABOUT IT. MUST TEST MUST TEST MUST TEST. TESTING IS NOT OPTIONAL HERE, BECAUSE I DON'T KNOW HOW THIS WORKS SO WE MUST MAKE SURE THAT I'M NOT COMPLETELY OFF
    }

    public Restaurant(String name){
        this.name = name;
        menus = new ArrayList<>();
        orders = new ArrayList<>();
    }

    public void addMenu(Menu menu){
        menus.add(menu);
    }

    public Menu getMenu(String id) {
        for (Menu menu : menus) {

            if (menu.getName().equals(id)) {
                return menu;
            }

        }
        return null;
    }
}