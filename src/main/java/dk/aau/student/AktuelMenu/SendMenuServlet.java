package dk.aau.student.AktuelMenu;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONObject;
import org.json.JSONString;

import java.io.IOException;
import java.util.stream.Collectors;

@WebServlet(name="MenuGet", value ="/AktuelMenu/MenuGet")
public class SendMenuServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String menuId = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        String menuJSONstring = "";
        System.out.println(menuId);
        for (Restaurant restaurant:Restaurant.allRestaurants) {
            Menu searchingMenu = restaurant.getMenu(menuId);
            if (searchingMenu != null)
            {
                menuJSONstring = searchingMenu.toJSONString();
            }
        }

        resp.getWriter().println(menuJSONstring);
        System.out.println(menuJSONstring);


    }
}
