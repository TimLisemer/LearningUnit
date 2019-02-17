package learningunit.learningunit.Objects.Timetable;

import java.util.ArrayList;

public class Week {

    int DayCount;
    ArrayList<Day> week = new ArrayList<Day>();

    public Week(int DayCount){
        this.DayCount = DayCount;
    }

    public void addDay(Day d){
        if(week.size() != DayCount) {
            week.add(d);
        }
    }

    public Day getDay(int Day){
        return week.get(Day);
    }

    public ArrayList<Day> getDayList(){
        return week;
    }

}
