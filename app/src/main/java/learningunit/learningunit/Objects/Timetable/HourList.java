package learningunit.learningunit.Objects.Timetable;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.widget.Button;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import learningunit.learningunit.Objects.API.ManageData;
import learningunit.learningunit.Objects.API.RequestHandler;
import learningunit.learningunit.R;
import learningunit.learningunit.beforeStart.FirstScreen;
import learningunit.learningunit.menu.MainActivity;
import learningunit.learningunit.menu.organizer.timetable.Timetable;
import learningunit.learningunit.menu.organizer.timetable.TimetableShowcase;

public class HourList {

    public static ArrayList<Hour> hourList =  new ArrayList<Hour>();
    private static ArrayList<String> namelist =  new ArrayList<String>();

    public static boolean weekly, currentWeek, currentWeekShowcase = false;


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

        }else if(colorId == android.graphics.Color.parseColor("#D8D8D8")) {
            d.addHour(b.getText().toString(), "#D8D8D8");
        }
    }


    public static void noConnection(final Boolean type, final Context ctx, final Week weekA, final Week weekB){
        if(!(ManageData.InternetAvailable(ctx))) {
            AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
            builder.setCancelable(true);
            builder.setNegativeButton(ctx.getResources().getString(R.string.Cancel), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    FirstScreen.tinyDB.putString("WeekA", "");
                    FirstScreen.tinyDB.putString("WeekB", "");
                    Intent intent = new Intent(ctx, MainActivity.class);
                    ctx.startActivity(intent);
                }
            });
            builder.setPositiveButton(ctx.getResources().getString(R.string.TryAgain), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                    noConnection(type, ctx, weekA, weekB);
                }
            });
            builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    dialog.cancel();
                    noConnection(type, ctx, weekA, weekB);
                }
            });
            builder.setTitle(ctx.getResources().getString(R.string.NoNetworkConnection));
            builder.setMessage(ctx.getResources().getString(R.string.NoNetworkInfo));
            builder.show();
        }else{
            Gson gson = new Gson();
            if(type) {
                String[][] NameArray = new String[weekA.getDayList().size()][weekA.getDayList().get(0).getHourList().size()];
                String[][] ColourArray = new String[weekA.getDayList().size()][weekA.getDayList().get(0).getHourList().size()];
                String[] DayNameArray = new String[weekA.getDayList().size()];

                for (int g = 0; g < weekA.getDayList().size(); g++) {
                    for (int k = 0; k < weekA.getDayList().get(g).getHourList().size(); k++) {
                        NameArray[g][k] = weekA.getDayList().get(g).getHourList().get(k).getName();
                        String co = weekA.getDayList().get(g).getHourList().get(k).getColorCode().replace("#", "");
                        ColourArray[g][k] = co;
                        DayNameArray[g] = weekA.getDayList().get(g).getName();
                    }
                }

                LinkedHashMap<String, String> params = new LinkedHashMap<>();
                params.put("id", ManageData.getUserID() + "");
                params.put("Tage", weekA.getDayList().size() + "");
                params.put("Stunde", weekA.getDayList().get(0).getHourCount() + "");
                params.put("jsonNameArray", gson.toJson(NameArray));
                params.put("jsonColourArray", gson.toJson(ColourArray));
                params.put("jsonDayNameArray", gson.toJson(DayNameArray));

                RequestHandler requestHandler = new RequestHandler();
                String sd = requestHandler.sendPostRequest(MainActivity.URL_insertWeek, params);

                if (ManageData.LoadTimetable(false, ManageData.getUserID())) {
                    FirstScreen.tinyDB.putString("WeekA", "");
                    FirstScreen.tinyDB.putString("WeekB", "");
                    Intent intent = new Intent(ctx, TimetableShowcase.class);
                    ctx.startActivity(intent);
                }
            }else{
                String[][] NameArray = new String[weekA.getDayList().size() * 2][weekA.getDayList().get(0).getHourList().size()];
                String[][] ColourArray = new String[weekA.getDayList().size() * 2][weekA.getDayList().get(0).getHourList().size()];
                String[] DayNameArray = new String[weekA.getDayList().size() * 2];

                for (int g = 0; g < weekA.getDayList().size(); g++) {
                    for (int k = 0; k < weekA.getDayList().get(g).getHourList().size(); k++) {
                        NameArray[g][k] = weekA.getDayList().get(g).getHourList().get(k).getName();
                        String co = weekA.getDayList().get(g).getHourList().get(k).getColorCode().replace("#", "");
                        ColourArray[g][k] = co;
                        DayNameArray[g] = weekA.getDayList().get(g).getName();
                    }
                }


                for (int g = 0; g < weekB.getDayList().size(); g++) {
                    for (int k = 0; k < weekB.getDayList().get(g).getHourList().size(); k++) {
                        NameArray[g + weekA.getDayList().size()][k] = weekB.getDayList().get(g).getHourList().get(k).getName();
                        String co = weekB.getDayList().get(g).getHourList().get(k).getColorCode().replace("#", "");
                        ColourArray[g + weekA.getDayList().size()][k] = co;
                        DayNameArray[g + weekB.getDayList().size()] = weekB.getDayList().get(g).getName();
                    }
                }

                LinkedHashMap<String, String> params = new LinkedHashMap<>();
                params.put("id", ManageData.getUserID() + "");
                params.put("Tage", weekA.getDayList().size() + "");
                params.put("Stunde", weekA.getDayList().get(0).getHourCount() + "");
                params.put("jsonNameArray", gson.toJson(NameArray));
                params.put("jsonColourArray", gson.toJson(ColourArray));
                params.put("jsonDayNameArray", gson.toJson(DayNameArray));

                RequestHandler requestHandler = new RequestHandler();
                String sd = requestHandler.sendPostRequest(MainActivity.URL_insertWeek, params);


                if (ManageData.LoadTimetable(false, ManageData.getUserID())) {
                    FirstScreen.tinyDB.putString("WeekA", "");
                    FirstScreen.tinyDB.putString("WeekB", "");
                    Intent intent = new Intent(ctx, TimetableShowcase.class);
                    ctx.startActivity(intent);
                }
            }
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
