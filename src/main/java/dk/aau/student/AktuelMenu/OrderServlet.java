package dk.aau.student.AktuelMenu;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;

@WebServlet(name = "orderinput", value = "/OrderSent")
public class OrderServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        BufferedReader reader = request.getReader();
        StringBuilder requestBody = new StringBuilder();
        String line;

        while ((line = reader.readLine())!=null){
            requestBody.append(line);
        }
        String Order = requestBody.toString();

        MenuItem orderMenuItem = menu.getMenuItemByDisplayName(itemName);
    }

}
