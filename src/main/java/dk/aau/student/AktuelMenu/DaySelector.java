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
}
