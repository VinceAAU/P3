package dk.aau.student.AktuelMenu;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.ServletException;
import java.io.IOException;
import jakarta.servlet.ServletContext;
import jakarta.servlet.annotation.WebServlet;

import java.util.ArrayList;

import org.json.JSONObject;
import org.json.JSONArray;

@WebServlet(name = "Kitchen", value="/kitchen")
public class KitchenServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletContext context = getServletContext();
        ArrayList<Order> currentOrders;

        synchronized (context) {
            currentOrders = (ArrayList<Order>) context.getAttribute("orderArray");


            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            if (currentOrders == null || currentOrders.isEmpty()) {
                String message = "{\"message\": \"Venter på ordre\"}";
                response.getWriter().write(message);
            } else {
                JSONArray ordersJsonArray = new JSONArray();
                for (Order order : currentOrders) {
                    // Check if the order is not delivered
                    if (!order.isDelivered()) {
                        JSONObject orderJSON = new JSONObject();
                        orderJSON.put("tableId", order.getTableId());
                        orderJSON.put("orderId", order.getOrderId());

                        JSONArray orderItemJSONArray = new JSONArray();


                        // Process each OrderItem as before
                        for (OrderItem item : order.getItems()) {
                            JSONObject itemJson = new JSONObject();
                            itemJson.put("internalName", item.getMenuItem().getInternalName());
                            itemJson.put("comment", item.getComment());
                            System.out.println("kommentar" + item.getComment());

                            JSONArray orderOption = new JSONArray();
                            for (Option option : item.getOptions()) {
                                JSONObject optionJSON = new JSONObject();
                                optionJSON.put("internalName", option.getInternalName());
                                orderOption.put(optionJSON);
                            }
                            itemJson.put("options", orderOption);

                            JSONArray orderAddition = new JSONArray();
                            for (Option addition : item.getAdditions()) {
                                JSONObject additionJSON = new JSONObject();
                                additionJSON.put("internalName", addition.getInternalName());
                                orderAddition.put(additionJSON);
                            }
                            itemJson.put("additions", orderAddition);

                            orderItemJSONArray.put(itemJson);
                        }

                        orderJSON.put("orders", orderItemJSONArray);
                        ordersJsonArray.put(orderJSON);
                    }
                }

                response.getWriter().write(ordersJsonArray.toString());
            }
        }
    }
}