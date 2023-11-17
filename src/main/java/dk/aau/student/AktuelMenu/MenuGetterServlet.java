package dk.aau.student.AktuelMenu;
//TODO: Figure out a better name for this.

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONArray;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalTime;
import java.util.NoSuchElementException;

@WebServlet(name = "Menu getter servlet", value="/menu.json")
public class MenuGetterServlet extends HttpServlet {
    @Override
    public void init() throws ServletException {
        //TODO: REMOVE, BECAUSE THIS IS A TEST

        Restaurant budolfi = new Restaurant("budolfi");

        Menu testMenuOne = new Menu("First test menu", new TimeAvailability(LocalTime.MIN, LocalTime.MAX, DaySelector.always()));

        budolfi.addMenu(testMenuOne);

        Restaurant.allRestaurants.add(budolfi);
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        JSONArray jsonResponse = new JSONArray();

        String restaurantId = req.getParameter("restaurant");

        Restaurant restaurant;
        try {
            restaurant = Restaurant.allRestaurants.stream().filter(r -> r.getName().equals(restaurantId)).findFirst().get();
        } catch (NoSuchElementException e) {
            resp.sendError(404, "Restaurant " + restaurantId + " does not exist");
            return;
        }

        for (Menu m : restaurant.availableMenus()){
            jsonResponse.put(m);
        }

        jsonResponse.write(resp.getWriter());
    }
}

