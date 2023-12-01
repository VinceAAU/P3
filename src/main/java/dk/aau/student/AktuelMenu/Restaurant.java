package dk.aau.student.AktuelMenu;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONString;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.IntFunction;

public class Restaurant implements JSONString {
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

    //Returns null if menu is not found
    public Menu getMenu(String id) {
        for (Menu menu : menus) {

            if (menu.getName().equals(id)) {
                return menu;
            }

        }
        return null;
    }

    //If there is no menu with that id, it just won't update anything
    public void updateMenu(Menu menu){
        String id = menu.getName();

        for (int i = 0; i < menus.size(); i++) {
            if(menus.get(i).getName().equals(id)){
                menus.set(i, menu);
                break; //There should only be one menu with a specific ID
            }
        }
    }


    @Override
    public String toJSONString() {
        JSONObject res = new JSONObject();
        res.put("name", name);
        res.put("menus", menus);
        return res.toString();
    }

    public static Restaurant fromJSONObject(JSONObject jsonObject) {
        Restaurant res = new Restaurant(jsonObject.getString("name"));

        JSONArray menuArrayJSON = jsonObject.getJSONArray("menus");
        menuArrayJSON.forEach(mJSON -> res.addMenu(Menu.fromJSONObject((JSONObject) mJSON)));

        return res;
    }
}