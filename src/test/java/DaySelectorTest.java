import dk.aau.student.AktuelMenu.DaySelector;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

public class DaySelectorTest {
    @Test
    public void doesNeverActuallyGiveAnObjectWhereEverythingIsFalseTest(){
        DaySelector never = DaySelector.never();

        Assertions.assertFalse(never.get(DayOfWeek.MONDAY));
        Assertions.assertFalse(never.get(DayOfWeek.TUESDAY));
        Assertions.assertFalse(never.get(DayOfWeek.WEDNESDAY));
        Assertions.assertFalse(never.get(DayOfWeek.THURSDAY));
        Assertions.assertFalse(never.get(DayOfWeek.FRIDAY));
        Assertions.assertFalse(never.get(DayOfWeek.SATURDAY));
        Assertions.assertFalse(never.get(DayOfWeek.SUNDAY));
    }

    @Test
    public void doesAlwaysActuallyGiveAnObjectWhereEverythingIsTrueTest(){
        DaySelector always = DaySelector.always();

        Assertions.assertTrue(always.get(DayOfWeek.MONDAY));
        Assertions.assertTrue(always.get(DayOfWeek.TUESDAY));
        Assertions.assertTrue(always.get(DayOfWeek.WEDNESDAY));
        Assertions.assertTrue(always.get(DayOfWeek.THURSDAY));
        Assertions.assertTrue(always.get(DayOfWeek.FRIDAY));
        Assertions.assertTrue(always.get(DayOfWeek.SATURDAY));
        Assertions.assertTrue(always.get(DayOfWeek.SUNDAY));
    }

    @Test
    public void isTodayTest(){
        DayOfWeek today = LocalDateTime.now().getDayOfWeek();
        DaySelector daySelector = DaySelector.never();
        daySelector.enable(today);

        Assertions.assertTrue(daySelector.isToday());
    }

    @Test
    public void enablerTest(){
        DaySelector ds = DaySelector.never();
        ds.enable(DayOfWeek.MONDAY);

        Assertions.assertTrue(ds.get(DayOfWeek.MONDAY));
        Assertions.assertFalse(ds.get(DayOfWeek.FRIDAY));
    }

    @Test
    public void disablerTest(){
        DaySelector ds = DaySelector.always();
        ds.disable(DayOfWeek.MONDAY);

        Assertions.assertFalse(ds.get(DayOfWeek.MONDAY));
        Assertions.assertTrue(ds.get(DayOfWeek.FRIDAY));
    }

    //I would've liked to test the .get() method too, but I can't think of a way to do that
}
