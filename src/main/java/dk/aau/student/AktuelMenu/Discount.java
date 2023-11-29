package dk.aau.student.AktuelMenu;

import org.json.JSONObject;
import org.json.JSONString;

public class Discount implements JSONString {
    private int price;
    //amount is the amount of option you need to choose for this
    //For example, "this discount only applies if you choose three options"
    private int amount;

    private DaySelector days;

    public static Discount fromJSONObject(JSONObject discountJSON) {
        return new Discount(
                discountJSON.getInt("price"),
                discountJSON.getInt("amount"),
                DaySelector.fromJSONArray(discountJSON.getJSONArray("days"))
        );
    }


    @Override
    public String toJSONString() {
        JSONObject json = new JSONObject();
        json.put("price", price);
        json.put("amount", amount);
        json.put("days", days.toArray());

        return json.toString();
    }

    public Discount(int price, int amount, DaySelector days) {
        this.price = price;
        this.amount = amount;
        this.days = days;
    }
    public boolean applies() {
        return days.isToday();
    }
}