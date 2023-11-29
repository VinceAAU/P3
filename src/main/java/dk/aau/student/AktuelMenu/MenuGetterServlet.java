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
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.NoSuchElementException;

@WebServlet(name = "Menu getter servlet", value="/menu.json")
public class MenuGetterServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("request recivede");
        resp.setContentType("application/json");
        JSONArray jsonResponse = new JSONArray();

        String restaurantId = req.getParameter("restaurant");

        Restaurant restaurant;
        try {
            //noinspection OptionalGetWithoutIsPresent
            restaurant = Restaurant.allRestaurants.stream().filter(r -> r.getName().equals(restaurantId)).findFirst().get();
        } catch (NoSuchElementException e) {
            resp.sendError(404, "Restaurant " + restaurantId + " does not exist");
            return;
        }

        for (Menu m : restaurant.availableMenus()){
            jsonResponse.put(m);
            System.out.println("menu contains" + m);
        }System.out.println("menu sent");

        jsonResponse.write(resp.getWriter());
    }
}

