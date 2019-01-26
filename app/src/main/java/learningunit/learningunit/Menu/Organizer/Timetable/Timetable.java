package learningunit.learningunit.Menu.Organizer.Timetable;

import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.RippleDrawable;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import learningunit.learningunit.Menu.MainActivity;
import learningunit.learningunit.Objects.Timetable.HourList;
import learningunit.learningunit.R;

public class Timetable extends AppCompatActivity {

    private Button create_next, create_back, day_back, day_cancel, dayBase, dayNext, day4back, hourNameBase, hourNameBase1, nameNext, nameBack, downHour[], downHour1[], down[];
    private ScrollView create_view;
    private ConstraintLayout day, daylayout, daylayout4, daylayout3, dayLayout6;
    private Spinner daySpinner, hourSpinner;
    private TextView nameHour;
    private EditText nameEditName, dayEditName;
    private RippleDrawable ButtonColor;

    private int tage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        HourList.addHour("Mathe");
        HourList.addHour("Freistunde");
        HourList.addHour("Deutsch");
        HourList.addHour("Englisch");
        HourList.addHour("Physik");
        HourList.addHour("Biologie");
        HourList.addHour("Chemie");
        HourList.addHour("Sport");
        HourList.addHour("Informatik");
        HourList.addHour("Religion");
        HourList.addHour("Kunst");
        HourList.addHour("Musik");

        MainActivity.hideKeyboard(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timetable);

        day = (ConstraintLayout) findViewById(R.id.timetable_dayLayout0);
        daylayout = (ConstraintLayout) findViewById(R.id.timetable_dayLayout);
        daylayout4 = (ConstraintLayout) findViewById(R.id.timetable_dayLayout4);
        daylayout3 = (ConstraintLayout) findViewById(R.id.timetable_dayLayout3);
        dayLayout6 = (ConstraintLayout) findViewById(R.id.timetable_dayLayout6);

