package learningunit.learningunit.Objects.Timetable;

import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.widget.Button;

import java.util.ArrayList;

public class HourList {

    public static ArrayList<Hour> hourList =  new ArrayList<Hour>();
    private static ArrayList<String> namelist =  new ArrayList<String>();


    public static void addHour(String name){

        if(!(namelist.contains(name))){
            namelist.add(name);
            Hour hour = new Hour(name, "#D8D8D8");
            hourList.add(hour);
            Log.d("Hour", "Succesfully created Hour with name " + name);
        }else{
            Log.d("Hour", "Could not create Hour with name " + name + ", name already given");
        }
    }

    public static void checkColor(Day d, Button b, ColorDrawable color){
        int colorId = color.getColor();
        if(colorId == android.graphics.Color.parseColor("#FF0000")){
            d.addHour(b.getText().toString(), "#FF0000");
        }else if(colorId == android.graphics.Color.parseColor("#FF4500")) {
            d.addHour(b.getText().toString(), "#FF4500");
        }else if(colorId == android.graphics.Color.parseColor("#800000")) {
            d.addHour(b.getText().toString(), "#800000");
        }else if(colorId == android.graphics.Color.parseColor("#FA8072")) {
            d.addHour(b.getText().toString(), "#FA8072");
        }else if(colorId == android.graphics.Color.parseColor("#FFFF00")) {
            d.addHour(b.getText().toString(), "#FFFF00");
        }else if(colorId == android.graphics.Color.parseColor("#000000")) {
            d.addHour(b.getText().toString(), "#000000");


        }else if(colorId == android.graphics.Color.parseColor("#008000")) {
            d.addHour(b.getText().toString(), "#008000");
        }else if(colorId == android.graphics.Color.parseColor("#006400")) {
            d.addHour(b.getText().toString(), "#006400");
        }else if(colorId == android.graphics.Color.parseColor("#7CFC00")) {
            d.addHour(b.getText().toString(), "#7CFC00");
        }else if(colorId == android.graphics.Color.parseColor("#90EE90")) {
            d.addHour(b.getText().toString(), "#90EE90");
        }else if(colorId == android.graphics.Color.parseColor("#6B8E23")) {
            d.addHour(b.getText().toString(), "#6B8E23");
        }else if(colorId == android.graphics.Color.parseColor("#A0522D")) {
            d.addHour(b.getText().toString(), "#A0522D");


        }else if(colorId == android.graphics.Color.parseColor("#0000FF")) {
            d.addHour(b.getText().toString(), "#0000FF");
        }else if(colorId == android.graphics.Color.parseColor("#00BFFF")) {
            d.addHour(b.getText().toString(), "#00BFFF");
        }else if(colorId == android.graphics.Color.parseColor("#483D8B")) {
            d.addHour(b.getText().toString(), "#483D8B");
        }else if(colorId == android.graphics.Color.parseColor("#8A2BE2")) {
            d.addHour(b.getText().toString(), "#8A2BE2");
        }else if(colorId == android.graphics.Color.parseColor("#B0E0E6")) {
            d.addHour(b.getText().toString(), "#B0E0E6");
        }else if(colorId == android.graphics.Color.parseColor("#5F9EA0")) {
            d.addHour(b.getText().toString(), "#5F9EA0");
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
