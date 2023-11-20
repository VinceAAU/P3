package dk.aau.student.AktuelMenu;

import org.json.JSONObject;
import org.json.JSONString;

import java.sql.Time;
import java.time.DayOfWeek;
import java.time.LocalTime;

public class TimeAvailability implements JSONString {
    private LocalTime start;
    private LocalTime end;

    private DaySelector days;

    public TimeAvailability(LocalTime start, LocalTime end, DaySelector daySelector){
        this.start = start;
        this.end = end;
        days = daySelector;
    }

    public boolean isAvailable(){
        return
                days.isToday() &&
                start.isBefore(LocalTime.now()) &&
                end.isAfter(LocalTime.now());
    }

    @Override
    public String toJSONString() {
        JSONObject availability = new JSONObject();
        availability.put("start", start.toString());
        availability.put("end", end.toString());
        availability.put("days", days.toArray());

        return availability.toString();
    }

    public static TimeAvailability fromJSONObject(JSONObject timeJSON) {
        return new TimeAvailability(
                LocalTime.parse(timeJSON.getString("start")),
                LocalTime.parse(timeJSON.getString("end")),
                DaySelector.fromJSONArray(timeJSON.getJSONArray("days"))
        );
    }
}