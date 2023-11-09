package dk.aau.student.AktuelMenu;

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
        return switch (LocalDateTime.now().getDayOfWeek()){
            case MONDAY -> this.monday;
            case TUESDAY -> this.tuesday;
            case WEDNESDAY -> this.wednesday;
            case THURSDAY -> this.thursday;
            case FRIDAY -> this.friday;
            case SATURDAY -> this.saturday;
            case SUNDAY -> this.sunday;
        };
    }
    static DaySelector always(){
        DaySelector sevenDays = new DaySelector();
        sevenDays.monday = true;
        sevenDays.tuesday = true;
        sevenDays.wednesday = true;
        sevenDays.thursday = true;
        sevenDays.friday = true;
        sevenDays.saturday = true;
        sevenDays.sunday = true;
        return sevenDays;
    }
}
