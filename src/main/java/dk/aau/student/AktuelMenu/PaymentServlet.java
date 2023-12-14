package dk.aau.student.AktuelMenu;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "payment", value="/payment")
public class PaymentServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletContext context = getServletContext();
        ArrayList<Order> currentOrders;

        synchronized (context) {
            currentOrders = (ArrayList<Order>) context.getAttribute("orderArray");

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            boolean noOrdersToShow = true;
            JSONArray ordersJsonArray = new JSONArray();
            if (currentOrders != null) {

                for (Order order : currentOrders) {
                    // Check if the order is not delivered
                    if (order.isDelivered()) {
                        JSONObject orderJSON = new JSONObject();
                        orderJSON.put("tableId", order.getTableId());
                        orderJSON.put("orderId", order.getOrderId());

                        JSONArray orderItemJSONArray = new JSONArray();


                        // Process each OrderItem as before
                        for (OrderItem item : order.getItems()) {
                            JSONObject itemJson = new JSONObject();
                            itemJson.put("internalName", item.getMenuItem().getInternalName());
                            itemJson.put("price", item.calcPrice());

                            JSONArray orderOption = new JSONArray();
                            for (Option option : item.getOptions()) {
                                JSONObject optionJSON = new JSONObject();
                                optionJSON.put("internalName", option.getInternalName());
                                optionJSON.put("price", option.getPrice());
                                orderOption.put(optionJSON);
                            }
                            itemJson.put("options", orderOption);

                            JSONArray orderAddition = new JSONArray();
                            for (Option addition : item.getAdditions()) {
                                JSONObject additionJSON = new JSONObject();
                                additionJSON.put("internalName", addition.getInternalName());
                                additionJSON.put("price", addition.getPrice());
                                orderAddition.put(additionJSON);
                            }
                            itemJson.put("additions", orderAddition);

                            orderItemJSONArray.put(itemJson);
                        }

                        orderJSON.put("orders", orderItemJSONArray);
                        ordersJsonArray.put(orderJSON);
                        noOrdersToShow = false;
                    }
                }
            }

            if (currentOrders == null || currentOrders.isEmpty() || noOrdersToShow) {
                String message = "{\"message\": \"Venter på ordre\"}";
                response.getWriter().write(message);
            } else {
                response.getWriter().write(ordersJsonArray.toString());
            }
        }
    }
}


/* legacy
    ArrayList<OrderItem> list = new ArrayList<>();
       public void payment(ArrayList<OrderItem> orderItems) {

        this.list = orderItems; // Constructs List
        this.price = 0; // Initializes price
        for (int i = 0; i < list.size(); i++) { // for every i in the list the code does the following:
            this.price = this.price + orderItems.get(i).calcPrice(); //gets the price and combines the price
        }

    }
    public int getPrice() {
        return price;
    }

 */
