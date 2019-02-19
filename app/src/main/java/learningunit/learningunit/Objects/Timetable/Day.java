package learningunit.learningunit.Objects.Timetable;

import android.graphics.drawable.ColorDrawable;

import java.util.ArrayList;

public class Day {

    private ArrayList<Hour> hour = new ArrayList<Hour>();
    private String Name;

    public Day (String Name){
        this.Name = Name;
    }

    public String getName(){
        return this.Name;
    }

    public void addHour(String Name, String color){
        Hour h = new Hour(Name, color);
        hour.add(h);
    }

    public int getHourCount(){
        return hour.size();
    }

    public Hour getHour(int Hour){
        return hour.get(Hour);
    }

    public ArrayList<Hour> getHourList(){
        return hour;
    }


}
