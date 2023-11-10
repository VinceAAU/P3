package dk.aau.student.AktuelMenu;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

public class DaySelector {
    boolean monday;
    boolean tuesday;
    boolean wednesday;
    boolean thursday;
    boolean friday;
    boolean saturday;
    boolean sunday;

    public boolean isToday(){
        return get(LocalDateTime.now().getDayOfWeek());
    }
    public static DaySelector always(){
        return new DaySelector(true, true, true, true, true, true, true);
    }

    // This could be done so much easier with bit flags, but I know for a fact that if I write them,
    // they'll show up to the exam and you guys will have no idea how to use them
    public DaySelector(boolean monday, boolean tuesday, boolean wednesday, boolean thursday, boolean friday, boolean saturday, boolean sunday){
        this.monday = monday;
        this.tuesday = tuesday;
        this.wednesday = wednesday;
        this.thursday = thursday;
        this.friday = friday;
        this.saturday = saturday;
        this.sunday = sunday;
    }

    public DaySelector(){
        this(false, false, false, false, false, false, false);
    }

    public void enable(DayOfWeek day){
        switch(day){
            case MONDAY    ->    monday = true;
            case TUESDAY   ->   tuesday = true;
            case WEDNESDAY -> wednesday = true;
            case THURSDAY  ->  thursday = true;
            case FRIDAY    ->    friday = true;
            case SATURDAY  ->  saturday = true;
            case SUNDAY    ->    sunday = true;
        }
    }

    public void disable(DayOfWeek day){
        switch(day){
            case MONDAY    ->    monday = false;
            case TUESDAY   ->   tuesday = false;
            case WEDNESDAY -> wednesday = false;
            case THURSDAY  ->  thursday = false;
            case FRIDAY    ->    friday = false;
            case SATURDAY  ->  saturday = false;
            case SUNDAY    ->    sunday = false;
        }
    }

    public boolean get(DayOfWeek day){
        return switch(day){

            case MONDAY    -> monday;
            case TUESDAY   -> tuesday;
            case WEDNESDAY -> wednesday;
            case THURSDAY  -> thursday;
            case FRIDAY    -> friday;
            case SATURDAY  -> saturday;
            case SUNDAY    -> sunday;
        };
    }
}
