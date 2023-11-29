package dk.aau.student.AktuelMenu;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONObject;


import java.io.BufferedReader;
import java.io.IOException;

import java.time.LocalTime;
import java.util.ArrayList;

@WebServlet(name = "ordersent", value = {"/OrderSent"})
public class OrderServlet extends HttpServlet {

    public void init() throws ServletException{
        super.init();
        System.out.println("OrderServlet initialized");
    }


    private final Menu menu; //dependency injection (for menu)
    private final Option option; //dependency injection (for option)

    //constructer for dependency injection
    public OrderServlet() {
        this.menu = new Menu("dependencyMenu",new TimeAvailability(LocalTime.MIDNIGHT,LocalTime.MIDNIGHT,DaySelector.never()));
        this.option = new Option("dependencyOption","dependencyOptions",0);
    }


    //dopost for handeling HTTP requests
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        //reads the request
        System.out.print("order request recivede.\n");
        BufferedReader reader = request.getReader();
        System.out.print("read into bufferedReader\n");
        StringBuilder requestBody = new StringBuilder();
        System.out.print("requestbody made\n");
        String line;

        while ((line = reader.readLine())!=null){
            requestBody.append(line);
        }System.out.print("lines placed in requestbody\n");

        //parses the json array from the request
        JSONArray orderArray = new JSONArray(requestBody.toString());
        System.out.print("orderarray created\n");
        ArrayList<OrderItem> orderItems = new ArrayList<>();
            //iterrate over each orderitem in jsonarray
            for (int i = 0; i < orderArray.length();i++) {
                JSONObject orderObject = orderArray.getJSONObject(i);

                //extracts orderitem name and finds matching menuitem
                String orderItemName = orderObject.getString("name");
                MenuItem menuItem = getMenuItemByDisplayName(orderItemName);

                //extracts selected options array
                JSONArray selectedOptionsArray = orderObject.getJSONArray("selectedOptions");
                ArrayList<Option> selectedOptions = new ArrayList<>();

                for (int j = 0; j < selectedOptionsArray.length(); j++) {
                    String orderOptionName = selectedOptionsArray.getString(j);
                    Option selectedOption = getOptionByDisplayName(menuItem, orderOptionName);
                    selectedOptions.add(selectedOption);
                }

                //extracts selected addition array
                JSONArray selectedAdditionsArray = orderObject.getJSONArray("selectedAdditions");
                ArrayList<Option> selectedAdditions = new ArrayList<>();

                for (int j = 0; j < selectedAdditionsArray.length(); j++) {
                    String orderAdditionName = selectedAdditionsArray.getString(j);
                    Option selectedAddition = getAdditionByDisplayName(menuItem, orderAdditionName);
                    selectedAdditions.add(selectedAddition);
                }

                //extracts comment
                String comment = orderObject.getString("comment");

                // creates an orderItem object using the found data
                OrderItem orderItem = new OrderItem(menuItem, selectedOptions, selectedAdditions,comment);
                orderItems.add(orderItem);
            }
        int tableId = 1; //need to change to get from json
        int orderId = 1; //don't remember what it represents so placeholder for now

        //creates order objercts to hold order items
        Order Order = new Order(tableId,orderId,orderItems);
    }

    //helper method to get MenuItem by display name
    private MenuItem getMenuItemByDisplayName(String orderItemName) {
        return menu.getMenuItemByDisplayName(orderItemName);
    }

    //helper method to get option by display name within Menuitem
    private Option getOptionByDisplayName(MenuItem menuItem, String displayName) {
        for (Option opt : menuItem.getOptions()) {
            if (opt.getDisplayName().equals(displayName)) {
                return opt;
            }
        }
        return null;
    }

    //helper method to get addition by display name within a MenuItem
    private Option getAdditionByDisplayName(MenuItem menuItem, String displayName) {
        for (Option add : menuItem.getAdditions()) {
            if (add.getDisplayName().equals(displayName)) {
                return add;
            }
        }
        return null;
    }

}