package dk.aau.student.AktuelMenu;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.json.JSONObject;

import java.io.FileReader;
import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;

@WebServlet(name = "mainServlet", value="/index.html")

public class ServletMain extends HttpServlet {
    @Override
    public void init() throws ServletException {

    }

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException,IOException {
        res.getWriter().println("Not fun project anymore please LET ME OUT OF THIS SYSTEM, I AM AN AI");
    }
}

/*        //TODO: Move all this stuff to test

        // construct menu item then add to menu uwu
        MenuItem burger = new MenuItem("Burger", "Burger", 12);
        burger.addOption(new Option("kylling","kylling",0));
        burger.addOption(new Option("Bøf","Bøf",0));
        Option vegiBurger = new Option("Vegi","Vegi",0);
        vegiBurger.addLabel(Label.VEGETARIAN);
        burger.addOption(vegiBurger);
        menu.addItem(burger);

        MenuItem variant = new MenuItem("xVarianter","Vælg 3 varianter eller flere",0);
        variant.addOption(new Option("Andelår","Confiteret andelår med skysauce",105));
        variant.addOption(new Option("Bao m grisebryst","Bao med mørt grisebryst marineret i Hoisin BBQ, spidskål, ristet sesam mayo og forårsløg",80));
        variant.addOption(new Option("Miso Rejer","Miso ramen med udon nudler, æg, forårsløg, chili og panko rejer",80));
        variant.addOption(new Option("Røget Laks","Skiver af kold røget laks med sesammayo, wasabi og ærteskud",80));
        variant.addOption(new Option("Tarteletter","Tarteletter med høns i asparges creme, tomat og persille",80));
        variant.addOption(new Option("Vinterslider m Ribbensteg","Vinterslider med ribbensteg, aioli, hjemmelavet rødkål og flæskesværs knas",80));
        variant.addOption(new Option("Svinekæber","Svinekæber ala creme med stegte svampe og svampesauce",80));
        variant.addOption(new Option("Pankorejer","Dybstegte pankorejer med chili mayo",80));
        variant.addOption(new Option("Fried Chicken","Fried chicken med chili mayo",80));
        variant.addOption(new Option("Tatar","Rørt tatar med marineret kål, grøn mayo, syltede rødløg, jordskokkechips og karse med rå æggeblomme",80));
        variant.addOption(new Option("lakse tatar","Rørt laksetatar med urter, avocadocreme og sprøde rugchips",80));
        variant.addOption(new Option("veggie Tatar","Veggie tatar på gulerod og sesam, med marineret kål, grøn mayo, edamamebønner, jordskokkechips og karse",80));

        MenuItem SliderFalafel = new MenuItem("SliderFafel","Slider med falafel, feldsalat og chimichurri af soltørret tomat og peberfrugt",80);


        ArrayList<Option> optionsInOrder = new ArrayList<>(List.of(new Option[]{burger.getOptions()[1]}));
        OrderItem order1 = new OrderItem(burger,optionsInOrder, new ArrayList<>(),"medium");

        Order orderExample = new Order(69,69,new ArrayList<>(List.of(order1)));

        System.out.println(orderExample.printForKitchen());

        System.out.println(new JSONObject(menu.toJSONString()).toString(4));
 */