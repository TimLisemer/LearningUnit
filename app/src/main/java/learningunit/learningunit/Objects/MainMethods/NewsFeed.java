package learningunit.learningunit.Objects.MainMethods;

import android.app.Activity;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.joda.time.DateTime;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import learningunit.learningunit.Objects.API.ManageData;
import learningunit.learningunit.Objects.Organizer.Event;
import learningunit.learningunit.Objects.Organizer.EventMethods;
import learningunit.learningunit.Objects.Organizer.Exam;
import learningunit.learningunit.Objects.Organizer.Homework;
import learningunit.learningunit.Objects.Organizer.Presentation;
import learningunit.learningunit.R;
import learningunit.learningunit.beforeStart.FirstScreen;

public class NewsFeed {

    public static String NewsString(Activity activity){
        String NewsString = "";
        Gson gson = new Gson();

        if(ManageData.OfflineAccount == 2 && ManageData.InternetAvailable(activity)) {
            ManageData.LoadOrganizer();
        }

        ArrayList<Homework> hl = new ArrayList<Homework>();
        ArrayList<Exam> el = new ArrayList<Exam>();
        ArrayList<Presentation> pl = new ArrayList<Presentation>();

        String json = FirstScreen.tinyDB.getString("Homework");
        ArrayList<Homework> homworklist = new ArrayList<Homework>();
        if(json.equals("")){
            homworklist = new ArrayList<Homework>();
        }else {
            Type type = new TypeToken<ArrayList<Homework>>() {
            }.getType();
            homworklist = gson.fromJson(json, type);
        }

        String json1 = FirstScreen.tinyDB.getString("Exam");
        ArrayList<Exam> examlist = new ArrayList<Exam>();
        if(json1.equals("")){
            examlist = new ArrayList<Exam>();
        }else {
            Type type = new TypeToken<ArrayList<Exam>>() {
            }.getType();
            examlist = gson.fromJson(json1, type);
        }

        String json2 = FirstScreen.tinyDB.getString("Presentation");
        ArrayList<Presentation> presentationlist = new ArrayList<Presentation>();
        if(json2.equals("")){
            presentationlist = new ArrayList<Presentation>();
        }else {
            Type type = new TypeToken<ArrayList<Presentation>>() {
            }.getType();
            presentationlist = gson.fromJson(json2, type);
        }

        Calendar ca = Calendar.getInstance();
        Date d = ca.getTime();
        DateTime dateTime = new DateTime(d);
        Event currentTimeEvent = new Event(dateTime.getDayOfMonth(), dateTime.getMonthOfYear(), dateTime.getYear());


        for(Homework homework : homworklist){
            if((EventMethods.isYounger((Event) homework, currentTimeEvent))){
                hl.add(homework);
            }
        }
        for(Exam exam : examlist){
            if((EventMethods.isYounger((Event) exam, currentTimeEvent))){
                el.add(exam);
            }
        }
        for(Presentation pre : presentationlist){
            if((EventMethods.isYounger((Event) pre, currentTimeEvent))){
                pl.add(pre);
            }
        }

        if(hl.size() == 0 && el.size() == 0 && pl.size() == 0){
            NewsString = activity.getResources().getString(R.string.NoActiveEvents);
        }else{
            NewsString = allString(hl, el, pl, activity);

        }

        return NewsString;
    }

    private static String allString(ArrayList<Homework> halist, ArrayList<Exam> exlist, ArrayList<Presentation> prlist, Activity activity){
        String all = activity.getResources().getString(R.string.AllActiveEvents) + "     ";

        if(halist.size() > 0) {
            all = all + activity.getResources().getString(R.string.Homework) + ":   ";
            for (int i = 0; i < halist.size(); i++) {
                Homework h = halist.get(i);
                if (i == halist.size() - 1) {
                    all = all + h.getDay() + "." + h.getMonth() + "." + h.getYear() + " " + h.getHour().getName() + " - " + h.getTitle() + "       ";
                } else {
                    all = all + h.getDay() + "." + h.getMonth() + "." + h.getYear() + " " + h.getHour().getName() + " - " + h.getTitle() + ",   ";
                }
            }
        }
        if(exlist.size() > 0) {
            all = all + activity.getResources().getString(R.string.Exam) + ":   ";
            for (int i = 0; i < exlist.size(); i++) {
                Exam e = exlist.get(i);
                if (i == exlist.size() - 1) {
                    all = all + e.getDay() + "." + e.getMonth() + "." + e.getYear() + " " + e.getHour().getName() + " - " + e.getTitle() + "       ";
                } else {
                    all = all + e.getDay() + "." + e.getMonth() + "." + e.getYear() + " " + e.getHour().getName() + " - " + e.getTitle() + ",   ";
                }
            }
        }
        if(prlist.size() > 0) {
            all = all + activity.getResources().getString(R.string.Presentations) + ":   ";
            for (int i = 0; i < prlist.size(); i++) {
                Presentation p = prlist.get(i);
                if (i == prlist.size() - 1) {
                    all = all + p.getDay() + "." + p.getMonth() + "." + p.getYear() + " " + p.getHour().getName() + " - " + p.getTitle() + "       ";
                } else {
                    all = all + p.getDay() + "." + p.getMonth() + "." + p.getYear() + " " + p.getHour().getName() + " - " + p.getTitle() + ",   ";
                }
            }
        }

        return all;
    }

}
