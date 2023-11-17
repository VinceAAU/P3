package dk.aau.student.AktuelMenu;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import netscape.javascript.JSException;
import netscape.javascript.JSObject;

import java.io.IOException;

@WebServlet(name="MenuSent", value = "/AktuelMenu/MenuSent")
public class AdminServerlet extends HttpServlet {


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);


        System.out.println("WE DID IT");
        //jObj.menu.items[0].name
    }
}
