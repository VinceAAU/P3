package dk.aau.student.AktuelMenu;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.stream.Collectors;


@WebServlet(name="MenuSent", value = "/AktuelMenu/MenuSent")
public class ReceiveMenuServlet extends HttpServlet {

    @Override
    public void init() throws ServletException {


        super.init();
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject uploadedMenu = new JSONObject(req.getReader().lines().collect(Collectors.joining(System.lineSeparator())));

        Menu menu = Menu.fromJSONObject(uploadedMenu.getJSONObject("menu"));

        Restaurant.allRestaurants.get(0).addMenu(menu);
//todo:make the path proper
        String path = "C:/Users/tobia/Desktop/TestFolderForMenus" +menu.getName() +".txt";




        try {
            FileWriter fileWriter = new FileWriter(path);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(menu.toJSONString());
            bufferedWriter.close();
            fileWriter.close();
        }catch (IOException i)
        {
            System.out.println(i);
        }



        System.out.println(menu.toJSONString());
        resp.setStatus(200);

    }
}
