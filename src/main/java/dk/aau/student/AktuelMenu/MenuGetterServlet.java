package dk.aau.student.AktuelMenu;
//TODO: Figure out a better name for this.

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import java.util.Optional;

@WebServlet(name = "MenuGetterServlet", value="/menu.json")
public class MenuGetterServlet extends HttpServlet {

    @Override
    public void init() throws ServletException {
        //Assume that our restaurant is Budolfi. It is a problem that we do this
        Restaurant restaurant = Restaurant.allRestaurants.stream().filter(r -> r.getName().equals("Budolfi")).findAny().orElse(null);

        if(restaurant == null){
            restaurant = new Restaurant("Budolfi");
            Restaurant.allRestaurants.add(restaurant);
        }
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("request received");
        resp.setContentType("application/json");
        JSONArray jsonResponse = new JSONArray();

        String restaurantId = req.getParameter("restaurant");

        Optional<Restaurant> optionalRestaurant = Restaurant.allRestaurants.stream()
                .filter(r -> r.getName().equals(restaurantId))
                .findFirst();

        if (optionalRestaurant.isEmpty()) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Restaurant " + restaurantId + " does not exist");
            return;
        }

        Restaurant restaurant = optionalRestaurant.get();
        for (Menu m : restaurant.availableMenus()) {
            JSONObject menuJson = new JSONObject(m.toJSONString());
            jsonResponse.put(menuJson);
        }

        PrintWriter writer = resp.getWriter();
        jsonResponse.write(writer);
        writer.close();
    }
}

