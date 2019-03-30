package learningunit.learningunit.Objects.Organizer;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import learningunit.learningunit.Objects.API.ManageData;
import learningunit.learningunit.Objects.Timetable.Day;
import learningunit.learningunit.Objects.Timetable.Hour;
import learningunit.learningunit.Objects.Timetable.HourList;
import learningunit.learningunit.Objects.Timetable.Week;
import learningunit.learningunit.R;
import learningunit.learningunit.beforeStart.FirstScreen;
import learningunit.learningunit.menu.MainActivity;
import learningunit.learningunit.menu.organizer.timetable.TimetableShowcase;

public class HomeFragmentMethods {

    int fday, fmonth, fyear;
    Date fdate;

    public HomeFragmentMethods(final View fragmentView, Context ctx){
        if(ctx == null){
            throw new IllegalArgumentException("Invalid Context --> Null");
        }
        HomeworkClick(fragmentView, ctx);
    }

    public void HomeworkClick(final View fragmentView, final Context ctx){

        Button homework = (Button) fragmentView.findViewById(R.id.fragment_organizer_home_hausaufgaben);
        homework.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ScrollView newEvent = (ScrollView) fragmentView.findViewById(R.id.fragment_organizer_home_NewEventLayout);
                ConstraintLayout homeLayout = (ConstraintLayout) fragmentView.findViewById(R.id.fragment_organizer_home_MainLayout);
                newEvent.setVisibility(View.VISIBLE);
                homeLayout.setVisibility(View.GONE);
            }
        });


        Button newEventBack = (Button) fragmentView.findViewById(R.id.organizer_NewEventBack);
        newEventBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ScrollView newEvent = (ScrollView) fragmentView.findViewById(R.id.fragment_organizer_home_NewEventLayout);
                ConstraintLayout homeLayout = (ConstraintLayout) fragmentView.findViewById(R.id.fragment_organizer_home_MainLayout);
                newEvent.setVisibility(View.GONE);
                homeLayout.setVisibility(View.VISIBLE);
            }
        });

        CalendarView cv = (CalendarView) fragmentView.findViewById(R.id.fragment_organizer_home_NewEvent_Calendar);
        cv.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                fday = dayOfMonth;
                fmonth = month + 1;
                fyear = year;
            }
        });

        Button newEventNext = (Button) fragmentView.findViewById(R.id.organizer_NewEventNext);
        newEventNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final TextView error = (TextView) fragmentView.findViewById(R.id.fragment_organizer_home_NewEvent_ErrorDate);
                int rday, rmonth, ryear;
                Date c = Calendar.getInstance().getTime();
                rday = Integer.parseInt(new SimpleDateFormat("dd").format(c));
                rmonth = Integer.parseInt(new SimpleDateFormat("MM").format(c));
                ryear = Integer.parseInt(new SimpleDateFormat("yyyy").format(c));

                Homework event = new Homework(fday, fmonth, fyear);

                if(ryear == fyear){
                    if(rmonth == fmonth){
                        if(rday < fday){
                            ContinueHomework(fragmentView, event, ctx);
                            error.setVisibility(View.GONE);
                        }else{
                            error.setVisibility(View.VISIBLE);
                        }
                    }else if(rmonth < fmonth){
                        ContinueHomework(fragmentView, event, ctx);
                        error.setVisibility(View.GONE);
                    }else{
                        error.setVisibility(View.VISIBLE);
                    }
                }else if(ryear < fyear){
                    ContinueHomework(fragmentView, event, ctx);
                    error.setVisibility(View.GONE);
                }else{
                    error.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    public void ContinueHomework(final View fragmentView, Homework event, Context ctx){

        if(ManageData.OfflineAccount == 2){
            if(ManageData.InternetAvailable(ctx)) {
                ManageData.LoadTimetable(false, ManageData.getUserID(), ctx, false);
            }else{
                Toast.makeText(ctx, ctx.getResources().getString(R.string.NoNetworkConnection), Toast.LENGTH_SHORT).show();
            }
        }

        if(FirstScreen.tinyDB.getString("WeekA").equals("")) {
            Log.d("homework:", "Continue");
        }else {
            ArrayList<Hour> hourList = new ArrayList<Hour>();
            Gson gson = new Gson();
            String json = FirstScreen.tinyDB.getString("WeekA");
            Type type = new TypeToken<Week>() {}.getType();

            if(FirstScreen.tinyDB.getString("WeekB").equals("")) {
                Week weekA = gson.fromJson(json, type);
                weekA.getDayList().get(0).getHourList();
                for(Day d : weekA.getDayList()){
                    for(Hour h : d.getHourList()){
                        boolean multi = false;
                        for(Hour hour : hourList){
                            if(hour.getName().equals(h.getName()) && hour.getColorCode().equals(h.getColorCode())){
                                multi = true;
                                break;
                            }
                        }
                        if(!multi){
                            hourList.add(h);
                        }
                    }
                }
            }else{
                Week weekB = gson.fromJson(json, type);
                weekB.getDayList().get(0).getHourList();
                for(Day d : weekB.getDayList()){
                    for(Hour h : d.getHourList()){
                        boolean multi = false;
                        for(Hour hour : hourList){
                            if(hour.getName().equals(h.getName()) && hour.getColorCode().equals(h.getColorCode())){
                                multi = true;
                                break;
                            }
                        }
                        if(!multi){
                            hourList.add(h);
                        }
                    }
                }
            }



            Log.d("homework:", hourList.size() + "");
            for (Hour h : hourList) {
                Log.d("Hour", h.getName() + "   ---  " + h.getColorCode());
            }



        }

    }































}
