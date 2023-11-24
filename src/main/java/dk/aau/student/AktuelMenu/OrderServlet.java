package dk.aau.student.AktuelMenu;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONObject;


import java.io.BufferedReader;
import java.io.IOException;

import java.util.ArrayList;

@WebServlet(name = "orderinput", value = "/OrderSent")
public class OrderServlet extends HttpServlet {


    private final Menu menu; //dependency injection
    private final Option option;

    public OrderServlet(Menu menu, Option option){
        this.menu = menu;
        this.option = option;
    }//constructer for injection

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        BufferedReader reader = request.getReader();
        StringBuilder requestBody = new StringBuilder();
        String line;

        while ((line = reader.readLine())!=null){
            requestBody.append(line);
        }
        JSONArray orderArray = new JSONArray(requestBody.toString());
        ArrayList<OrderItem> orderItems = new ArrayList<>();

            for (int i = 0; i < orderArray.length();i++) {
                JSONObject orderObject = orderArray.getJSONObject(i);

                String orderItemName = orderObject.getString("name");
                MenuItem menuItem = getMenuItemByDisplayName(orderItemName);

                JSONArray selectedOptionsArray = orderObject.getJSONArray("selectedOptions");
                ArrayList<Option> selectedOptions = new ArrayList<>();

                for (int j = 0; j < selectedOptionsArray.length(); j++) {
                    String orderOptionName = selectedOptionsArray.getString(j);
                    Option selectedOption = getOptionByDisplayName(menuItem, orderOptionName);
                    selectedOptions.add(selectedOption);
                }

                JSONArray selectedAdditionsArray = orderObject.getJSONArray("selectedAdditions");
                ArrayList<Option> selectedAdditions = new ArrayList<>();

                for (int j = 0; j < selectedAdditionsArray.length(); j++) {
                    String orderAdditionName = selectedAdditionsArray.getString(j);
                    Option selectedAddition = getAdditionByDisplayName(menuItem, orderAdditionName);
                    selectedAdditions.add(selectedAddition);
                }

                String comment = orderObject.getString("comment");

                OrderItem orderItem = new OrderItem(menuItem, selectedOptions, selectedAdditions,comment);
                orderItems.add(orderItem);
            }
        int tableId = 1; //need to change to get from json
        int orderId = 1; //don't remember what it represents so placeholder for now

        Order Order = new Order(tableId,orderId,orderItems);
    }

    private MenuItem getMenuItemByDisplayName(String orderItemName) {
        return menu.getMenuItemByDisplayName(orderItemName);
    }
    private Option getOptionByDisplayName(MenuItem menuItem, String displayName) {
        for (Option opt : menuItem.getOptions()) {
            if (opt.getDisplayName().equals(displayName)) {
                return opt;
            }
        }
        return null;
    }
    private Option getAdditionByDisplayName(MenuItem menuItem, String displayName) {
        for (Option add : menuItem.getAdditions()) {
            if (add.getDisplayName().equals(displayName)) {
                return add;
            }
        }
        return null;
    }

}