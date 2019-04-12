package learningunit.learningunit.Objects.Organizer;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import learningunit.learningunit.R;
import learningunit.learningunit.beforeStart.FirstScreen;

public class DashboardFragmentMethods {


    public DashboardFragmentMethods(Activity activity, ListView DashboardView){

        Gson gson = new Gson();

        ArrayList<Homework> homeworkList = new ArrayList<Homework>();
        String json = FirstScreen.tinyDB.getString("Homework");
        if(json.equals("")){
            homeworkList = new ArrayList<Homework>();
        }else {
            Type type = new TypeToken<ArrayList<Homework>>() {
            }.getType();
            homeworkList = gson.fromJson(json, type);
        }

        ArrayList<Exam> examlist = new ArrayList<Exam>();
        String json1 = FirstScreen.tinyDB.getString("Exam");
        if(json1.equals("")){
            examlist = new ArrayList<Exam>();
        }else {
            Type type = new TypeToken<ArrayList<Exam>>() {
            }.getType();
            examlist = gson.fromJson(json1, type);
        }

        ArrayList<Presentation> prelist = new ArrayList<Presentation>();
        String json2 = FirstScreen.tinyDB.getString("Presentation");
        if(json2.equals("")){
            prelist = new ArrayList<Presentation>();
        }else {
            Type type = new TypeToken<ArrayList<Presentation>>() {
            }.getType();
            prelist = gson.fromJson(json2, type);
        }

        DashboardView.setAdapter(new HomeCustomAdapter(examlist, homeworkList, prelist, activity, 88));

    }














    public DashboardFragmentMethods(Activity activity, ListView homeworkview, ListView examview){

        Gson gson = new Gson();

        ArrayList<Homework> homeworkList = new ArrayList<Homework>();
        String json = FirstScreen.tinyDB.getString("Homework");
        if(json.equals("")){
            homeworkList = new ArrayList<Homework>();
        }else {
            Type type = new TypeToken<ArrayList<Homework>>() {
            }.getType();
            homeworkList = gson.fromJson(json, type);
        }

        ArrayList<Exam> examlist = new ArrayList<Exam>();
        String json1 = FirstScreen.tinyDB.getString("Exam");
        if(json1.equals("")){
            examlist = new ArrayList<Exam>();
        }else {
            Type type = new TypeToken<ArrayList<Exam>>() {
            }.getType();
            examlist = gson.fromJson(json1, type);
        }

        if(homeworkList.size() > 0){
            homeworkview.setAdapter(new HomeCustomAdapter(activity, homeworkList));
        }

        if(examlist.size() > 0){
            examview.setAdapter(new HomeCustomAdapter(examlist, activity));
        }


    }












}
