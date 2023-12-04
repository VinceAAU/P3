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

@WebServlet(name = "DeleteOrderServlet", urlPatterns = "/delete")
public class DeleteOrderServlet extends HttpServlet {

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

        ServletContext context = getServletContext();
        synchronized (context) { // Synchronize on the ServletContext object
            ArrayList<Order> orders = (ArrayList<Order>) context.getAttribute("orderArray");

            if (orders != null) {
                boolean orderRemoved = false;
                for (int i = 0; i < orders.size(); i++) {
                    if (orders.get(i).getOrderId() == orderId) {
                        orders.remove(i);
                        System.out.println("order deleted? i hope... orderID " + orderId);
                        orderRemoved = true;
                        break;
                    }
                }

                if (orderRemoved) {
                    response.getWriter().write("{\"message\": \"Order deleted.\"}");
                } else {
                    response.getWriter().write("{\"message\": \"Order not found.\"}");
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                }
            }
        }
    }
}