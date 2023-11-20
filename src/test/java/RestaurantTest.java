import dk.aau.student.AktuelMenu.DaySelector;
import dk.aau.student.AktuelMenu.Menu;
import dk.aau.student.AktuelMenu.Restaurant;
import dk.aau.student.AktuelMenu.TimeAvailability;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.Arrays;

public class RestaurantTest {

    @Test
    //Note: This test will fail if it takes more than an hour to complete, or if your computer can travel backwards in time
    void doesTheAvailableMenusMethodActuallyWorkProperly(){
        Menu available = new Menu("Available menu",
                new TimeAvailability(
                        LocalTime.now(),
                        LocalTime.now().plusHours(1),
                        DaySelector.always()
                )
        );
        Menu notAvailable = new Menu("Not available menu",
                new TimeAvailability(
                        LocalTime.now().plusHours(1),
                        LocalTime.now().plusHours(2),
                        DaySelector.always()
                )
        );

        Restaurant budofol = new Restaurant("Budofol: The Test Restaurant");

        budofol.addMenu(available);
        budofol.addMenu(notAvailable);

        Menu[] menus = budofol.availableMenus();

        Assertions.assertSame(menus[0], available);
        Assertions.assertFalse(Arrays.asList(menus).contains(notAvailable));
    }

}
