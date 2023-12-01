package dk.aau.student.AktuelMenu;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.ServletException;
import java.io.IOException;
import jakarta.servlet.ServletContext;
import jakarta.servlet.annotation.WebServlet;

import java.util.ArrayList;

@WebServlet(name = "Kitchen", value="/kitchen")
public class KitchenServlet extends HttpServlet {

    @Override
    protected  void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        ServletContext orders = getServletContext();
        ArrayList<Order> currentOrders;

        synchronized (orders) {
            currentOrders = (ArrayList<Order>) orders.getAttribute("globalOrderArray");

            if (currentOrders == null || currentOrders.isEmpty()){
                String message = "{\"message\": \"No orders yet\"}";
                response.getWriter().write(message);
            } else {


            }
        }







    }






}
