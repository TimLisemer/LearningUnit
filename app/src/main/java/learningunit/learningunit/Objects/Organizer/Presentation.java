package learningunit.learningunit.Objects.Organizer;

import learningunit.learningunit.Objects.Timetable.Hour;

public class Presentation extends Event {

    private Hour hour;
    private int grade;

    public Presentation(int d,int m,int y){
        super(d, m, y);
    }

    public void setGrade(int grade){
        this.grade = grade;
    }

    public int getGrade(){
        return this.grade;
    }

    public Hour getHour() {
        return hour;
    }

    public void setHour(Hour hour) {
        this.hour = hour;
    }

    @Override
    public String toString() {
        super.toString();
        return "Presentation{" +
                "hour=" + hour +
                ", grade=" + grade +
                '}';
    }
}
