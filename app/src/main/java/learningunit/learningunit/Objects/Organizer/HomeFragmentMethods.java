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
import android.widget.ImageView;
import android.widget.ListView;
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
import learningunit.learningunit.Objects.Timetable.CustomAdapter;
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

    private void HomeworkClick(final View fragmentView, final Activity activity){
        MainActivity.hideKeyboard(activity);
        Button homework = (Button) fragmentView.findViewById(R.id.fragment_organizer_home_hausaufgaben);
        homework.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ConstraintLayout Selection = (ConstraintLayout) fragmentView.findViewById(R.id.fragment_organizer_home_HomeworkSelection);
                final ConstraintLayout MainLayout = (ConstraintLayout) fragmentView.findViewById(R.id.fragment_organizer_home_MainLayout);
                Selection.setVisibility(View.VISIBLE);
                MainLayout.setVisibility(View.GONE);

                final Button TopBack = (Button) activity.findViewById(R.id.organizer_back);
                TopBack.setText(activity.getResources().getString(R.string.Back));
                TopBack.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Selection.setVisibility(View.GONE);
                        MainLayout.setVisibility(View.VISIBLE);
                    }
                });

                Button OverviewHomework = (Button) fragmentView.findViewById(R.id.fragment_organizer_home_Homework_OverviewSelection);
                OverviewHomework.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final ConstraintLayout OverviewView = (ConstraintLayout) fragmentView.findViewById(R.id.fragment_organizer_home_HomeworkOverview_Layout);
                        OverviewView.setVisibility(View.VISIBLE);
                        Selection.setVisibility(View.GONE);

                        final TextView infoView = (TextView) fragmentView.findViewById(R.id.fragment_organizer_home_Homework_Overview_Info);
                        final Button infoButton = (Button) fragmentView.findViewById(R.id.fragment_organizer_home_Homework_Overview_CreateNow);
                        if(FirstScreen.tinyDB.getString("Homework").equals("")){
                            infoView.setVisibility(View.VISIBLE);
                            infoButton.setVisibility(View.VISIBLE);
                            infoButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    fragmentView.findViewById(R.id.fragment_organizer_home_NewEventLayout).setVisibility(View.VISIBLE);
                                    OverviewView.setVisibility(View.GONE);
                                    activity.findViewById(R.id.organizer_new).setVisibility(View.GONE);
                                }
                            });
                        }else{
                            infoView.setVisibility(View.GONE);
                            infoButton.setVisibility(View.GONE);

                            Gson gson = new Gson();
                            ArrayList<Homework> eventlist;
                            Type type = new TypeToken<ArrayList<Homework>>() {
                            }.getType();
                            String json = FirstScreen.tinyDB.getString("Homework");
                            eventlist = gson.fromJson(json, type);

                            ListView listView = (ListView) activity.findViewById(R.id.fragment_organizer_home_Homework_Overview_ListView);
                            HomeworkCustomAdapter customAdapter = new HomeworkCustomAdapter(activity, eventlist);
                            listView.setAdapter(customAdapter);
                        }


                        final Button TopNew = (Button) activity.findViewById(R.id.organizer_new);
                        TopBack.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Selection.setVisibility(View.VISIBLE);
                                OverviewView.setVisibility(View.GONE);
                                TopNew.setVisibility(View.GONE);
                                TopBack.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Selection.setVisibility(View.GONE);
                                        MainLayout.setVisibility(View.VISIBLE);
                                        TopNew.setVisibility(View.GONE);
                                    }
                                });
                            }
                        });

                        TopNew.setVisibility(View.VISIBLE);
                        TopNew.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                fragmentView.findViewById(R.id.fragment_organizer_home_NewEventLayout).setVisibility(View.VISIBLE);
                                OverviewView.setVisibility(View.GONE);
                                TopNew.setVisibility(View.GONE);
                            }
                        });
                    }
                });

                Button CreateHomework = (Button) fragmentView.findViewById(R.id.fragment_organizer_home_Homework_CreateSelection);
                CreateHomework.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final ScrollView newEvent = (ScrollView) fragmentView.findViewById(R.id.fragment_organizer_home_NewEventLayout);
                        Selection.setVisibility(View.GONE);
                        newEvent.setVisibility(View.VISIBLE);
                        CalendarView cv = (CalendarView) fragmentView.findViewById(R.id.fragment_organizer_home_NewEvent_Calendar);
                        cv.setDate(new Date().getTime(), false, true);
                        final TextView error = (TextView) fragmentView.findViewById(R.id.fragment_organizer_home_NewEvent_ErrorDate);
                        error.setVisibility(View.GONE);
                        fyear = 0;

                        final ConstraintLayout homeLayout = (ConstraintLayout) fragmentView.findViewById(R.id.fragment_organizer_home_MainLayout);
                        final ScrollView newHomework = (ScrollView) fragmentView.findViewById(R.id.fragment_organizer_home_NewHomeworkLayout);
                        final ScrollView HourSelection = (ScrollView) fragmentView.findViewById(R.id.fragment_organizer_home_SubjectSelection_ScrollView);
                        final ScrollView FinishHomework = (ScrollView) fragmentView.findViewById(R.id.fragment_organizer_home_HomeworkFinish_ScrollView);

                        TopBack.setText(activity.getResources().getString(R.string.Cancel));
                        TopBack.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Selection.setVisibility(View.GONE);
                                homeLayout.setVisibility(View.GONE);
                                newHomework.setVisibility(View.GONE);
                                HourSelection.setVisibility(View.GONE);
                                FinishHomework.setVisibility(View.GONE);
                                newEvent.setVisibility(View.GONE);
                                MainLayout.setVisibility(View.VISIBLE);
                                TopBack.setText(activity.getResources().getString(R.string.Back));
                                TopBack.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Selection.setVisibility(View.VISIBLE);
                                        newEvent.setVisibility(View.GONE);
                                    }
                                });
                            }
                        });

                        Button newEventBack = (Button) fragmentView.findViewById(R.id.organizer_NewEventBack);
                        newEventBack.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Selection.setVisibility(View.VISIBLE);
                                newEvent.setVisibility(View.GONE);
                                TopBack.setText(activity.getResources().getString(R.string.Back));
                                TopBack.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Selection.setVisibility(View.GONE);
                                        MainLayout.setVisibility(View.VISIBLE);
                                    }
                                });
                            }
                        });
                    }
                });
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

        final TextView error = (TextView) fragmentView.findViewById(R.id.fragment_organizer_home_NewEvent_ErrorDate);
        Button newEventNext = (Button) fragmentView.findViewById(R.id.organizer_NewEventNext);
        newEventNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.hideKeyboard(activity);
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

    private void ContinueHomework(final View fragmentView, Homework event, final Activity activity){

        HourChosen = false;
        Selected = null;
        final TextView hourinfo = (TextView) fragmentView.findViewById(R.id.fragment_organizer_home_NewHomework_HourInfo);
        final ImageView hourimage = (ImageView) fragmentView.findViewById(R.id.fragment_organizer_home_NewHomework_HourSelection_ImageView);
        hourimage.setVisibility(View.GONE);
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

            Back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MainActivity.hideKeyboard(activity);
                    fragmentView.findViewById(R.id.fragment_organizer_home_NewEventLayout).setVisibility(View.VISIBLE);
                    fragmentView.findViewById(R.id.fragment_organizer_home_NewHomeworkLayout).setVisibility(View.GONE);
                    Log.d("HA", "Back");
                }
            });


            hourinfo.setError(null);
            titel.setError(null);
            description.setError(null);

            final Button Next = (Button) fragmentView.findViewById(R.id.fragment_organizer_home_NewHomework_Next);
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
                    }else{
                        Log.d("Da", "Da");
                    }
                }
            });

            HourSelection(hourList, 0, fragmentView, activity, HourSelection, event);
        }
    }




    private void HourSelection(final ArrayList<Hour> hourList, final int Case, final View fragmentView, final Activity activity, Button HourSelection, final Homework event){ //Case: 0 = Homework
        final TextView hourinfo = (TextView) fragmentView.findViewById(R.id.fragment_organizer_home_NewHomework_HourInfo);
        final ImageView hourimage = (ImageView) fragmentView.findViewById(R.id.fragment_organizer_home_NewHomework_HourSelection_ImageView);
        final EditText Custom = (EditText) fragmentView.findViewById(R.id.fragment_organizer_home_SubjectSelection_TextInput);
        MainActivity.hideKeyboard(activity);
        Custom.setText("");
        HourSelection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.hideKeyboard(activity);
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
                                    Selected = hourList.get(gh);
                                    HourChosen = true;
                                    hourinfo.setText(Selected.getName());
                                    hourimage.setVisibility(View.VISIBLE);
                                    ColorDrawable ic = new ColorDrawable();
                                    ic.setColor(android.graphics.Color.parseColor(Selected.getColorCode()));
                                    hourimage.setBackground(ic);
                                    if(Case == 0){
                                        if(Selected != null) {
                                            FinishHomework(fragmentView, activity, event);
                                            Log.d("KA", "NotNull");
                                        }else{
                                            Log.d("KA", "Null");
                                        }
                                    }
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
                                    Selected = hourList.get(gh);
                                    HourChosen = true;
                                    hourinfo.setText(Selected.getName());
                                    hourimage.setVisibility(View.VISIBLE);
                                    ColorDrawable ic = new ColorDrawable();
                                    ic.setColor(android.graphics.Color.parseColor(Selected.getColorCode()));
                                    hourimage.setBackground(ic);
                                    if(Case == 0){
                                        if(Selected != null) {
                                            FinishHomework(fragmentView, activity, event);
                                            Log.d("KA", "NotNull");
                                        }else{
                                            Log.d("KA", "Null");
                                        }
                                    }
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
                        hourinfo.setText(activity.getResources().getString(R.string.NoHourNameChosen));
                        hourimage.setVisibility(View.GONE);
                        Selected = null;
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

                        if (Custom.getText().toString().trim().equals("")) {
                            Custom.setError(activity.getResources().getString(R.string.EmptyField));
                        } else {
                            Selected = new Hour(Custom.getText().toString(), "#D8D8D8");
                            HourChosen = true;
                            hourinfo.setText(Selected.getName());
                            hourimage.setVisibility(View.VISIBLE);
                            ColorDrawable ic = new ColorDrawable();
                            ic.setColor(android.graphics.Color.parseColor(Selected.getColorCode()));
                            hourimage.setBackground(ic);
                            if(Case == 0){
                                if(Selected != null) {
                                    FinishHomework(fragmentView, activity, event);
                                    Log.d("KA", "NotNull");
                                }else{
                                    Log.d("KA", "Null");
                                }
                            }
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
                        Selected = hourList.get(0);
                        HourChosen = true;
                        hourinfo.setText(Selected.getName());
                        hourimage.setVisibility(View.VISIBLE);
                        ColorDrawable ic = new ColorDrawable();
                        ic.setColor(android.graphics.Color.parseColor(Selected.getColorCode()));
                        hourimage.setBackground(ic);
                        if(Case == 0){
                            if(Selected != null) {
                                FinishHomework(fragmentView, activity, event);
                                Log.d("KA", "NotNull");
                            }else{
                                Log.d("KA", "Null");
                            }
                        }
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
                        Selected = hourList.get(1);
                        HourChosen = true;
                        hourinfo.setText(Selected.getName());
                        hourimage.setVisibility(View.VISIBLE);
                        ColorDrawable ic = new ColorDrawable();
                        ic.setColor(android.graphics.Color.parseColor(Selected.getColorCode()));
                        hourimage.setBackground(ic);
                        if(Case == 0){
                            if(Selected != null) {
                                FinishHomework(fragmentView, activity, event);
                                Log.d("KA", "NotNull");
                            }else{
                                Log.d("KA", "Null");
                            }
                        }
                    }
                });

            }
        });
    }


    private void FinishHomework(final View fragmentView, final Activity activity, final Homework event){

        final Button Next = (Button) fragmentView.findViewById(R.id.fragment_organizer_home_NewHomework_Next);
        final EditText titel = (EditText) fragmentView.findViewById(R.id.fragment_organizer_home_NewHomework_TitelInput);
        final EditText description = (EditText) fragmentView.findViewById(R.id.fragment_organizer_home_NewHomework_DescriptionInput);
        final TextView hourinfo = (TextView) fragmentView.findViewById(R.id.fragment_organizer_home_NewHomework_HourInfo);

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
                }else{
                    event.setHour(Selected);
                    event.setTitle(titel.getText().toString().trim());
                    event.setDescription(description.getText().toString().trim());
                    fragmentView.findViewById(R.id.fragment_organizer_home_NewHomeworkLayout).setVisibility(View.GONE);
                    fragmentView.findViewById(R.id.fragment_organizer_home_HomeworkFinish_ScrollView).setVisibility(View.VISIBLE);

                    TextView Date = (TextView) fragmentView.findViewById(R.id.fragment_organizer_home_HomeworkFinish_DateText);
                    TextView Hour = (TextView) fragmentView.findViewById(R.id.fragment_organizer_home_HomeworkFinish_HourText);
                    TextView Titel = (TextView) fragmentView.findViewById(R.id.fragment_organizer_home_HomeworkFinish_Titel);
                    TextView Description = (TextView) fragmentView.findViewById(R.id.fragment_organizer_home_HomeworkFinish_Text);
                    ImageView colour = (ImageView) fragmentView.findViewById(R.id.fragment_organizer_home_HomeworkFinish_HourPic);

                    Date.setText(event.getDay() + "." + event.getMonth() + "." + event.getYear());
                    Hour.setText(Selected.getName());
                    colour.setBackgroundColor(android.graphics.Color.parseColor(Selected.getColorCode()));
                    Titel.setText(event.getTitle());
                    Description.setText(event.getDescription());

                    Button back = (Button) fragmentView.findViewById(R.id.fragment_organizer_home_HomeworkFinish_Back);
                    Button finish = (Button) fragmentView.findViewById(R.id.fragment_organizer_home_HomeworkFinish_Finish);

                    back.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            fragmentView.findViewById(R.id.fragment_organizer_home_NewHomeworkLayout).setVisibility(View.VISIBLE);
                            fragmentView.findViewById(R.id.fragment_organizer_home_HomeworkFinish_ScrollView).setVisibility(View.GONE);
                        }
                    });

                    finish.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            fragmentView.findViewById(R.id.fragment_organizer_home_HomeworkFinish_ScrollView).setVisibility(View.GONE);
                            fragmentView.findViewById(R.id.fragment_organizer_home_HomeworkOverview_Layout).setVisibility(View.VISIBLE);

                            Selected.addEvent(event);

                            Gson gson = new Gson();
                            ArrayList<Homework> eventlist;
                            String json1 = FirstScreen.tinyDB.getString("Homework");
                            if(json1.equals("")){
                                eventlist = new ArrayList<Homework>();
                            }else {
                                Type type = new TypeToken<ArrayList<Homework>>() {
                                }.getType();
                                eventlist = gson.fromJson(json1, type);
                            }

                            eventlist.add(event);
                            FirstScreen.tinyDB.putString("Homework", gson.toJson(eventlist));

                            if(FirstScreen.tinyDB.getString("WeekA").equals("")) {
                                Log.d("homework:", "Continue");
                            }else {
                                final ArrayList<Hour> hourList = new ArrayList<Hour>();
                                String json = FirstScreen.tinyDB.getString("WeekA");
                                Type type = new TypeToken<Week>() {
                                }.getType();

                                Week weekA = gson.fromJson(json, type);
                                Week NWeekA = new Week(weekA.getDayList().size());
                                weekA.getDayList().get(0).getHourList();
                                for (Day d : weekA.getDayList()) {
                                    Day day = new Day(d.getName());
                                    for (Hour h : d.getHourList()) {
                                        if (h.getName().equals(Selected.getName()) && h.getColorCode().equals(h.getColorCode())) {
                                            day.addHour(Selected);
                                        } else {
                                            day.addHour(h);
                                        }
                                    }
                                    NWeekA.addDay(day);
                                }

                                FirstScreen.tinyDB.putString("WeekA", gson.toJson(NWeekA));

                                if (!(FirstScreen.tinyDB.getString("WeekB").equals(""))) {
                                    json = FirstScreen.tinyDB.getString("WeekB");
                                    Week weekB = gson.fromJson(json, type);
                                    Week NWeekB = new Week(weekB.getDayList().size());
                                    weekB.getDayList().get(0).getHourList();
                                    for (Day d : weekB.getDayList()) {
                                        Day day = new Day(d.getName());
                                        for (Hour h : d.getHourList()) {
                                            if (h.getName().equals(Selected.getName()) && h.getColorCode().equals(h.getColorCode())) {
                                                day.addHour(Selected);
                                            } else {
                                                day.addHour(h);
                                            }
                                        }
                                        NWeekB.addDay(day);
                                    }

                                    FirstScreen.tinyDB.putString("WeekB", gson.toJson(NWeekB));

                                }
                            }
                            HomeworkOverview(fragmentView, activity);
                        }
                    });
                }
            }
        });

    }



    public void HomeworkOverview(final View fragmentView, final Activity activity){

        ArrayList<Homework> eventlist;

        Gson gson = new Gson();
        String json = FirstScreen.tinyDB.getString("Homework");
        Type type = new TypeToken<ArrayList<Homework>>() {
        }.getType();

        eventlist = gson.fromJson(json, type);




        final ConstraintLayout OverviewView = (ConstraintLayout) fragmentView.findViewById(R.id.fragment_organizer_home_HomeworkOverview_Layout);
        final ConstraintLayout Selection = (ConstraintLayout) fragmentView.findViewById(R.id.fragment_organizer_home_HomeworkSelection);
        OverviewView.setVisibility(View.VISIBLE);
        Selection.setVisibility(View.GONE);

        final TextView infoView = (TextView) fragmentView.findViewById(R.id.fragment_organizer_home_Homework_Overview_Info);
        final Button infoButton = (Button) fragmentView.findViewById(R.id.fragment_organizer_home_Homework_Overview_CreateNow);
        if(FirstScreen.tinyDB.getString("Homework").equals("")){
            infoView.setVisibility(View.VISIBLE);
            infoButton.setVisibility(View.VISIBLE);
            infoButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fragmentView.findViewById(R.id.fragment_organizer_home_NewEventLayout).setVisibility(View.VISIBLE);
                    OverviewView.setVisibility(View.GONE);
                    activity.findViewById(R.id.organizer_new).setVisibility(View.GONE);
                }
            });
        }else{
            infoView.setVisibility(View.GONE);
            infoButton.setVisibility(View.GONE);

            ListView listView = (ListView) activity.findViewById(R.id.fragment_organizer_home_Homework_Overview_ListView);
            HomeworkCustomAdapter customAdapter = new HomeworkCustomAdapter(activity, eventlist);
            listView.setAdapter(customAdapter);
        }


        final Button TopNew = (Button) activity.findViewById(R.id.organizer_new);
        final Button TopBack = (Button) activity.findViewById(R.id.organizer_back);
        final ConstraintLayout MainLayout = (ConstraintLayout) fragmentView.findViewById(R.id.fragment_organizer_home_MainLayout);
        TopBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Selection.setVisibility(View.VISIBLE);
                OverviewView.setVisibility(View.GONE);
                TopNew.setVisibility(View.GONE);
                TopBack.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Selection.setVisibility(View.GONE);
                        MainLayout.setVisibility(View.VISIBLE);
                        TopNew.setVisibility(View.GONE);
                    }
                });
            }
        });

        TopNew.setVisibility(View.VISIBLE);
        TopNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentView.findViewById(R.id.fragment_organizer_home_NewEventLayout).setVisibility(View.VISIBLE);
                OverviewView.setVisibility(View.GONE);
                TopNew.setVisibility(View.GONE);
            }
        });


    }






















}
