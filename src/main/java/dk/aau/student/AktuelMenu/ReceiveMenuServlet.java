package dk.aau.student.AktuelMenu;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;


@WebServlet(name="MenuSent", value = "/AktuelMenu/MenuSent")
public class ReceiveMenuServlet extends HttpServlet {

    private String menuSaveLocation;

    @Override
    public void init() {
        //Figure out if you're using Windows or UNIX, and set menuSaveLocation based on that
        String osName = System.getProperty("os.name");
        if(osName.startsWith("Windows")){
            String appData = System.getenv("APPDATA");
            menuSaveLocation = appData + "\\aktuel-menus\\";
        } else if(osName.startsWith("Mac")) {
            throw new UnsupportedOperationException("MacOS is not supported at the moment, because we don't currently have any of those computers to test on:)");
        } else /* Assume it's a UNIX system */ {
            String dataDir = System.getenv("XDG_DATA_HOME");
            if(dataDir==null) { //If $XDG_DATA_HOME is not set, which it often is not
                dataDir = System.getenv("HOME") + "/.local/share"; //The $XDG_DATA_HOME default, according to the specs
            }
            menuSaveLocation = dataDir + "/aktuel-menus/";
        }

        //Create menuSaveLocation if not existent
        File saveDir = new File(menuSaveLocation);
        if(!saveDir.exists() || !saveDir.isDirectory()) {//Make the directory (and parent directories) if necessary
            boolean success = saveDir.mkdirs();
            if(!success) {
                System.err.println("ERROR: Could not create directory '" + menuSaveLocation + "'. Using current working directory instead");
                menuSaveLocation = System.getProperty("user.dir");
            }
        }
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        JSONObject uploadedMenu = new JSONObject(req.getReader().lines().collect(Collectors.joining(System.lineSeparator())));

        Menu menu = Menu.fromJSONObject(uploadedMenu.getJSONObject("menu"));

        Restaurant restaurant = Restaurant.allRestaurants.get(0); //TODO: Actually get restaurant input
        if(restaurant.getMenu(menu.getName())==null) {
            restaurant.addMenu(menu);
        } else {
            restaurant.updateMenu(menu);
        }

        //Rather than building our paths as a String, we use Paths.get(), because it's safer (handles things like "..", hopefully)
        Path path = Paths.get(menuSaveLocation, menu.getName()+".json");

        try {
            path.toFile().createNewFile();
            FileWriter fileWriter = new FileWriter(path.toFile(), false);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(menu.toJSONString());
            bufferedWriter.close();
            fileWriter.close();
        }catch (IOException i) {
            System.out.println("IO ERROR: " + i.getMessage());
        }

        System.out.println(menu.toJSONString());
        resp.setStatus(200);

    }
}
