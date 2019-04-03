package learningunit.learningunit.Objects.Organizer;

import java.util.ArrayList;
import java.util.Date;

public class Event {

    private int day, month, year;
    private String title, description;

    public Event(int d,int m,int y){
        day = d;
        month = m;
        year = y;
    }

    public Event(){}

    public void setTitle(String t){this.title = t;}

    public String getTitle() {return title;}
    public int getDay(){return day;}
    public int getMonth(){return month;}
    public int getYear(){return year;}

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
