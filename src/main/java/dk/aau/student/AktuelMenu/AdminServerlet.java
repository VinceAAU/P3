package dk.aau.student.AktuelMenu;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import netscape.javascript.JSException;
import netscape.javascript.JSObject;

import java.io.IOException;
import java.util.stream.Collectors;


@WebServlet(name="MenuSent", value = "/AktuelMenu/MenuSent")
public class AdminServerlet extends HttpServlet {

    @Override
    public void init() throws ServletException {

        System.out.println("WE DID IT2");
        super.init();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));







    }
}
