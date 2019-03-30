package learningunit.learningunit.Objects.Organizer;

import learningunit.learningunit.Objects.Timetable.Hour;

public class Homework extends Event {

    private Hour hour;
    private boolean done;

    public Homework(int d,int m,int y){
        super(d, m, y);
    }

    public Hour getHour() {
        return hour;
    }

    public void setHour(Hour hour) {
        this.hour = hour;
    }

    public boolean getDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }
}
