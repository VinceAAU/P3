package dk.aau.student.AktuelMenu;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "ordersent", value = {"/OrderSent"})
public class OrderServlet extends HttpServlet {

    public void init() throws ServletException {
        super.init();
        System.out.println("OrderServlet initialized");
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
            System.out.println("first loop started");
            if (menuItem == null) {
                System.out.println("menuitem not found");
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Menu item not found: " + orderItemName);
                return;
            }

            JSONArray selectedOptionsArray = orderObject.getJSONArray("selectedOptions");
            ArrayList<Option> selectedOptions = new ArrayList<>();
            for (int j = 0; j < selectedOptionsArray.length(); j++) {
                System.out.println("made it into option loop");
                String orderOptionName = selectedOptionsArray.getString(j);
                System.out.println("menuItem " + menuItem);
                System.out.println("orderOptionName " + orderOptionName);
                Option selectedOption = getOptionByDisplayName(menuItem, orderOptionName);
                if (selectedOption != null) {
                    selectedOptions.add(selectedOption);
                }
            }

            JSONArray selectedAdditionsArray = orderObject.getJSONArray("selectedAdditions");
            ArrayList<Option> selectedAdditions = new ArrayList<>();
            for (int j = 0; j < selectedAdditionsArray.length(); j++) {
                System.out.println("made it inside addition loop");
                String orderAdditionName = selectedAdditionsArray.getString(j);
                Option selectedAddition = getAdditionByDisplayName(menuItem, orderAdditionName);
                if (selectedAddition != null) {
                    selectedAdditions.add(selectedAddition);
                }
            }

            String comment = orderObject.optString("comment", ""); // optString will handle null or missing 'comment'

            OrderItem orderItem = new OrderItem(menuItem, selectedOptions, selectedAdditions, comment);
            orderItems.add(orderItem);
            System.out.println("orderItem created");
        }

        int tableId = 1; // Extract from JSON if needed
        int orderId = 1; // Extract from JSON if needed

        Order order = new Order(tableId, orderId, orderItems);

        ServletContext context = getServletContext();
        synchronized (context) {
            context.setAttribute("orderidplaceholder",order);
        }

        System.out.println("order created");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write("{\"message\": \"Order received and processed\"}");
        response.setStatus(HttpServletResponse.SC_OK);
    }

    //helper method to get MenuItem by display name
    private MenuItem getMenuItemByDisplayName(String orderItemName) {
        for (Restaurant restaurant : Restaurant.allRestaurants) {
            for (Menu menu : restaurant.getMenus()) {
                MenuItem menuItem = menu.getMenuItemByDisplayName(orderItemName);
                if (menuItem != null) {
                    return menuItem;
                }
            }
        }
        return null;
    }

    //helper method to get option by display name within Menuitem
    private Option getOptionByDisplayName(MenuItem menuItem, String displayName) {
        for (Option option : menuItem.getOptions()) {
            if (option.getDisplayName().equals(displayName)) {
                return option;
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