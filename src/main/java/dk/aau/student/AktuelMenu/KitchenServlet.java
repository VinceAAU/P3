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
            currentOrders = (ArrayList<Order>) context.getAttribute("globalOrderArray");

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            if (currentOrders == null || currentOrders.isEmpty()) {
                String message = "{\"message\": \"No orders yet\"}";
                response.getWriter().write(message);
            } else {
                JSONArray ordersJsonArray = new JSONArray();
                for (Order order : currentOrders) {
                    JSONObject orderJSON = new JSONObject();
                    orderJSON.put("tableId", order.getTableId());
                    orderJSON.put("orderId", order.getOrderId());

                    JSONArray orderItemJSONArray = new JSONArray();
                    for (OrderItem item : order.getItems()) {
                        JSONObject itemJson = new JSONObject();
                        itemJson.put("internalName", item.getMenuItem().getInternalName());
                        itemJson.put("comment",item.getComment());
                        orderItemJSONArray.put(itemJson);

                        JSONArray orderOption = new JSONArray();
                        for (Option option : item.getOptions()) {
                            JSONObject optionJSON = new JSONObject();
                            optionJSON.put("internalName", option.getInternalName());
                            orderOption.put(optionJSON);
                        }
                        orderJSON.put("options", orderOption);

                        JSONArray orderAddition = new JSONArray();
                        for (Option addition : item.getAdditions()){
                            JSONObject additionJSON= new JSONObject();
                            additionJSON.put("internalName", addition.getInternalName());
                            orderAddition.put(additionJSON);
                        }
                        orderJSON.put("additions",orderAddition);

                        orderItemJSONArray.put(itemJson);
                    }

                    orderJSON.put("orders", orderItemJSONArray);
                    ordersJsonArray.put(orderJSON);
                }

                response.getWriter().write(ordersJsonArray.toString());
            }
        }
    }
}