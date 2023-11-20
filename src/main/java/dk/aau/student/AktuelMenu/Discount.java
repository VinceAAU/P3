package dk.aau.student.AktuelMenu;

import org.json.JSONObject;
import org.json.JSONString;

public class Discount implements JSONString {
    private int price;
    //amount is the amount of option you need to choose for this
    private int amount;

    private DaySelector days;


    @Override
    public String toJSONString() {
        JSONObject json = new JSONObject();
        json.put("price", price);
        json.put("amount", amount);
        json.put("days", days.toArray());

        return json.toString();
    }
}