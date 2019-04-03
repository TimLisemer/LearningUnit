package learningunit.learningunit.Objects.Timetable;

import java.util.ArrayList;

import learningunit.learningunit.Objects.Organizer.Event;

public class Hour {

    private String name;
    private String colorCode;
    private transient boolean keep = false;
    private transient int HourID;
    private transient ArrayList<Event> EventList = new ArrayList<Event>();

    public Hour(String name, String ColorCode){
        this.name = name;
        this.colorCode = ColorCode;
    }

    public void setKeep(boolean keep){
        this.keep = keep;
    }

    public boolean getKeep(){
        return this.keep;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColorCode() {
        return colorCode;
    }

    public void setColorCode(String colorCode) {
        this.colorCode = colorCode;
    }

    public int getHourID() {
        return HourID;
    }

    public void setHourID(int hourID) {
        HourID = hourID;
    }

    public ArrayList<Event> getEventList() {
        return EventList;
    }

    public void addEvent(Event e){
        if(EventList == null){
            EventList = new ArrayList<Event>();
        }
        EventList.add(e);
    }
}