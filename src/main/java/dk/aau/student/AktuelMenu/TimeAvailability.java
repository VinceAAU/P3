package dk.aau.student.AktuelMenu;

import java.time.LocalTime;

public class TimeAvailability{
    private LocalTime start;
    private LocalTime end;

    private DaySelector days;

    public TimeAvailability(LocalTime start, LocalTime end, DaySelector daySelector){
        this.start = start;
        this.end = end;
        days = daySelector;
    }
}