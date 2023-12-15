package dk.aau.student.AktuelMenu;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.json.JSONObject;

import java.io.*;
import java.nio.file.Files;
import java.security.SecureRandom;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "mainServlet", value="/index.html", loadOnStartup = 0)
public class ServletMain extends HttpServlet {
    @Override
    public void init() throws ServletException {

        ServletContext context = getServletContext();

        synchronized (context) {
            //Figure out if you're using Windows or UNIX, and set menuSaveLocation based on that
            String osName = System.getProperty("os.name");
            if (osName.startsWith("Windows")) {
                String appData = System.getenv("APPDATA");
                context.setAttribute("menuSaveLocation", appData + "\\aktuel-menus\\");
            } else if (osName.startsWith("Mac")) {
                throw new UnsupportedOperationException("MacOS is not supported at the moment, because we don't currently have any of those computers to test on:)");
            } else /* Assume it's a UNIX system */ {
                String dataDir = System.getenv("XDG_DATA_HOME");
                if (dataDir == null) { //If $XDG_DATA_HOME is not set, which it often is not
                    dataDir = System.getenv("HOME") + "/.local/share"; //The $XDG_DATA_HOME default, according to the specs
                }
                context.setAttribute("menuSaveLocation", dataDir + "/aktuel-menus/");
            }

            //Create menuSaveLocation if not existent
            File saveDir = new File(String.valueOf(context.getAttribute("menuSaveLocation")));
            if (!saveDir.exists() || !saveDir.isDirectory()) {//Make the directory (and parent directories) if necessary
                boolean success = saveDir.mkdirs();
                if (!success) {
                    System.err.println("ERROR: Could not create directory '" + context.getAttribute("menuSaveLocation") + "'. Using current working directory instead");
                    context.setAttribute("menuSaveLocation", System.getProperty("user.dir"));
                }
            }

            //Load all menus in
            for(File restaurantFile : saveDir.listFiles((file, s) -> s.endsWith(".json"))){
                try {
                    JSONObject restaurant = new JSONObject(Files.readString(restaurantFile.toPath()));

                    Restaurant.allRestaurants.add(Restaurant.fromJSONObject(restaurant));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            //Do the auth stuff
            File tokenFile = new File(saveDir, "token");
            String token;
            try {
                if (tokenFile.exists() && tokenFile.isFile()) {
                    token = Files.readString(tokenFile.toPath()).strip();
                } else {
                    token = Long.toString(new SecureRandom().nextLong(), 36);
                    BufferedWriter writer = new BufferedWriter(new FileWriter(tokenFile));
                    writer.write(token);
                    writer.close();
                }
            } catch (IOException e){
                throw new RuntimeException(e);
            }

            context.setAttribute("adminToken", token);

            System.out.println("Your token is '" + token + "'");
        }
    }

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        res.getWriter().println("Tak for at vaere med i testen!");
    }
}