        daySpinner = (Spinner) findViewById(R.id.timetable_createSpinner);
        String[] daySpinnerItems = new String[]{"1", "2", "3", "4", "5", "6", "7"};
        ArrayAdapter<String> daySpinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, daySpinnerItems);
        daySpinner.setAdapter(daySpinnerAdapter);

        hourSpinner = (Spinner) findViewById(R.id.timetable_createSpinner1);
        String[] hourSpinnerItems = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24"};
        ArrayAdapter<String> hourSpinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, hourSpinnerItems);
        hourSpinner.setAdapter(hourSpinnerAdapter);

        create_view = (ScrollView) findViewById(R.id.timetable_createScrollview);

        nameHour = (TextView) findViewById(R.id.timetable_name_Hour);

        dayBase = (Button) findViewById(R.id.timetable_addDayBase);
        dayNext = (Button) findViewById(R.id.timetable_dayNext);
        day4back = (Button) findViewById(R.id.timetable_dayLayout4Back);

        hourNameBase = (Button) findViewById(R.id.timetable_hourNameBase);
        hourNameBase1 = (Button) findViewById(R.id.timetable_hourNameBase1);
        nameNext = (Button) findViewById(R.id.timetable_name_next);
        nameBack = (Button) findViewById(R.id.timetable_name_back);
        ButtonColor = (RippleDrawable) nameBack.getBackground();

        create_next = (Button) findViewById(R.id.timetable_createNext);
        create_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                opencreate_next();
            }
        });

        create_back = (Button) findViewById(R.id.timetable_createBack);
        create_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                opencreate_back();
            }
        });

        day_back = (Button) findViewById(R.id.timetable_dayBack);
        day_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openday_back();
            }
        });

        day_cancel = (Button) findViewById(R.id.timetable_dayCancel);
        day_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                opencreate_cancel();
            }
        });

        dayEditName = (EditText) findViewById(R.id.timetable_dayEditName);

        nameEditName = (EditText) findViewById(R.id.timetable_name_editName);
        nameEditName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String inhalt = nameEditName.getText().toString().trim();
                nameNext.setEnabled(!inhalt.isEmpty());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }





    private void opencreate_back(){
        HourList.clearList();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void opencreate_next(){
        day.setVisibility(View.VISIBLE);
        create_view.setVisibility(View.GONE);
        MainActivity.hideKeyboard(this);
        tage = Integer.parseInt(daySpinner.getSelectedItem().toString());

        addDay(Integer.parseInt(hourSpinner.getSelectedItem().toString()), 1);
    }

    private void openday_back(){
        day.setVisibility(View.GONE);
        create_view.setVisibility(View.VISIBLE);
        MainActivity.hideKeyboard(this);

        clearHourName();

        for(int i = 0; i < down.length; i++){
            daylayout.removeView(down[i]);
        }
    }

    private void opencreate_cancel(){
        HourList.clearList();
        MainActivity.hideKeyboard(this);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void addDay(final int hours, final int currentDay){

        clearHourName();

        ConstraintSet constraintSet;
        int e = 0;
        boolean f = true;

        if(currentDay == 1){
            dayEditName.setHint("Montag");
        }else if(currentDay == 1){
            dayEditName.setHint("Dienstag");
        }else if(currentDay == 1){
            dayEditName.setHint("Mittwoch");
        }else if(currentDay == 1){
            dayEditName.setHint("Donnerstag");
        }else if(currentDay == 1){
            dayEditName.setHint("Freitag");
        }else if(currentDay == 1){
            dayEditName.setHint("Samstag");
        }else if(currentDay == 1){
            dayEditName.setHint("Sonntag");
        }

        if(hours > 0){

            down = new Button[hours];

            for (int i = 0; i < hours; i++){

                if(i != 0){

                    if(down[i] == null) {
                        down[i] = new Button(Timetable.this);
                    }

                    ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(
                            ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.MATCH_PARENT);

                    if(i % 2 == 0) {
                        params.setMargins(8, 70, 8, 8);
                    }else{
                        params.setMargins(8, 16, 8, 8);
                    }

                    final String name = i + 1 +". Stunde";
                    down[i].setText(name);
                    down[i].setId(i);


                    Log.d("i = " + i, "added button with name " + i + 1 + ". Stunde" + " to daylayout");

                    daylayout.addView(down[i], params);


                    constraintSet = new ConstraintSet();
                    constraintSet.clone(daylayout);
                    if (f == true) {
                        constraintSet.connect(i, ConstraintSet.TOP, dayBase.getId(), ConstraintSet.BOTTOM);

                        constraintSet.connect(i, ConstraintSet.RIGHT, dayBase.getId(), ConstraintSet.RIGHT);
                        constraintSet.connect(i, ConstraintSet.LEFT, dayBase.getId(), ConstraintSet.LEFT);
                        f = false;
                    } else {
                        constraintSet.connect(i, ConstraintSet.TOP, e - 1, ConstraintSet.BOTTOM);

                        constraintSet.connect(i, ConstraintSet.RIGHT, e - 1, ConstraintSet.RIGHT);
                        constraintSet.connect(i, ConstraintSet.LEFT, e - 1, ConstraintSet.LEFT);
                    }

                    e = i + 1;
                    constraintSet.applyTo(daylayout);
                    final int d = i;

                    dayBase.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            clearHourName();
                            ConstraintSet constraintSet1;
                            int h = 0;
                            boolean l = true, l1 = true;

                            nameEditName.setText("");
                            nameNext.setEnabled(false);
                            daylayout3.setVisibility(View.VISIBLE);
                            day.setVisibility(View.GONE);
                            nameHour.setText(name);

                            downHour = new Button[HourList.hourList.size()];
                            downHour1 = new Button[HourList.hourList.size()];

                            hourNameBase.setText(HourList.hourList.get(0).getName());
                            hourNameBase1.setText(HourList.hourList.get(1).getName());

                            for(int ii = 2; ii < HourList.hourList.size(); ii++){

                                if (ii % 2 == 0) {
                                    if(downHour[ii] == null) {
                                        downHour[ii] = new Button(Timetable.this);
                                    }

                                    ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(
                                            ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);

                                    params.setMargins(8, 16, 8, 8);

                                    downHour[ii].setText(HourList.hourList.get(ii).getName());
                                    downHour[ii].setId(ii);

                                    dayLayout6.addView(downHour[ii], params);


                                    constraintSet1 = new ConstraintSet();
                                    constraintSet1.clone(dayLayout6);
                                    if (l == true) {
                                        constraintSet1.connect(ii, ConstraintSet.TOP, hourNameBase.getId(), ConstraintSet.BOTTOM);

                                        constraintSet1.connect(ii, ConstraintSet.RIGHT, hourNameBase.getId(), ConstraintSet.RIGHT);
                                        constraintSet1.connect(ii, ConstraintSet.LEFT, hourNameBase.getId(), ConstraintSet.LEFT);
                                        l = false;
                                    } else {
                                        constraintSet1.connect(ii, ConstraintSet.TOP, h - 2, ConstraintSet.BOTTOM);

                                        constraintSet1.connect(ii, ConstraintSet.RIGHT, h - 2, ConstraintSet.RIGHT);
                                        constraintSet1.connect(ii, ConstraintSet.LEFT, h - 2, ConstraintSet.LEFT);
                                    }

                                    h = ii + 1;
                                    constraintSet1.applyTo(dayLayout6);
                                    HourNamePressed(downHour[ii], dayBase, 0);

                                } else {

                                    if(downHour1[ii] == null) {
                                        downHour1[ii] = new Button(Timetable.this);
                                    }
                                    ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(
                                            ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);

                                    params.setMargins(8, 16, 8, 8);

                                    downHour1[ii].setText(HourList.hourList.get(ii).getName());
                                    downHour1[ii].setId(ii);

                                    dayLayout6.addView(downHour1[ii], params);


                                    constraintSet1 = new ConstraintSet();
                                    constraintSet1.clone(dayLayout6);
                                    if (l1 == true) {
                                        constraintSet1.connect(ii, ConstraintSet.TOP, hourNameBase1.getId(), ConstraintSet.BOTTOM);

                                        constraintSet1.connect(ii, ConstraintSet.RIGHT, hourNameBase1.getId(), ConstraintSet.RIGHT);
                                        constraintSet1.connect(ii, ConstraintSet.LEFT, hourNameBase1.getId(), ConstraintSet.LEFT);
                                        l1 = false;
                                    } else {
                                        constraintSet1.connect(ii, ConstraintSet.TOP, h - 2, ConstraintSet.BOTTOM);

                                        constraintSet1.connect(ii, ConstraintSet.RIGHT, h - 2, ConstraintSet.RIGHT);
                                        constraintSet1.connect(ii, ConstraintSet.LEFT, h - 2, ConstraintSet.LEFT);
                                    }

                                    h = ii + 1;
                                    constraintSet1.applyTo(dayLayout6);
                                    HourNamePressed(downHour1[ii], dayBase, 0);
                                }
                            }

                            nameNext.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    daylayout3.setVisibility(View.GONE);
                                    day.setVisibility(View.VISIBLE);
                                    clearHourName();
                                    HourList.addHour(nameEditName.getText().toString());
                                    dayBase.setText(nameEditName.getText().toString());
                                    MainActivity.hideKeyboard(Timetable.this);
                                }
                            });


                            nameBack.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    daylayout3.setVisibility(View.GONE);
                                    day.setVisibility(View.VISIBLE);
                                    MainActivity.hideKeyboard(Timetable.this);

                                }
                            });
                            HourNamePressed(hourNameBase, dayBase, 0);
                            HourNamePressed(hourNameBase1, dayBase, 0);
                        }
                    });

                    down[i].setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            clearHourName();
                            ConstraintSet constraintSet1;
                            int h = 0;
                            boolean l = true, l1 = true;

                            nameEditName.setText("");
                            nameNext.setEnabled(false);
                            daylayout3.setVisibility(View.VISIBLE);
                            day.setVisibility(View.GONE);
                            nameHour.setText(name);

                            hourNameBase.setText(HourList.hourList.get(0).getName());
                            hourNameBase1.setText(HourList.hourList.get(1).getName());

                            downHour = new Button[HourList.hourList.size()];
                            downHour1 = new Button[HourList.hourList.size()];

                            for(int ii = 2; ii < HourList.hourList.size(); ii++){

                                if (ii % 2 == 0) {
                                    if(downHour[ii] == null) {
                                        downHour[ii] = new Button(Timetable.this);
                                    }
                                    ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(
                                            ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);

                                    params.setMargins(8, 16, 8, 8);

                                    downHour[ii].setText(HourList.hourList.get(ii).getName());
                                    downHour[ii].setId(ii);

                                    dayLayout6.addView(downHour[ii], params);


                                    constraintSet1 = new ConstraintSet();
                                    constraintSet1.clone(dayLayout6);
                                    if (l == true) {
                                        constraintSet1.connect(ii, ConstraintSet.TOP, hourNameBase.getId(), ConstraintSet.BOTTOM);

                                        constraintSet1.connect(ii, ConstraintSet.RIGHT, hourNameBase.getId(), ConstraintSet.RIGHT);
                                        constraintSet1.connect(ii, ConstraintSet.LEFT, hourNameBase.getId(), ConstraintSet.LEFT);
                                        l = false;
                                    } else {
                                        constraintSet1.connect(ii, ConstraintSet.TOP, h - 2, ConstraintSet.BOTTOM);

                                        constraintSet1.connect(ii, ConstraintSet.RIGHT, h - 2, ConstraintSet.RIGHT);
                                        constraintSet1.connect(ii, ConstraintSet.LEFT, h - 2, ConstraintSet.LEFT);
                                    }

                                    h = ii + 1;
                                    constraintSet1.applyTo(dayLayout6);
                                    HourNamePressed(downHour[ii], down[d], d);

                                } else {
                                    if(downHour1[ii] == null) {
                                        downHour1[ii] = new Button(Timetable.this);
                                    }
                                    ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(
                                            ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);

                                    params.setMargins(8, 16, 8, 8);

                                    downHour1[ii].setText(HourList.hourList.get(ii).getName());
                                    downHour1[ii].setId(ii);

                                    dayLayout6.addView(downHour1[ii], params);


                                    constraintSet1 = new ConstraintSet();
                                    constraintSet1.clone(dayLayout6);
                                    if (l1 == true) {
                                        constraintSet1.connect(ii, ConstraintSet.TOP, hourNameBase1.getId(), ConstraintSet.BOTTOM);

                                        constraintSet1.connect(ii, ConstraintSet.RIGHT, hourNameBase1.getId(), ConstraintSet.RIGHT);
                                        constraintSet1.connect(ii, ConstraintSet.LEFT, hourNameBase1.getId(), ConstraintSet.LEFT);
                                        l1 = false;
                                    } else {
                                        constraintSet1.connect(ii, ConstraintSet.TOP, h - 2, ConstraintSet.BOTTOM);

                                        constraintSet1.connect(ii, ConstraintSet.RIGHT, h - 2, ConstraintSet.RIGHT);
                                        constraintSet1.connect(ii, ConstraintSet.LEFT, h - 2, ConstraintSet.LEFT);
                                    }

                                    h = ii + 1;
                                    constraintSet1.applyTo(dayLayout6);
                                    HourNamePressed(downHour1[ii], down[d], d);

                                }
                            }

                            nameNext.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    daylayout3.setVisibility(View.GONE);
                                    day.setVisibility(View.VISIBLE);
                                    clearHourName();
                                    HourList.addHour(nameEditName.getText().toString());
                                    down[d].setText(nameEditName.getText().toString());
                                    MainActivity.hideKeyboard(Timetable.this);
                                }
                            });


                            nameBack.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    daylayout3.setVisibility(View.GONE);
                                    day.setVisibility(View.VISIBLE);
                                    MainActivity.hideKeyboard(Timetable.this);

                                }
                            });
                            HourNamePressed(hourNameBase, down[d], d);
                            HourNamePressed(hourNameBase1, down[d], d);
                        }
                    });
                }else{

                    dayBase.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            clearHourName();
                            ConstraintSet constraintSet1;
                            int h = 0;
                            boolean l = true, l1 = true;

                            nameEditName.setText("");
                            nameNext.setEnabled(false);
                            daylayout3.setVisibility(View.VISIBLE);
                            day.setVisibility(View.GONE);
                            nameHour.setText("1. Stunde");

                            downHour = new Button[HourList.hourList.size()];
                            downHour1 = new Button[HourList.hourList.size()];

                            hourNameBase.setText(HourList.hourList.get(0).getName());
                            hourNameBase1.setText(HourList.hourList.get(1).getName());

                            for(int ii = 2; ii < HourList.hourList.size(); ii++){

                                if (ii % 2 == 0) {
                                    if(downHour[ii] == null) {
                                        downHour[ii] = new Button(Timetable.this);
                                    }

                                    ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(
                                            ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);

                                    params.setMargins(8, 16, 8, 8);

                                    downHour[ii].setText(HourList.hourList.get(ii).getName());
                                    downHour[ii].setId(ii);

                                    dayLayout6.addView(downHour[ii], params);


                                    constraintSet1 = new ConstraintSet();
                                    constraintSet1.clone(dayLayout6);
                                    if (l == true) {
                                        constraintSet1.connect(ii, ConstraintSet.TOP, hourNameBase.getId(), ConstraintSet.BOTTOM);

                                        constraintSet1.connect(ii, ConstraintSet.RIGHT, hourNameBase.getId(), ConstraintSet.RIGHT);
                                        constraintSet1.connect(ii, ConstraintSet.LEFT, hourNameBase.getId(), ConstraintSet.LEFT);
                                        l = false;
                                    } else {
                                        constraintSet1.connect(ii, ConstraintSet.TOP, h - 2, ConstraintSet.BOTTOM);

                                        constraintSet1.connect(ii, ConstraintSet.RIGHT, h - 2, ConstraintSet.RIGHT);
                                        constraintSet1.connect(ii, ConstraintSet.LEFT, h - 2, ConstraintSet.LEFT);
                                    }

                                    h = ii + 1;
                                    constraintSet1.applyTo(dayLayout6);
                                    HourNamePressed(downHour[ii], dayBase, 0);

                                } else {

                                    if(downHour1[ii] == null) {
                                        downHour1[ii] = new Button(Timetable.this);
                                    }
                                    ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(
                                            ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);

                                    params.setMargins(8, 16, 8, 8);

                                    downHour1[ii].setText(HourList.hourList.get(ii).getName());
                                    downHour1[ii].setId(ii);

                                    dayLayout6.addView(downHour1[ii], params);


                                    constraintSet1 = new ConstraintSet();
                                    constraintSet1.clone(dayLayout6);
                                    if (l1 == true) {
                                        constraintSet1.connect(ii, ConstraintSet.TOP, hourNameBase1.getId(), ConstraintSet.BOTTOM);

                                        constraintSet1.connect(ii, ConstraintSet.RIGHT, hourNameBase1.getId(), ConstraintSet.RIGHT);
                                        constraintSet1.connect(ii, ConstraintSet.LEFT, hourNameBase1.getId(), ConstraintSet.LEFT);
                                        l1 = false;
                                    } else {
                                        constraintSet1.connect(ii, ConstraintSet.TOP, h - 2, ConstraintSet.BOTTOM);

                                        constraintSet1.connect(ii, ConstraintSet.RIGHT, h - 2, ConstraintSet.RIGHT);
                                        constraintSet1.connect(ii, ConstraintSet.LEFT, h - 2, ConstraintSet.LEFT);
                                    }

                                    h = ii + 1;
                                    constraintSet1.applyTo(dayLayout6);
                                    HourNamePressed(downHour1[ii], dayBase, 0);
                                }
                            }

                            nameNext.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    daylayout3.setVisibility(View.GONE);
                                    day.setVisibility(View.VISIBLE);
                                    clearHourName();
                                    HourList.addHour(nameEditName.getText().toString());
                                    dayBase.setText(nameEditName.getText().toString());
                                    MainActivity.hideKeyboard(Timetable.this);
                                }
                            });


                            nameBack.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    daylayout3.setVisibility(View.GONE);
                                    day.setVisibility(View.VISIBLE);
                                    MainActivity.hideKeyboard(Timetable.this);

                                }
                            });
                            HourNamePressed(hourNameBase, dayBase, 0);
                            HourNamePressed(hourNameBase1, dayBase, 0);
                        }
                    });

                }

            }

            dayNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clearHourName();
                    if(currentDay != tage){
                        //addDay(hours, currentDay + 1);
                    }
                    day.setVisibility(View.GONE);
                    daylayout4.setVisibility(View.VISIBLE);
                    day4back.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            daylayout4.setVisibility(View.GONE);
                            day.setVisibility(View.VISIBLE);
                        }
                    });

                }
            });

        }

    }










    private void HourNamePressed(final Button Pressed, final Button Hour, final int SHour){

        Pressed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                clearHourName();
                findViewById(R.id.timetable_dayLayout3).setVisibility(View.VISIBLE);
                findViewById(R.id.timetable_dayNameScrollview).setVisibility(View.GONE);
                findViewById(R.id.timetable_dayLayout5).setVisibility(View.GONE);
                findViewById(R.id.timetable_hourColorLayout).setVisibility(View.VISIBLE);
                MainActivity.hideKeyboard(Timetable.this);

                Button c = (Button) findViewById(R.id.timetable_hourColor6);
                ColorDrawable buttonColor = (ColorDrawable) c.getBackground();

                    Log.d("HourNamePressed I = ", SHour + "");

                    if (HourList.hourList.get(SHour).getName() == Pressed.getText().toString()) {
                        Log.d("HourNamePressed best = ", SHour + "");
                        if (HourList.hourList.get(SHour).getColorCode() != null) {
                            Log.d("hourlist", "not null");

                            Hour.setBackground((ColorDrawable) HourList.hourList.get(SHour).getColorCode());

                            if(Hour.getBackground() == c.getBackground()){
                                Log.d("Weise Schrift", "");
                                Hour.setTextColor(android.graphics.Color.parseColor("#FFFFFF"));
                            }else{
                                Log.d("Schwarze Schrift", "");
                                Hour.setTextColor(android.graphics.Color.parseColor("#000000"));
                            }

                            clearHourName();
                            findViewById(R.id.timetable_dayLayout3).setVisibility(View.GONE);
                            findViewById(R.id.timetable_dayNameScrollview).setVisibility(View.VISIBLE);
                            findViewById(R.id.timetable_dayLayout5).setVisibility(View.VISIBLE);
                            findViewById(R.id.timetable_hourColorLayout).setVisibility(View.GONE);
                            day.setVisibility(View.VISIBLE);
                            Hour.setText(Pressed.getText().toString());
                            MainActivity.hideKeyboard(Timetable.this);
                            Hour.setTypeface(Typeface.DEFAULT_BOLD);
                            Hour.setTextSize(18);
                            Switch switch1 = (Switch) findViewById(R.id.timetable_colorSwitch);
                            switch1.setChecked(false);

                            HourNamePressed(Pressed, Hour, SHour);
                            ColorVoids(Pressed, Hour, SHour);


                        }else{

                            clearHourName();
                            findViewById(R.id.timetable_dayLayout3).setVisibility(View.VISIBLE);
                            findViewById(R.id.timetable_dayNameScrollview).setVisibility(View.GONE);
                            findViewById(R.id.timetable_dayLayout5).setVisibility(View.GONE);
                            findViewById(R.id.timetable_hourColorLayout).setVisibility(View.VISIBLE);
                            MainActivity.hideKeyboard(Timetable.this);

                            ColorVoids(Pressed, Hour, SHour);

                        }

                    }


            }
        });
    }














    public void setColor(Button Pressed, Button Hour, Button Color, int SHour){

        Switch switch1 = (Switch) findViewById(R.id.timetable_colorSwitch);
        ColorDrawable buttonColor = (ColorDrawable) Color.getBackground();

        findViewById(R.id.timetable_hourColorLayout).setVisibility(View.VISIBLE);
        findViewById(R.id.timetable_dayLayout5).setVisibility(View.GONE);
        findViewById(R.id.timetable_dayNameScrollview).setVisibility(View.GONE);

        if(switch1.isChecked() == true){
            Log.d("Switch", "Checked");

            if(HourList.hourList.get(SHour).getName() == Pressed.getText().toString()){
                Log.d("setColor I bestanden = ", SHour + "");

                HourList.hourList.get(SHour).setColorCode(buttonColor);
                Log.d("Color sddd Code", "" + HourList.hourList.get(SHour).getColorCode());
            }

        }else{
            Log.d("Switch", "Not Checked");
        }

        ColorDrawable colorBlack =  (ColorDrawable) findViewById(R.id.timetable_hourColor6).getBackground();

        if(buttonColor == colorBlack){
            Hour.setTextColor(android.graphics.Color.parseColor("#FFFFFF"));
        }else{
            Hour.setTextColor(android.graphics.Color.parseColor("#000000"));
        }

        clearHourName();
        Hour.setBackgroundColor(buttonColor.getColor());
        findViewById(R.id.timetable_dayLayout3).setVisibility(View.GONE);
        findViewById(R.id.timetable_dayNameScrollview).setVisibility(View.VISIBLE);
        findViewById(R.id.timetable_dayLayout5).setVisibility(View.VISIBLE);
        findViewById(R.id.timetable_hourColorLayout).setVisibility(View.GONE);
        day.setVisibility(View.VISIBLE);
        Hour.setText(Pressed.getText().toString());
        MainActivity.hideKeyboard(Timetable.this);
        Hour.setTypeface(Typeface.DEFAULT_BOLD);
        Hour.setTextSize(18);
        switch1.setChecked(false);

        HourNamePressed(Pressed, Hour, SHour);
    }





    private void clearHourName(){
        if(downHour != null && downHour1 != null){
            for(int i = 0; i < downHour.length; i++){
                dayLayout6.removeView(downHour[i]);
                if(downHour[i] != null){
                    downHour[i].setVisibility(View.GONE);
                }
            }

            for(int i = 0; i < downHour1.length; i++){
                dayLayout6.removeView(downHour1[i]);
                if(downHour1[i] != null) {
                    downHour1[i].setVisibility(View.GONE);
                }
            }
        }
    }







    public void ColorVoids(final Button Pressed, final Button Hour, final int SHour){
        findViewById(R.id.timetable_hourColor1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setColor(Pressed, Hour, (Button) findViewById(R.id.timetable_hourColor1), SHour);
            }
        });

        findViewById(R.id.timetable_hourColor2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setColor(Pressed, Hour, (Button) findViewById(R.id.timetable_hourColor2), SHour);
            }
        });

        findViewById(R.id.timetable_hourColor3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setColor(Pressed, Hour, (Button) findViewById(R.id.timetable_hourColor3), SHour);
            }
        });

        findViewById(R.id.timetable_hourColor4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setColor(Pressed, Hour, (Button) findViewById(R.id.timetable_hourColor4), SHour);
            }
        });

        findViewById(R.id.timetable_hourColor5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setColor(Pressed, Hour, (Button) findViewById(R.id.timetable_hourColor5), SHour);
            }
        });

        findViewById(R.id.timetable_hourColor6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setColor(Pressed, Hour, (Button) findViewById(R.id.timetable_hourColor6), SHour);
            }
        });







        findViewById(R.id.timetable_hourColor7).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setColor(Pressed, Hour, (Button) findViewById(R.id.timetable_hourColor7), SHour);
            }
        });

        findViewById(R.id.timetable_hourColor8).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setColor(Pressed, Hour, (Button) findViewById(R.id.timetable_hourColor8), SHour);
            }
        });

        findViewById(R.id.timetable_hourColor9).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setColor(Pressed, Hour, (Button) findViewById(R.id.timetable_hourColor9), SHour);
            }
        });

        findViewById(R.id.timetable_hourColor10).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setColor(Pressed, Hour, (Button) findViewById(R.id.timetable_hourColor10), SHour);
            }
        });

        findViewById(R.id.timetable_hourColor11).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setColor(Pressed, Hour, (Button) findViewById(R.id.timetable_hourColor11), SHour);
            }
        });

        findViewById(R.id.timetable_hourColor12).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setColor(Pressed, Hour, (Button) findViewById(R.id.timetable_hourColor12), SHour);
            }
        });






        findViewById(R.id.timetable_hourColor13).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setColor(Pressed, Hour, (Button) findViewById(R.id.timetable_hourColor13), SHour);
            }
        });

        findViewById(R.id.timetable_hourColor14).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setColor(Pressed, Hour, (Button) findViewById(R.id.timetable_hourColor14), SHour);
            }
        });

        findViewById(R.id.timetable_hourColor15).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setColor(Pressed, Hour, (Button) findViewById(R.id.timetable_hourColor15), SHour);
            }
        });

        findViewById(R.id.timetable_hourColor16).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setColor(Pressed, Hour, (Button) findViewById(R.id.timetable_hourColor16), SHour);
            }
        });

        findViewById(R.id.timetable_hourColor17).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setColor(Pressed, Hour, (Button) findViewById(R.id.timetable_hourColor17), SHour);
            }
        });

        findViewById(R.id.timetable_hourColor18).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setColor(Pressed, Hour, (Button) findViewById(R.id.timetable_hourColor18), SHour);
            }
        });
    }











}
