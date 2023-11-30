package dk.aau.student.AktuelMenu;
//TODO: Figure out a better name for this.

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;

@WebServlet(name = "MenuGetterServlet", value="/menu.json")
public class MenuGetterServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("Request received");
        resp.setContentType("application/json");
        JSONArray jsonResponse = new JSONArray();

        String restaurantId = req.getParameter("restaurant");

        Optional<Restaurant> optionalRestaurant = Restaurant.allRestaurants.stream()
                .filter(r -> r.getName().equals(restaurantId))
                .findFirst();

        if (!optionalRestaurant.isPresent()) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Restaurant " + restaurantId + " does not exist");
            return;
        }

        Restaurant restaurant = optionalRestaurant.get();
        System.out.println(restaurant.availableMenus());
        for (Menu m : restaurant.availableMenus()) {
            System.out.println("used for loop");
            JSONObject menuJson = new JSONObject(m.toJSONString());
            jsonResponse.put(menuJson);
            System.out.println("Menu contains: " + m);
        }
        System.out.println("Menu sent");

        PrintWriter writer = resp.getWriter();
        jsonResponse.write(writer);
        writer.close();
    }
}

