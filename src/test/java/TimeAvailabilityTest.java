import dk.aau.student.AktuelMenu.TimeAvailability;
import dk.aau.student.AktuelMenu.DaySelector;
import org.junit.jupiter.api.Assertions;        //imports jupiter assertions
import org.junit.jupiter.api.Test;              //import jupiter tests
import java.time.LocalTime;

public class TimeAvailabilityTest {

    @Test
    public void timeTestingTrue(){
        DaySelector Daysselection = DaySelector.always();
        TimeAvailability timeAvailability = new TimeAvailability(LocalTime.of(0,0,0), LocalTime.of(23,59,59),Daysselection);
        Assertions.assertTrue(timeAvailability.isAvailable());
    }
    @Test
    public void timeTestingFalse(){
        DaySelector Daysselection = DaySelector.always();
        TimeAvailability timeAvailability = new TimeAvailability(LocalTime.of(23,59,55), LocalTime.of(23,59,59),Daysselection);
        Assertions.assertFalse(timeAvailability.isAvailable(),"try again after 10 secs");
    }


}
