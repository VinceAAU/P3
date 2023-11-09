package dk.aau.student.AktuelMenu;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import javax.swing.plaf.metal.MetalBorders;
import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "mainServlet", value="/")

public class ServletMain extends HttpServlet {
    @Override
    public void init() throws ServletException {
        Menu menu = new Menu("budofol",new TimeAvailability(LocalTime.of(8,0,30),LocalTime.of(20,0,30),DaySelector.always()));
        // construct menu item then add to menu uwu
        MenuItem burger = new MenuItem("Burger", "Burger", 12);
        burger.addOption(new Option("kylling","kylling",0));
        burger.addOption(new Option("Bøf","Bøf",0));
        burger.addOption(new Option("Vegi","Vegi",0));
        menu.addItem(burger);

        ArrayList<Option> optionsInOrder = new ArrayList<>(List.of(new Option[]{burger.getOptions()[0]}));
        OrderItem order1 = new OrderItem(burger,optionsInOrder, new ArrayList<>());

        TableOrder tableOrderExample = new TableOrder(69,69,new ArrayList<>(List.of(order1)));

        System.out.println(tableOrderExample.printForKitchen());
    }

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        res.getWriter().println("Hello:)");
    }
}