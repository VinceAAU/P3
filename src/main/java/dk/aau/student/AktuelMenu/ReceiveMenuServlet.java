package dk.aau.student.AktuelMenu;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.Optional;

@WebServlet(name="MenuSent", value = "/AktuelMenu/MenuSent")
public class ReceiveMenuServlet extends HttpServlet {


    @Override
    public void init() {
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        JSONObject uploadedMenu = new JSONObject(req.getReader().lines().collect(Collectors.joining(System.lineSeparator())));

        Menu menu = Menu.fromJSONObject(uploadedMenu.getJSONObject("menu"));

        String restaurantName = uploadedMenu.getJSONObject("menu").getString("restaurant");
        Optional<Restaurant> maybeRestaurant = Restaurant.allRestaurants.stream().filter(r -> r.getName().equals(restaurantName)).findAny();

        if(maybeRestaurant.isEmpty()) {
            resp.setStatus(404);
            resp.getWriter().println("Restaurant '" + restaurantName + "' not found");
            return;
        }
        Restaurant restaurant = maybeRestaurant.get();


        if(restaurant.getMenu(menu.getName())==null) {
            restaurant.addMenu(menu);
        } else {
            restaurant.updateMenu(menu);
        }

        //Rather than building our paths as a String, we use Paths.get(), because it's safer (handles things like "..", hopefully)
        Path path = Paths.get((String) getServletContext().getAttribute("menuSaveLocation"), restaurant.getName() +".json");

        try {
            path.toFile().createNewFile();
            FileWriter fileWriter = new FileWriter(path.toFile(), false);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(restaurant.toJSONString());
            bufferedWriter.close();
            fileWriter.close();
        }catch (IOException i) {
            System.out.println("IO ERROR: " + i.getMessage());
        }

        System.out.println(menu.toJSONString());
        resp.setStatus(200);

    }
}
