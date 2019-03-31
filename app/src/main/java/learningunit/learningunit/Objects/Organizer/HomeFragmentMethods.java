package learningunit.learningunit.Objects.Organizer;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
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
import learningunit.learningunit.Objects.Timetable.Week;
import learningunit.learningunit.R;
import learningunit.learningunit.beforeStart.FirstScreen;
import learningunit.learningunit.menu.MainActivity;

public class HomeFragmentMethods {

    int fday, fmonth, fyear;
    Hour Selected;
    public static Boolean HourChosen = false;
    public static Button downHour[];
    public static Button downHour1[];

    public HomeFragmentMethods(final View fragmentView, final Activity activity){
        if(activity == null){
            throw new IllegalArgumentException("Invalid activity --> Null");
        }
        HomeworkClick(fragmentView, activity);
    }

    public void HomeworkClick(final View fragmentView, final Activity activity){

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
                            ContinueHomework(fragmentView, event, activity);
                            error.setVisibility(View.GONE);
                        }else{
                            error.setVisibility(View.VISIBLE);
                        }
                    }else if(rmonth < fmonth){
                        ContinueHomework(fragmentView, event, activity);
                        error.setVisibility(View.GONE);
                    }else{
                        error.setVisibility(View.VISIBLE);
                    }
                }else if(ryear < fyear){
                    ContinueHomework(fragmentView, event, activity);
                    error.setVisibility(View.GONE);
                }else{
                    error.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    public void ContinueHomework(final View fragmentView, Homework event, final Activity activity){

        HourChosen = false;
        Selected = null;
        final TextView hourinfo = (TextView) fragmentView.findViewById(R.id.fragment_organizer_home_NewHomework_HourInfo);
        hourinfo.setText(activity.getResources().getString(R.string.NoHourNameChosen));

        if(ManageData.OfflineAccount == 2){
            if(ManageData.InternetAvailable(activity)) {
                ManageData.LoadTimetable(false, ManageData.getUserID(), activity, false);
            }else{
                Toast.makeText(activity, activity.getResources().getString(R.string.NoNetworkConnection), Toast.LENGTH_SHORT).show();
            }
        }

        if(FirstScreen.tinyDB.getString("WeekA").equals("")) {
            Log.d("homework:", "Continue");
        }else {
            final ArrayList<Hour> hourList = new ArrayList<Hour>();
            Gson gson = new Gson();
            String json = FirstScreen.tinyDB.getString("WeekA");
            Type type = new TypeToken<Week>() {}.getType();

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

            if(!(FirstScreen.tinyDB.getString("WeekB").equals(""))) {
                json = FirstScreen.tinyDB.getString("WeekB");
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

            for(Hour h : hourList){
                Log.d("Hours", h.getName() + " --- " + h.getColorCode());
            }

            fragmentView.findViewById(R.id.fragment_organizer_home_NewEventLayout).setVisibility(View.GONE);
            fragmentView.findViewById(R.id.fragment_organizer_home_NewHomeworkLayout).setVisibility(View.VISIBLE);

            TextView DateShowcase = (TextView) fragmentView.findViewById(R.id.fragment_organizer_home_NewHomework_Date);
            DateShowcase.setText(event.getDay() + "." + event.getMonth() + "." + event.getYear());

            final EditText titel = (EditText) fragmentView.findViewById(R.id.fragment_organizer_home_NewHomework_TitelInput);
            final EditText description = (EditText) fragmentView.findViewById(R.id.fragment_organizer_home_NewHomework_DescriptionInput);
            titel.setText("");
            description.setText("");

            Button HourSelection = (Button) fragmentView.findViewById(R.id.fragment_organizer_home_NewHomework_HourSelection);
            Button Back = (Button) fragmentView.findViewById(R.id.fragment_organizer_home_NewHomework_Back);
            final Button Next = (Button) fragmentView.findViewById(R.id.fragment_organizer_home_NewHomework_Next);

            Back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MainActivity.hideKeyboard(activity);
                    fragmentView.findViewById(R.id.fragment_organizer_home_NewEventLayout).setVisibility(View.VISIBLE);
                    fragmentView.findViewById(R.id.fragment_organizer_home_NewHomeworkLayout).setVisibility(View.GONE);
                    Log.d("HA", "Back");
                }
            });

            Next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MainActivity.hideKeyboard(activity);
                    if(!HourChosen){
                        hourinfo.setError(activity.getResources().getString(R.string.NoHourNameChosen));
                    }else if(titel.getText().toString().trim().equals("")){
                        titel.setError(activity.getResources().getString(R.string.EmptyField));
                    }else if(description.getText().toString().trim().equals("")){
                        description.setError(activity.getResources().getString(R.string.EmptyField));
                    }
                    Log.d("HA", "Next");
                }
            });

            hourinfo.setError(null);
            titel.setError(null);
            description.setError(null);

            Selected = HourSelection(hourList, 0, fragmentView, activity, HourSelection);
            Log.d("HA", "Continue");
        }
    }




    private Hour returner;

    public Hour HourSelection(final ArrayList<Hour> hourList, final int Case, final View fragmentView, final Activity activity, Button HourSelection){ //Case: 0 = Homework
        final TextView hourinfo = (TextView) fragmentView.findViewById(R.id.fragment_organizer_home_NewHomework_HourInfo);
        HourSelection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                downHour = null;
                downHour1 = null;

                if (Case == 0) {
                    fragmentView.findViewById(R.id.fragment_organizer_home_NewHomeworkLayout).setVisibility(View.GONE);
                    fragmentView.findViewById(R.id.fragment_organizer_home_SubjectSelection_ScrollView).setVisibility(View.VISIBLE);
                }

                Button Base1 = fragmentView.findViewById(R.id.fragment_organizer_home_SubjectSelection_DOWN_Base1);
                Button Base2 = fragmentView.findViewById(R.id.fragment_organizer_home_SubjectSelection_DOWN_Base2);

                if (hourList.isEmpty()) {
                    Base1.setVisibility(View.GONE);
                    Base2.setVisibility(View.GONE);
                    fragmentView.findViewById(R.id.fragment_organizer_home_SubjectSelection_DOWN_timetableInfo).setVisibility(View.VISIBLE);
                } else {
                    Base1.setVisibility(View.VISIBLE);
                    Base2.setVisibility(View.VISIBLE);
                    fragmentView.findViewById(R.id.fragment_organizer_home_SubjectSelection_DOWN_timetableInfo).setVisibility(View.GONE);

                    downHour = new Button[hourList.size()];
                    downHour1 = new Button[hourList.size()];

                    Base1.setText(hourList.get(0).getName());
                    ColorDrawable c = new ColorDrawable();
                    c.setColor(android.graphics.Color.parseColor(hourList.get(0).getColorCode()));
                    Base1.setBackground(c);
                    if (hourList.get(0).getColorCode().equals("#000000")) {
                        Base1.setTextColor(android.graphics.Color.parseColor("#FFFFFF"));
                    } else {
                        Base1.setTextColor(android.graphics.Color.parseColor("#000000"));
                    }
                    Base2.setText(hourList.get(1).getName());
                    ColorDrawable c1 = new ColorDrawable();
                    c1.setColor(android.graphics.Color.parseColor(hourList.get(1).getColorCode()));
                    Base2.setBackground(c1);
                    if (hourList.get(1).getColorCode().equals("#000000")) {
                        Base2.setTextColor(android.graphics.Color.parseColor("#FFFFFF"));
                    } else {
                        Base2.setTextColor(android.graphics.Color.parseColor("#000000"));
                    }

                    ConstraintSet constraintSet1, constraintSet;
                    Boolean l = true, l1 = true;
                    ConstraintLayout layout = (ConstraintLayout) fragmentView.findViewById(R.id.fragment_organizer_home_SubjectSelection_DOWN1);

                    int h = 0;

                    for (int ii = 2; ii < hourList.size(); ii++) {
                        if (ii % 2 == 0) {
                            if (downHour[ii] == null) {
                                downHour[ii] = new Button(activity);
                            }

                            ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(
                                    ConstraintLayout.LayoutParams.MATCH_CONSTRAINT, ConstraintLayout.LayoutParams.WRAP_CONTENT);

                            params.setMargins(24, 16, 24, 8);

                            downHour[ii].setText(hourList.get(ii).getName());
                            downHour[ii].setId(ii);

                            ColorDrawable c2 = new ColorDrawable();
                            c2.setColor(android.graphics.Color.parseColor(hourList.get(ii).getColorCode()));
                            downHour[ii].setBackground(c2);
                            if (hourList.get(ii).getColorCode().equals("#000000")) {
                                downHour[ii].setTextColor(android.graphics.Color.parseColor("#FFFFFF"));
                            } else {
                                downHour[ii].setTextColor(android.graphics.Color.parseColor("#000000"));
                            }


                            layout.addView(downHour[ii], params);

                            constraintSet = new ConstraintSet();
                            constraintSet.clone(layout);
                            if (l == true) {
                                constraintSet.connect(ii, ConstraintSet.TOP, Base1.getId(), ConstraintSet.BOTTOM);

                                constraintSet.connect(ii, ConstraintSet.RIGHT, layout.getId(), ConstraintSet.RIGHT);
                                constraintSet.connect(ii, ConstraintSet.LEFT, fragmentView.findViewById(R.id.fragment_organizer_home_SubjectSelection_Guideline2).getId(), ConstraintSet.RIGHT);
                                l = false;
                            } else {
                                constraintSet.connect(ii, ConstraintSet.TOP, h - 2, ConstraintSet.BOTTOM);

                                constraintSet.connect(ii, ConstraintSet.RIGHT, layout.getId(), ConstraintSet.RIGHT);
                                constraintSet.connect(ii, ConstraintSet.LEFT, fragmentView.findViewById(R.id.fragment_organizer_home_SubjectSelection_Guideline2).getId(), ConstraintSet.RIGHT);
                            }

                            constraintSet.applyTo(layout);
                            h = ii + 1;
                            final int gh = ii;

                            downHour[ii].setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    ConstraintLayout layout = (ConstraintLayout) fragmentView.findViewById(R.id.fragment_organizer_home_SubjectSelection_DOWN1);
                                    for (int i = 0; i < downHour.length; i++) {
                                        layout.removeView(downHour[i]);
                                    }
                                    for (int i = 0; i < downHour1.length; i++) {
                                        layout.removeView(downHour1[i]);
                                    }

                                    if (Case == 0) {
                                        fragmentView.findViewById(R.id.fragment_organizer_home_NewHomeworkLayout).setVisibility(View.VISIBLE);
                                        fragmentView.findViewById(R.id.fragment_organizer_home_SubjectSelection_ScrollView).setVisibility(View.GONE);
                                    }
                                    returner = hourList.get(gh);
                                    HourChosen = true;
                                    hourinfo.setText(returner.getName());
                                    hourinfo.setBackgroundColor(android.graphics.Color.parseColor(returner.getColorCode()));
                                }
                            });

                        } else {

                            if (downHour1[ii] == null) {
                                downHour1[ii] = new Button(activity);
                            }
                            ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(
                                    ConstraintLayout.LayoutParams.MATCH_CONSTRAINT, ConstraintLayout.LayoutParams.WRAP_CONTENT);

                            params.setMargins(24, 16, 24, 8);

                            downHour1[ii].setText(hourList.get(ii).getName());
                            downHour1[ii].setId(ii);

                            ColorDrawable c2 = new ColorDrawable();
                            c2.setColor(android.graphics.Color.parseColor(hourList.get(ii).getColorCode()));
                            downHour1[ii].setBackground(c2);
                            if (hourList.get(ii).getColorCode().equals("#000000")) {
                                downHour1[ii].setTextColor(android.graphics.Color.parseColor("#FFFFFF"));
                            } else {
                                downHour1[ii].setTextColor(android.graphics.Color.parseColor("#000000"));
                            }

                            layout.addView(downHour1[ii], params);


                            constraintSet1 = new ConstraintSet();
                            constraintSet1.clone(layout);
                            if (l1 == true) {
                                constraintSet1.connect(ii, ConstraintSet.TOP, Base2.getId(), ConstraintSet.BOTTOM);

                                constraintSet1.connect(ii, ConstraintSet.LEFT, layout.getId(), ConstraintSet.LEFT);
                                constraintSet1.connect(ii, ConstraintSet.RIGHT, fragmentView.findViewById(R.id.fragment_organizer_home_SubjectSelection_Guideline2).getId(), ConstraintSet.LEFT);
                                l1 = false;
                            } else {
                                constraintSet1.connect(ii, ConstraintSet.TOP, h - 2, ConstraintSet.BOTTOM);

                                constraintSet1.connect(ii, ConstraintSet.LEFT, layout.getId(), ConstraintSet.LEFT);
                                constraintSet1.connect(ii, ConstraintSet.RIGHT, fragmentView.findViewById(R.id.fragment_organizer_home_SubjectSelection_Guideline2).getId(), ConstraintSet.LEFT);
                            }

                            constraintSet1.applyTo(layout);
                            h = ii + 1;
                            final int gh = ii;

                            downHour1[ii].setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    ConstraintLayout layout = (ConstraintLayout) fragmentView.findViewById(R.id.fragment_organizer_home_SubjectSelection_DOWN1);
                                    for (int i = 0; i < downHour.length; i++) {
                                        layout.removeView(downHour[i]);
                                    }
                                    for (int i = 0; i < downHour1.length; i++) {
                                        layout.removeView(downHour1[i]);
                                    }

                                    if (Case == 0) {
                                        fragmentView.findViewById(R.id.fragment_organizer_home_NewHomeworkLayout).setVisibility(View.VISIBLE);
                                        fragmentView.findViewById(R.id.fragment_organizer_home_SubjectSelection_ScrollView).setVisibility(View.GONE);
                                    }
                                    returner = hourList.get(gh);
                                    HourChosen = true;
                                    hourinfo.setText(returner.getName());
                                    hourinfo.setBackgroundColor(android.graphics.Color.parseColor(returner.getColorCode()));
                                }
                            });
                        }

                    }

                }

                Button Back = (Button) fragmentView.findViewById(R.id.fragment_organizer_home_SubjectSelection_Back);
                Button Next = (Button) fragmentView.findViewById(R.id.fragment_organizer_home_SubjectSelection_Next);

                Back.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        ConstraintLayout layout = (ConstraintLayout) fragmentView.findViewById(R.id.fragment_organizer_home_SubjectSelection_DOWN1);
                        for (int i = 0; i < downHour.length; i++) {
                            layout.removeView(downHour[i]);
                        }
                        for (int i = 0; i < downHour1.length; i++) {
                            layout.removeView(downHour1[i]);
                        }

                        if (Case == 0) {
                            fragmentView.findViewById(R.id.fragment_organizer_home_NewHomeworkLayout).setVisibility(View.VISIBLE);
                            fragmentView.findViewById(R.id.fragment_organizer_home_SubjectSelection_ScrollView).setVisibility(View.GONE);
                        }
                        HourChosen = false;
                        returner = null;
                    }
                });

                Next.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        ConstraintLayout layout = (ConstraintLayout) fragmentView.findViewById(R.id.fragment_organizer_home_SubjectSelection_DOWN1);
                        for (int i = 0; i < downHour.length; i++) {
                            layout.removeView(downHour[i]);
                        }
                        for (int i = 0; i < downHour1.length; i++) {
                            layout.removeView(downHour1[i]);
                        }

                        if (Case == 0) {
                            fragmentView.findViewById(R.id.fragment_organizer_home_NewHomeworkLayout).setVisibility(View.VISIBLE);
                            fragmentView.findViewById(R.id.fragment_organizer_home_SubjectSelection_ScrollView).setVisibility(View.GONE);
                        }

                        EditText Custom = (EditText) fragmentView.findViewById(R.id.fragment_organizer_home_SubjectSelection_TextInput);
                        if (Custom.getText().toString().trim().equals("")) {
                            Custom.setError(activity.getResources().getString(R.string.EmptyField));
                        } else {
                            returner = new Hour(Custom.getText().toString(), "#D8D8D8");
                            HourChosen = true;
                            hourinfo.setText(returner.getName());
                            hourinfo.setBackgroundColor(android.graphics.Color.parseColor(returner.getColorCode()));
                        }

                    }
                });


                Base1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ConstraintLayout layout = (ConstraintLayout) fragmentView.findViewById(R.id.fragment_organizer_home_SubjectSelection_DOWN1);
                        for (int i = 0; i < downHour.length; i++) {
                            layout.removeView(downHour[i]);
                        }
                        for (int i = 0; i < downHour1.length; i++) {
                            layout.removeView(downHour1[i]);
                        }

                        if (Case == 0) {
                            fragmentView.findViewById(R.id.fragment_organizer_home_NewHomeworkLayout).setVisibility(View.VISIBLE);
                            fragmentView.findViewById(R.id.fragment_organizer_home_SubjectSelection_ScrollView).setVisibility(View.GONE);
                        }
                        returner = hourList.get(0);
                        HourChosen = true;
                        hourinfo.setText(returner.getName());
                        hourinfo.setBackgroundColor(android.graphics.Color.parseColor(returner.getColorCode()));
                    }
                });

                Base2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ConstraintLayout layout = (ConstraintLayout) fragmentView.findViewById(R.id.fragment_organizer_home_SubjectSelection_DOWN1);
                        for (int i = 0; i < downHour.length; i++) {
                            layout.removeView(downHour[i]);
                        }
                        for (int i = 0; i < downHour1.length; i++) {
                            layout.removeView(downHour1[i]);
                        }

                        if (Case == 0) {
                            fragmentView.findViewById(R.id.fragment_organizer_home_NewHomeworkLayout).setVisibility(View.VISIBLE);
                            fragmentView.findViewById(R.id.fragment_organizer_home_SubjectSelection_ScrollView).setVisibility(View.GONE);
                        }
                        returner = hourList.get(1);
                        HourChosen = true;
                        hourinfo.setText(returner.getName());
                        hourinfo.setBackgroundColor(android.graphics.Color.parseColor(returner.getColorCode()));
                    }
                });

            }
        });

        return returner;
    }



























}
