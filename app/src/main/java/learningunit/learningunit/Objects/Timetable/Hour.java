package learningunit.learningunit.Objects.Timetable;

import android.graphics.drawable.ColorDrawable;

public class Hour {

    private String name;
    private ColorDrawable colorCode = null;

    public Hour(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ColorDrawable getColorCode() {
        return colorCode;
    }

    public void setColorCode(ColorDrawable colorCode) {
        this.colorCode = colorCode;
    }
}