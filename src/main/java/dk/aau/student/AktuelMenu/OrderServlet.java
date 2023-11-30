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
        BufferedReader reader = request.getReader();
        StringBuilder requestBody = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            requestBody.append(line);
        }

        JSONArray orderArray = new JSONArray(requestBody.toString());
        ArrayList<OrderItem> orderItems = new ArrayList<>();

        for (int i = 0; i < orderArray.length(); i++) {
            JSONObject orderObject = orderArray.getJSONObject(i);
            String orderItemName = orderObject.getString("name");
            MenuItem menuItem = getMenuItemByDisplayName(orderItemName);

            if (menuItem == null) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Menu item not found: " + orderItemName);
                return;
            }

            JSONArray selectedOptionsArray = orderObject.getJSONArray("selectedOptions");
            ArrayList<Option> selectedOptions = new ArrayList<>();
            for (int j = 0; j < selectedOptionsArray.length(); j++) {
                String orderOptionName = selectedOptionsArray.getString(j);
                Option selectedOption = getOptionByDisplayName(menuItem, orderOptionName);
                if (selectedOption != null) {
                    selectedOptions.add(selectedOption);
                }
            }

            JSONArray selectedAdditionsArray = orderObject.getJSONArray("selectedAdditions");
            ArrayList<Option> selectedAdditions = new ArrayList<>();
            for (int j = 0; j < selectedAdditionsArray.length(); j++) {
                String orderAdditionName = selectedAdditionsArray.getString(j);
                Option selectedAddition = getAdditionByDisplayName(menuItem, orderAdditionName);
                if (selectedAddition != null) {
                    selectedAdditions.add(selectedAddition);
                }
            }

            String comment = orderObject.optString("comment", ""); // optString will handle null or missing 'comment'

            OrderItem orderItem = new OrderItem(menuItem, selectedOptions, selectedAdditions, comment);
            orderItems.add(orderItem);
        }

        int tableId = 1; // Extract from JSON if needed
        int orderId = 1; // Extract from JSON if needed

        Order order = new Order(tableId, orderId, orderItems);
        // TODO: Process the order as needed (store in database, send to kitchen)

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write("{\"message\": \"Order received and processed\"}");
        response.setStatus(HttpServletResponse.SC_OK);
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