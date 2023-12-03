package dk.aau.student.AktuelMenu;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import java.util.ArrayList;
import java.util.Arrays;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;

@WebServlet(name = "TempMockupServlet", urlPatterns = {"/TempMockupServlet"}, loadOnStartup = 1)
public class TempMockupServlet extends HttpServlet {
    public void init() throws ServletException {
        super.init();

        // Create a list to hold all the mock Orders
        ArrayList<Order> mockOrders = new ArrayList<>();

        for (int i = 1; i <= 7; i++) {
            // Mock MenuItem
            MenuItem burger = new MenuItem("burger", "Classic Burger", 18000);

            // Mock Options
            Option cheeseOption = new Option("cheese", "Extra Cheese", 0);
            Option baconOption = new Option("bacon", "Bacon", 1000);
            ArrayList<Option> burgerOptions = new ArrayList<>(Arrays.asList(cheeseOption, baconOption));

            // Mock Additions
            Option friesAddition = new Option("fries", "Fries", 3500);
            ArrayList<Option> burgerAdditions = new ArrayList<>(Arrays.asList(friesAddition));

            // Create OrderItem with the burger, options, and additions
            OrderItem burgerItem = new OrderItem(burger, burgerOptions, burgerAdditions, "Extra ketchup, please.");

            ArrayList<OrderItem> orderItems = new ArrayList<>();
            orderItems.add(burgerItem);

            // Create an Order with a unique tableId and orderId
            Order mockOrder = new Order(i, 100 + i, orderItems);

            // Add the Order to the list
            mockOrders.add(mockOrder);
        }

        // Get the ServletContext
        ServletContext context = getServletContext();

        // Insert mockOrders into the ServletContext
        context.setAttribute("globalOrderArray", mockOrders);
        System.out.println("mockOrders made and sent");
    }
}