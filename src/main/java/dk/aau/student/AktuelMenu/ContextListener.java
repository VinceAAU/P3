package dk.aau.student.AktuelMenu;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

import java.time.LocalTime;

@WebListener
public class ContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent start) {

        Menu menu = new Menu("dependencyMenu",new TimeAvailability(LocalTime.MIDNIGHT,LocalTime.MIDNIGHT,DaySelector.never()));
        Option option= new Option("dependencyOption","dependencyOptions",0);

        start.getServletContext().setAttribute("menu",menu);
        start.getServletContext().setAttribute("option",option);
    }
}
