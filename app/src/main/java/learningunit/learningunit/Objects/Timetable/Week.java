package learningunit.learningunit.Objects.Timetable;

import java.util.ArrayList;

public class Week {

    int DayCount;
    ArrayList<Day> week = new ArrayList<Day>();
    private int weekID;

    public Week(int DayCount){
        this.DayCount = DayCount;
    }

    public void setWeekID(int weekID){
        this.weekID = weekID;
    }

    public int getWeekID(){
        return this.weekID;
    }

    public void addDay(Day d){
        if(week.size() != DayCount) {
            week.add(d);
        }
    }

    public ArrayList<Day> getDayList(){
        return week;
    }

}
