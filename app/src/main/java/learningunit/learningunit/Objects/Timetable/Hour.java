package learningunit.learningunit.Objects.Timetable;

import java.util.ArrayList;

import learningunit.learningunit.Objects.Organizer.Event;

public class Hour {

    private String name;
    private String colorCode;
    private transient boolean keep = false;
    private transient int HourID;

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

}