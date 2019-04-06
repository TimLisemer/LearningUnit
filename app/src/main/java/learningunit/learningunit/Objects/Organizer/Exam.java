package learningunit.learningunit.Objects.Organizer;

import learningunit.learningunit.Objects.Timetable.Hour;

public class Exam extends Event {

    private Hour hour;
    private int grade;

    public Exam(int d,int m,int y){
        super(d, m, y);
    }

    public void setGrade(int grade){
        if(grade >= 0 && grade <= 15){
            this.grade = grade;
        }else{
            throw new IllegalArgumentException("Invalid grade number --> Only 0-15 --> Entered: " + grade);
        }
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

}
