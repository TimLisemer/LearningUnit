package learningunit.learningunit.Objects.Timetable;

import android.util.Log;

import java.util.ArrayList;

public class HourList {

    public static ArrayList<Hour> hourList =  new ArrayList<Hour>();
    private static ArrayList<String> namelist =  new ArrayList<String>();


    public static void addHour(String name){

        if(!(namelist.contains(name))){
            namelist.add(name);
            Hour hour = new Hour(name);
            hourList.add(hour);
            Log.d("Hour", "Succesfully created Hour with name " + name);
        }else{
            Log.d("Hour", "Could not create Hour with name " + name + ", name already given");
        }
    }

    public static void clearList(){
        /*
        for(int i = 0; i < hourList.size(); i++){
            hourList.remove(i);
        }

        for(int i = 0; i < namelist.size(); i++){
            namelist.remove(i);
        }
        */
    }



}
