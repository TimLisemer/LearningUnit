package learningunit.learningunit.Objects.Organizer;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class EventMethods {

    public static File fullscreen;

    public static ArrayList<Event> SortEventList(ArrayList<Event> elist){
        ArrayList<Event> eventlist = elist;
        Event temp;
        Event[] zusortieren = new Event[eventlist.size()];
        for(int i = 0; i < eventlist.size(); i++){
            zusortieren[i] = eventlist.get(i);
        }

        for(int i=1; i<zusortieren.length; i++) {
            for(int j=0; j<zusortieren.length-i; j++) {
                if(isYounger(zusortieren[j], zusortieren[j+1])) {
                    temp=zusortieren[j];
                    zusortieren[j]=zusortieren[j+1];
                    zusortieren[j+1]=temp;
                }

            }
        }

        eventlist = new ArrayList<Event>(Arrays.asList(zusortieren));
        return eventlist;
    }

    public static boolean isYounger(Event a, Event b){
        if(a.getYear() == b.getYear()){
            if(a.getMonth() == b.getMonth()){
                if(a.getDay() == b.getDay()){
                    return false;
                }else if(a.getDay() > b.getDay()){
                    return true;
                }else{
                    return false;
                }
            }else if(a.getMonth() > b.getMonth()){
                return  true;
            }else{
                return false;
            }
        }else if(a.getYear() > b.getYear()){
            return true;
        }else{
            return false;
        }
    }



}
