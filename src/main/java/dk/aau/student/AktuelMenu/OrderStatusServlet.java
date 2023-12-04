package dk.aau.student.AktuelMenu;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.BufferedReader;
import java.util.ArrayList;

import org.json.JSONObject;

@WebServlet(name = "OrderStatusServlet", urlPatterns = "/updateOrderStatus")
public class OrderStatusServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        StringBuilder buffer = new StringBuilder();
        String line;
        BufferedReader reader = request.getReader();
        while ((line = reader.readLine()) != null) {
            buffer.append(line);
        }
        String data = buffer.toString();

        // Parse the request body to get order data
        JSONObject json = new JSONObject(data);
        int orderId = json.getInt("orderId");
        boolean delivered = json.getBoolean("delivered");

        ServletContext context = getServletContext();
        synchronized (context) { // Synchronize on the ServletContext object
            ArrayList<Order> orders = (ArrayList<Order>) context.getAttribute("globalOrderArray");

            if (orders != null) {
                for (Order order : orders) {
                    if (order.getOrderId() == orderId) {
                        order.setDelivered(delivered);
                        break;
                    }
                }

                response.getWriter().write("{\"message\": \"Order status updated.\"}");
            } else {

                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("{\"error\": \"Orders not found or not initialized.\"}");
            }
        }
    }
}