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

        for(File jsonFile : new File((String) getServletContext().getAttribute("menuSaveLocation")).listFiles((file, s) -> s.endsWith(".json"))) {

            try (BufferedReader br = new BufferedReader(new FileReader(jsonFile))) {
                StringBuilder content = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    content.append(line).append("\n");
                }
                Menu menu = Menu.fromJSONObject(new JSONObject(content.toString()));
                restaurant.addMenu(menu);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("request recivede");
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

        PrintWriter writer = resp.getWriter();
        jsonResponse.write(writer);
        writer.close();
    }
}

