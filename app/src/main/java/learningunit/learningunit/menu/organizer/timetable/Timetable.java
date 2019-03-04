package learningunit.learningunit.menu.organizer.timetable;

import android.content.Intent;
import android.graphics.Color;
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


import com.google.gson.Gson;

import learningunit.learningunit.Objects.Timetable.Day;
import learningunit.learningunit.Objects.Timetable.Hour;
import learningunit.learningunit.Objects.Timetable.Week;
import learningunit.learningunit.beforeStart.FirstScreen;
import learningunit.learningunit.menu.MainActivity;
import learningunit.learningunit.Objects.Timetable.HourList;
import learningunit.learningunit.R;

public class Timetable extends AppCompatActivity {

    private Button create_next, create_back, day_back, day_cancel, dayBase, dayNext, day4back, hourNameBase, hourNameBase1, nameNext, nameBack, downHour[], downHour1[], down[];
    private ScrollView create_view;
    private ConstraintLayout day, daylayout, daylayout4, daylayout3, dayLayout6;
    private Spinner daySpinner, hourSpinner;
    private TextView nameHour, dayName;
    private EditText nameEditName, dayEditName;
    private ColorDrawable normalDrawable;
    Week week;

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
        HourList.addHour("Politik");
        HourList.addHour("Spanisch");
        HourList.addHour("Französisch");
        HourList.addHour("Latein");

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
        dayName = findViewById(R.id.timetable_dayName);

        nameHour = (TextView) findViewById(R.id.timetable_name_Hour);

        dayBase = (Button) findViewById(R.id.timetable_addDayBase);
        dayNext = (Button) findViewById(R.id.timetable_dayNext);
        day4back = (Button) findViewById(R.id.timetable_dayLayout4Back);

        hourNameBase = (Button) findViewById(R.id.timetable_hourNameBase);
        hourNameBase1 = (Button) findViewById(R.id.timetable_hourNameBase1);
        nameNext = (Button) findViewById(R.id.timetable_name_next);
        nameBack = (Button) findViewById(R.id.timetable_name_back);

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
        week = new Week(tage);
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
            dayEditName.setHint(getResources().getString(R.string.Monday));
            dayName.setText("1. " + getResources().getString(R.string.Day));
        }else if(currentDay == 2){
            dayEditName.setHint(getResources().getString(R.string.Tuesday));
            dayName.setText("2. " + getResources().getString(R.string.Day));
        }else if(currentDay == 3){
            dayEditName.setHint(getResources().getString(R.string.Wednesday));
            dayName.setText("3. " + getResources().getString(R.string.Day));
        }else if(currentDay == 4){
            dayEditName.setHint(getResources().getString(R.string.Thursday));
            dayName.setText("4. " + getResources().getString(R.string.Day));
        }else if(currentDay == 5){
            dayEditName.setHint(getResources().getString(R.string.Friday));
            dayName.setText("5. " + getResources().getString(R.string.Day));
        }else if(currentDay == 6){
            dayEditName.setHint(getResources().getString(R.string.Saturday));
            dayName.setText("6. " + getResources().getString(R.string.Day));
        }else if(currentDay == 7){
            dayEditName.setHint(getResources().getString(R.string.Sunday));
            dayName.setText("7. " + getResources().getString(R.string.Day));
        }
        dayBase.setBackgroundColor(android.graphics.Color.parseColor("#D8D8D8"));
        normalDrawable = (ColorDrawable) dayBase.getBackground();
        dayBase.setTextColor(android.graphics.Color.parseColor("#000000"));
        dayBase.setText("1." + getResources().getString(R.string.Hour));
        dayBase.setTextSize(14);
        dayBase.setTypeface(Typeface.DEFAULT_BOLD);
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
                        params.setMargins(0, 70, 0, 16);
                    }else{
                        params.setMargins(0, 26, 0, 16);
                    }

                    final String name = i + 1 +"." + getResources().getString(R.string.Hour);
                    down[i].setBackgroundColor(android.graphics.Color.parseColor("#D8D8D8"));
                    down[i].setText(name);
                    down[i].setId(i);
                    down[i].setTypeface(Typeface.DEFAULT_BOLD);


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
                            ConstraintSet constraintSet1;
                            int h = 0;
                            boolean l = true, l1 = true;

                            nameEditName.setText("");
                            nameNext.setEnabled(false);
                            daylayout3.setVisibility(View.VISIBLE);
                            day.setVisibility(View.GONE);
                            nameHour.setText(1 +"." + getResources().getString(R.string.Hour));

                            downHour = new Button[HourList.hourList.size()];
                            downHour1 = new Button[HourList.hourList.size()];

                            hourNameBase.setText(HourList.hourList.get(0).getName());
                            HourNamePressed(hourNameBase, dayBase);
                            hourNameBase1.setText(HourList.hourList.get(1).getName());
                            HourNamePressed(hourNameBase1, dayBase);


                            for(int ii = 2; ii < HourList.hourList.size(); ii++){

                                if (ii % 2 == 0) {
                                    if(downHour[ii] == null) {
                                        downHour[ii] = new Button(Timetable.this);
                                    }

                                    ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(
                                            ConstraintLayout.LayoutParams.MATCH_CONSTRAINT, ConstraintLayout.LayoutParams.WRAP_CONTENT);

                                    params.setMargins(24, 16, 24, 8);

                                    downHour[ii].setText(HourList.hourList.get(ii).getName());
                                    downHour[ii].setId(ii);

                                    dayLayout6.addView(downHour[ii], params);


                                    constraintSet1 = new ConstraintSet();
                                    constraintSet1.clone(dayLayout6);
                                    if (l == true) {
                                        constraintSet1.connect(ii, ConstraintSet.TOP, hourNameBase.getId(), ConstraintSet.BOTTOM);

                                        constraintSet1.connect(ii, ConstraintSet.RIGHT, dayLayout6.getId(), ConstraintSet.RIGHT);
                                        constraintSet1.connect(ii, ConstraintSet.LEFT, findViewById(R.id.guideline20).getId(), ConstraintSet.RIGHT);
                                        l = false;
                                    } else {
                                        constraintSet1.connect(ii, ConstraintSet.TOP, h - 2, ConstraintSet.BOTTOM);

                                        constraintSet1.connect(ii, ConstraintSet.RIGHT, dayLayout6.getId(), ConstraintSet.RIGHT);
                                        constraintSet1.connect(ii, ConstraintSet.LEFT, findViewById(R.id.guideline20).getId(), ConstraintSet.RIGHT);
                                    }

                                    h = ii + 1;
                                    constraintSet1.applyTo(dayLayout6);
                                    HourNamePressed(downHour[ii], dayBase);

                                } else {

                                    if(downHour1[ii] == null) {
                                        downHour1[ii] = new Button(Timetable.this);
                                    }
                                    ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(
                                            ConstraintLayout.LayoutParams.MATCH_CONSTRAINT, ConstraintLayout.LayoutParams.WRAP_CONTENT);

                                    params.setMargins(24, 16, 24, 8);

                                    downHour1[ii].setText(HourList.hourList.get(ii).getName());
                                    downHour1[ii].setId(ii);

                                    dayLayout6.addView(downHour1[ii], params);


                                    constraintSet1 = new ConstraintSet();
                                    constraintSet1.clone(dayLayout6);
                                    if (l1 == true) {
                                        constraintSet1.connect(ii, ConstraintSet.TOP, hourNameBase1.getId(), ConstraintSet.BOTTOM);

                                        constraintSet1.connect(ii, ConstraintSet.LEFT, dayLayout6.getId(), ConstraintSet.LEFT);
                                        constraintSet1.connect(ii, ConstraintSet.RIGHT, findViewById(R.id.guideline20).getId(), ConstraintSet.LEFT);
                                        l1 = false;
                                    } else {
                                        constraintSet1.connect(ii, ConstraintSet.TOP, h - 2, ConstraintSet.BOTTOM);

                                        constraintSet1.connect(ii, ConstraintSet.LEFT, dayLayout6.getId(), ConstraintSet.LEFT);
                                        constraintSet1.connect(ii, ConstraintSet.RIGHT, findViewById(R.id.guideline20).getId(), ConstraintSet.LEFT);
                                    }

                                    h = ii + 1;
                                    constraintSet1.applyTo(dayLayout6);
                                    HourNamePressed(downHour1[ii], dayBase);
                                }
                            }
                            nameNext.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    clearHourName();
                                    Log.d("nameNext", "3");
                                    findViewById(R.id.timetable_dayLayout3).setVisibility(View.VISIBLE);
                                    findViewById(R.id.timetable_hourColorLayout).setVisibility(View.VISIBLE);
                                    TextView HourColor = (TextView) findViewById(R.id.timetable_color_Hour);
                                    EditText newHourName = (EditText) findViewById(R.id.timetable_name_editName);
                                    HourColor.setText(newHourName.getText().toString().trim());
                                    TextView switchText = (TextView) findViewById(R.id.timetable_colorSwitchText);
                                    String switch0 = getResources().getString(R.string.ColourSwitchText0);
                                    String switch1 = getResources().getString(R.string.ColourSwitchText1);
                                    switchText.setText(switch0 + newHourName.getText().toString().trim() + switch1);
                                    String colourChooseText = getResources().getString(R.string.ColourCooseText);
                                    TextView colourChoose = (TextView) findViewById(R.id.timetable_color_Text);
                                    colourChoose.setText(colourChooseText + newHourName.getText().toString().trim().toString());
                                    findViewById(R.id.timetable_dayNameScrollview).setVisibility(View.GONE);
                                    findViewById(R.id.timetable_dayLayout5).setVisibility(View.GONE);
                                    HourList.addHour(nameEditName.getText().toString());
                                    ColorVoids(nameNext, dayBase, nameEditName.getText().toString());
                                    MainActivity.hideKeyboard(Timetable.this);
                                }
                            });


                            nameBack.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    clearHourName();
                                    daylayout3.setVisibility(View.GONE);
                                    day.setVisibility(View.VISIBLE);
                                    MainActivity.hideKeyboard(Timetable.this);

                                }
                            });
                        }
                    });
                    final int ege = i;
                    down[i].setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ConstraintSet constraintSet1;
                            int h = 0;
                            boolean l = true, l1 = true;

                            nameEditName.setText("");
                            nameNext.setEnabled(false);
                            daylayout3.setVisibility(View.VISIBLE);
                            day.setVisibility(View.GONE);
                            nameHour.setText(name);

                            hourNameBase.setText(HourList.hourList.get(0).getName());
                            HourNamePressed(hourNameBase, down[ege]);
                            hourNameBase1.setText(HourList.hourList.get(1).getName());
                            HourNamePressed(hourNameBase1, down[ege]);


                            downHour = new Button[HourList.hourList.size()];
                            downHour1 = new Button[HourList.hourList.size()];

                            for(int ii = 2; ii < HourList.hourList.size(); ii++){

                                if (ii % 2 == 0) {
                                    if(downHour[ii] == null) {
                                        downHour[ii] = new Button(Timetable.this);
                                    }
                                    ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(
                                            ConstraintLayout.LayoutParams.MATCH_CONSTRAINT, ConstraintLayout.LayoutParams.WRAP_CONTENT);

                                    params.setMargins(24, 16, 24, 8);

                                    downHour[ii].setText(HourList.hourList.get(ii).getName());
                                    downHour[ii].setId(ii);

                                    dayLayout6.addView(downHour[ii], params);


                                    constraintSet1 = new ConstraintSet();
                                    constraintSet1.clone(dayLayout6);
                                    if (l == true) {
                                        constraintSet1.connect(ii, ConstraintSet.TOP, hourNameBase.getId(), ConstraintSet.BOTTOM);

                                        constraintSet1.connect(ii, ConstraintSet.RIGHT, dayLayout6.getId(), ConstraintSet.RIGHT);
                                        constraintSet1.connect(ii, ConstraintSet.LEFT, findViewById(R.id.guideline20).getId(), ConstraintSet.RIGHT);
                                        l = false;
                                    } else {
                                        constraintSet1.connect(ii, ConstraintSet.TOP, h - 2, ConstraintSet.BOTTOM);

                                        constraintSet1.connect(ii, ConstraintSet.RIGHT, dayLayout6.getId(), ConstraintSet.RIGHT);
                                        constraintSet1.connect(ii, ConstraintSet.LEFT, findViewById(R.id.guideline20).getId(), ConstraintSet.RIGHT);
                                    }

                                    h = ii + 1;
                                    constraintSet1.applyTo(dayLayout6);
                                    HourNamePressed(downHour[ii], down[ege]);

                                } else {
                                    if(downHour1[ii] == null) {
                                        downHour1[ii] = new Button(Timetable.this);
                                    }
                                    ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(
                                            ConstraintLayout.LayoutParams.MATCH_CONSTRAINT, ConstraintLayout.LayoutParams.WRAP_CONTENT);

                                    params.setMargins(24, 16, 24, 8);

                                    downHour1[ii].setText(HourList.hourList.get(ii).getName());
                                    downHour1[ii].setId(ii);

                                    dayLayout6.addView(downHour1[ii], params);


                                    constraintSet1 = new ConstraintSet();
                                    constraintSet1.clone(dayLayout6);
                                    if (l1 == true) {
                                        constraintSet1.connect(ii, ConstraintSet.TOP, hourNameBase1.getId(), ConstraintSet.BOTTOM);

                                        constraintSet1.connect(ii, ConstraintSet.LEFT, dayLayout6.getId(), ConstraintSet.LEFT);
                                        constraintSet1.connect(ii, ConstraintSet.RIGHT, findViewById(R.id.guideline20).getId(), ConstraintSet.LEFT);
                                        l1 = false;
                                    } else {
                                        constraintSet1.connect(ii, ConstraintSet.TOP, h - 2, ConstraintSet.BOTTOM);

                                        constraintSet1.connect(ii, ConstraintSet.LEFT, dayLayout6.getId(), ConstraintSet.LEFT);
                                        constraintSet1.connect(ii, ConstraintSet.RIGHT, findViewById(R.id.guideline20).getId(), ConstraintSet.LEFT);
                                    }

                                    h = ii + 1;
                                    constraintSet1.applyTo(dayLayout6);
                                    HourNamePressed(downHour1[ii], down[ege]);
                                }
                            }

                            nameNext.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    clearHourName();
                                    Log.d("nameNext", "2");
                                    findViewById(R.id.timetable_dayLayout3).setVisibility(View.VISIBLE);
                                    findViewById(R.id.timetable_hourColorLayout).setVisibility(View.VISIBLE);
                                    TextView HourColor = (TextView) findViewById(R.id.timetable_color_Hour);
                                    EditText newHourName = (EditText) findViewById(R.id.timetable_name_editName);
                                    HourColor.setText(newHourName.getText().toString().trim());
                                    TextView switchText = (TextView) findViewById(R.id.timetable_colorSwitchText);
                                    String switch0 = getResources().getString(R.string.ColourSwitchText0);
                                    String switch1 = getResources().getString(R.string.ColourSwitchText1);
                                    switchText.setText(switch0 + newHourName.getText().toString().trim() + switch1);
                                    String colourChooseText = getResources().getString(R.string.ColourCooseText);
                                    TextView colourChoose = (TextView) findViewById(R.id.timetable_color_Text);
                                    colourChoose.setText(colourChooseText + newHourName.getText().toString().trim().toString());
                                    findViewById(R.id.timetable_dayNameScrollview).setVisibility(View.GONE);
                                    findViewById(R.id.timetable_dayLayout5).setVisibility(View.GONE);
                                    HourList.addHour(nameEditName.getText().toString());
                                    ColorVoids(nameNext, down[d], nameEditName.getText().toString());
                                    MainActivity.hideKeyboard(Timetable.this);
                                }
                            });


                            nameBack.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    clearHourName();
                                    daylayout3.setVisibility(View.GONE);
                                    day.setVisibility(View.VISIBLE);
                                    MainActivity.hideKeyboard(Timetable.this);

                                }
                            });
                        }
                    });
                }else{

                    dayBase.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ConstraintSet constraintSet1;
                            int h = 0;
                            boolean l = true, l1 = true;

                            nameEditName.setText("");
                            nameNext.setEnabled(false);
                            daylayout3.setVisibility(View.VISIBLE);
                            day.setVisibility(View.GONE);
                            nameHour.setText(1 +"." + getResources().getString(R.string.Hour));

                            downHour = new Button[HourList.hourList.size()];
                            downHour1 = new Button[HourList.hourList.size()];

                            hourNameBase.setText(HourList.hourList.get(0).getName());
                            HourNamePressed(hourNameBase, dayBase);
                            hourNameBase1.setText(HourList.hourList.get(1).getName());
                            HourNamePressed(hourNameBase1, dayBase);

                            for(int ii = 2; ii < HourList.hourList.size(); ii++){

                                if (ii % 2 == 0) {
                                    if(downHour[ii] == null) {
                                        downHour[ii] = new Button(Timetable.this);
                                    }

                                    ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(
                                            ConstraintLayout.LayoutParams.MATCH_CONSTRAINT, ConstraintLayout.LayoutParams.WRAP_CONTENT);

                                    params.setMargins(24, 16, 24, 8);

                                    downHour[ii].setText(HourList.hourList.get(ii).getName());
                                    downHour[ii].setId(ii);

                                    dayLayout6.addView(downHour[ii], params);


                                    constraintSet1 = new ConstraintSet();
                                    constraintSet1.clone(dayLayout6);
                                    if (l == true) {
                                        constraintSet1.connect(ii, ConstraintSet.TOP, hourNameBase.getId(), ConstraintSet.BOTTOM);

                                        constraintSet1.connect(ii, ConstraintSet.RIGHT, dayLayout6.getId(), ConstraintSet.RIGHT);
                                        constraintSet1.connect(ii, ConstraintSet.LEFT, findViewById(R.id.guideline20).getId(), ConstraintSet.RIGHT);
                                        l = false;
                                    } else {
                                        constraintSet1.connect(ii, ConstraintSet.TOP, h - 2, ConstraintSet.BOTTOM);

                                        constraintSet1.connect(ii, ConstraintSet.RIGHT, dayLayout6.getId(), ConstraintSet.RIGHT);
                                        constraintSet1.connect(ii, ConstraintSet.LEFT, findViewById(R.id.guideline20).getId(), ConstraintSet.RIGHT);
                                    }

                                    h = ii + 1;
                                    constraintSet1.applyTo(dayLayout6);
                                    HourNamePressed(downHour[ii], dayBase);

                                } else {

                                    if(downHour1[ii] == null) {
                                        downHour1[ii] = new Button(Timetable.this);
                                    }
                                    ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(
                                            ConstraintLayout.LayoutParams.MATCH_CONSTRAINT, ConstraintLayout.LayoutParams.WRAP_CONTENT);

                                    params.setMargins(24, 16, 24, 8);

                                    downHour1[ii].setText(HourList.hourList.get(ii).getName());
                                    downHour1[ii].setId(ii);

                                    dayLayout6.addView(downHour1[ii], params);


                                    constraintSet1 = new ConstraintSet();
                                    constraintSet1.clone(dayLayout6);
                                    if (l1 == true) {
                                        constraintSet1.connect(ii, ConstraintSet.TOP, hourNameBase1.getId(), ConstraintSet.BOTTOM);

                                        constraintSet1.connect(ii, ConstraintSet.LEFT, dayLayout6.getId(), ConstraintSet.LEFT);
                                        constraintSet1.connect(ii, ConstraintSet.RIGHT, findViewById(R.id.guideline20).getId(), ConstraintSet.LEFT);
                                        l1 = false;
                                    } else {
                                        constraintSet1.connect(ii, ConstraintSet.TOP, h - 2, ConstraintSet.BOTTOM);

                                        constraintSet1.connect(ii, ConstraintSet.LEFT, dayLayout6.getId(), ConstraintSet.LEFT);
                                        constraintSet1.connect(ii, ConstraintSet.RIGHT, findViewById(R.id.guideline20).getId(), ConstraintSet.LEFT);
                                    }

                                    h = ii + 1;
                                    constraintSet1.applyTo(dayLayout6);
                                    HourNamePressed(downHour1[ii], dayBase);
                                }
                            }

                            nameNext.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    clearHourName();
                                    Log.d("nameNext", "1");
                                    findViewById(R.id.timetable_dayLayout3).setVisibility(View.VISIBLE);
                                    findViewById(R.id.timetable_hourColorLayout).setVisibility(View.VISIBLE);
                                    TextView HourColor = (TextView) findViewById(R.id.timetable_color_Hour);
                                    EditText newHourName = (EditText) findViewById(R.id.timetable_name_editName);
                                    HourColor.setText(newHourName.getText().toString().trim());
                                    TextView switchText = (TextView) findViewById(R.id.timetable_colorSwitchText);
                                    String switch0 = getResources().getString(R.string.ColourSwitchText0);
                                    String switch1 = getResources().getString(R.string.ColourSwitchText1);
                                    switchText.setText(switch0 + newHourName.getText().toString().trim() + switch1);
                                    String colourChooseText = getResources().getString(R.string.ColourCooseText);
                                    TextView colourChoose = (TextView) findViewById(R.id.timetable_color_Text);
                                    colourChoose.setText(colourChooseText + newHourName.getText().toString().trim().toString());
                                    findViewById(R.id.timetable_dayNameScrollview).setVisibility(View.GONE);
                                    findViewById(R.id.timetable_dayLayout5).setVisibility(View.GONE);
                                    HourList.addHour(nameEditName.getText().toString());
                                    ColorVoids(nameNext, dayBase, nameEditName.getText().toString());
                                    MainActivity.hideKeyboard(Timetable.this);
                                }
                            });


                            nameBack.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    clearHourName();
                                    daylayout3.setVisibility(View.GONE);
                                    day.setVisibility(View.VISIBLE);
                                    MainActivity.hideKeyboard(Timetable.this);

                                }
                            });
                        }
                    });

                }

            }

            dayNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int size = down.length;
                    boolean cancel = false;
                    if(dayEditName.getText().toString().isEmpty()){
                        cancel = true;
                        Empty();
                    }

                    if(dayBase.getText().equals("1." + getResources().getString(R.string.Hour))){
                        Log.d("Name", "1." + getResources().getString(R.string.Hour));
                        cancel = true;
                        Empty();
                    }

                    for(int i = 1; i < size; i++){
                        String name = i + 1 +"." + getResources().getString(R.string.Hour);
                        Log.d("Name", name);
                        if (down[i].getText().equals(name)) {
                            cancel = true;
                            Empty();
                            break;
                        }
                    }
                    if(cancel == false){

                        Day d = new Day(dayEditName.getText().toString());
                        ColorDrawable dayBaseBackground = (ColorDrawable) dayBase.getBackground();
                        HourList.checkColor(d, dayBase, dayBaseBackground);

                        for(int i = 1; i < size; i++){
                            ColorDrawable ButtonBackground = (ColorDrawable) down[i].getBackground();
                            HourList.checkColor(d, down[i], ButtonBackground);
                            daylayout.removeView(down[i]);
                        }
                        week.addDay(d);
                        dayEditName.setText("");

                        if(currentDay == tage){


                            Gson gson = new Gson();
                            String json = gson.toJson(week);
                            FirstScreen.tinyDB.putString("Week", json);

                            Intent intent = new Intent(Timetable.this, TimetableShowcase.class);
                            startActivity(intent);
                        }else{
                            addDay(hours, currentDay + 1);
                        }
                    }

                }
            });

        }

    }

    public void Empty(){
        day.setVisibility(View.GONE);
        daylayout4.setVisibility(View.VISIBLE);
        MainActivity.hideKeyboard(this);
        day4back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                daylayout4.setVisibility(View.GONE);
                day.setVisibility(View.VISIBLE);
            }
        });
    }

    private void HourNamePressed(final Button Pressed, final Button Hour){
        MainActivity.hideKeyboard(Timetable.this);
        Pressed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for(Hour h : HourList.hourList) {
                    if (h.getName().equals(Pressed.getText().toString())) {
                        if (h.getKeep() == true) {
                            ColorDrawable c = new ColorDrawable();
                            c.setColor(android.graphics.Color.parseColor(h.getColorCode()));
                            setColor(Pressed, Hour, null, false, c, "", "");
                        } else {
                            findViewById(R.id.timetable_dayLayout3).setVisibility(View.VISIBLE);
                            findViewById(R.id.timetable_dayNameScrollview).setVisibility(View.GONE);
                            findViewById(R.id.timetable_dayLayout5).setVisibility(View.GONE);
                            findViewById(R.id.timetable_hourColorLayout).setVisibility(View.VISIBLE);
                            MainActivity.hideKeyboard(Timetable.this);
                            TextView colorSwitch = (TextView) findViewById(R.id.timetable_colorSwitchText);
                            TextView colourname = (TextView) findViewById(R.id.timetable_color_Hour);
                            TextView colourChoose = (TextView) findViewById(R.id.timetable_color_Text);
                            String switch0 = getResources().getString(R.string.ColourSwitchText0);
                            String switch1 = getResources().getString(R.string.ColourSwitchText1);
                            colorSwitch.setText(switch0 + Pressed.getText().toString() + switch1);
                            colourname.setText(Hour.getText().toString());
                            String colourChooseText = getResources().getString(R.string.ColourCooseText);
                            colourChoose.setText(colourChooseText + Pressed.getText().toString());

                            Button colorBack = (Button) findViewById(R.id.timetable_colorBack);
                            colorBack.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    findViewById(R.id.timetable_dayLayout5).setVisibility(View.VISIBLE);
                                    findViewById(R.id.timetable_dayNameScrollview).setVisibility(View.VISIBLE);
                                    findViewById(R.id.timetable_hourColorLayout).setVisibility(View.GONE);
                                }
                            });

                            Button colourSkip = (Button) findViewById(R.id.timetable_colorSkip);
                            colourSkip.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    clearHourName();
                                    findViewById(R.id.timetable_dayLayout5).setVisibility(View.VISIBLE);
                                    findViewById(R.id.timetable_dayNameScrollview).setVisibility(View.VISIBLE);
                                    findViewById(R.id.timetable_hourColorLayout).setVisibility(View.GONE);
                                    daylayout3.setVisibility(View.GONE);
                                    day.setVisibility(View.VISIBLE);
                                    Hour.setTextSize(18);
                                    Hour.setText(Pressed.getText().toString());
                                    Hour.setBackgroundColor(android.graphics.Color.parseColor("#D8D8D8"));

                                    MainActivity.hideKeyboard(Timetable.this);
                                }
                            });
                            ColorVoids(Pressed, Hour, "");
                        }
                    }
                }
            }
        });
    }

    public void setColor(final Button Pressed, final Button Hour, final Button Colour, boolean keep, ColorDrawable colour, String ColorCode, String Name) {
        MainActivity.hideKeyboard(this);
        ColorDrawable buttonColor;

        if (colour == null) {

            buttonColor = (ColorDrawable) Colour.getBackground();
            Hour.setBackgroundColor(buttonColor.getColor());

            if (keep == true) {
                for (learningunit.learningunit.Objects.Timetable.Hour h : HourList.hourList) {
                    if (h.getName().equalsIgnoreCase(Pressed.getText().toString())) {
                        h.setKeep(true);
                        h.setColorCode(ColorCode);
                        break;
                    }
                }
            }
        } else {
            buttonColor = colour;
            Hour.setBackgroundColor(buttonColor.getColor());
        }

        ColorDrawable colorBlack = (ColorDrawable) findViewById(R.id.timetable_hourColor6).getBackground();

        if (buttonColor.getColor() == colorBlack.getColor()) {
            Hour.setTextColor(android.graphics.Color.parseColor("#FFFFFF"));
        } else {
            Hour.setTextColor(android.graphics.Color.parseColor("#000000"));
        }

        Hour.setTypeface(Typeface.DEFAULT_BOLD);
        Hour.setTextSize(18);
        Switch switch1 = (Switch) findViewById(R.id.timetable_colorSwitch);
        switch1.setChecked(false);

        clearHourName();
        findViewById(R.id.timetable_dayLayout5).setVisibility(View.VISIBLE);
        findViewById(R.id.timetable_dayNameScrollview).setVisibility(View.VISIBLE);
        findViewById(R.id.timetable_hourColorLayout).setVisibility(View.GONE);
        daylayout3.setVisibility(View.GONE);
        day.setVisibility(View.VISIBLE);
        if (Name == "") {
            Hour.setText(Pressed.getText().toString());
        }else{
            Hour.setText(Name);
        }
        MainActivity.hideKeyboard(Timetable.this);
    }






    public void ColorVoids(final Button Pressed, final Button Hour, final String Name){
        MainActivity.hideKeyboard(Timetable.this);
        MainActivity.hideKeyboard(Timetable.this);
        MainActivity.hideKeyboard(Timetable.this);
        findViewById(R.id.timetable_hourColor1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Switch switch2 = (Switch) findViewById(R.id.timetable_colorSwitch);
                setColor(Pressed, Hour, (Button) findViewById(R.id.timetable_hourColor1), switch2.isChecked(), null, "#FF0000", Name);
            }
        });

        findViewById(R.id.timetable_hourColor2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Switch switch2 = (Switch) findViewById(R.id.timetable_colorSwitch);
                setColor(Pressed, Hour, (Button) findViewById(R.id.timetable_hourColor2), switch2.isChecked(), null, "#FF4500", Name);
            }
        });

        findViewById(R.id.timetable_hourColor3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Switch switch2 = (Switch) findViewById(R.id.timetable_colorSwitch);
                setColor(Pressed, Hour, (Button) findViewById(R.id.timetable_hourColor3), switch2.isChecked(), null, "#800000", Name);
            }
        });

        findViewById(R.id.timetable_hourColor4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Switch switch2 = (Switch) findViewById(R.id.timetable_colorSwitch);
                setColor(Pressed, Hour, (Button) findViewById(R.id.timetable_hourColor4), switch2.isChecked(), null, "#FA8072", Name);
            }
        });

        findViewById(R.id.timetable_hourColor5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Switch switch2 = (Switch) findViewById(R.id.timetable_colorSwitch);
                setColor(Pressed, Hour, (Button) findViewById(R.id.timetable_hourColor5), switch2.isChecked(), null, "#FFFF00", Name);
            }
        });

        findViewById(R.id.timetable_hourColor6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Switch switch2 = (Switch) findViewById(R.id.timetable_colorSwitch);
                setColor(Pressed, Hour, (Button) findViewById(R.id.timetable_hourColor6), switch2.isChecked(), null, "#000000", Name);
            }
        });







        findViewById(R.id.timetable_hourColor7).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Switch switch2 = (Switch) findViewById(R.id.timetable_colorSwitch);
                setColor(Pressed, Hour, (Button) findViewById(R.id.timetable_hourColor7), switch2.isChecked(), null, "#008000", Name);
            }
        });

        findViewById(R.id.timetable_hourColor8).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Switch switch2 = (Switch) findViewById(R.id.timetable_colorSwitch);
                setColor(Pressed, Hour, (Button) findViewById(R.id.timetable_hourColor8), switch2.isChecked(), null, "#006400", Name);
            }
        });

        findViewById(R.id.timetable_hourColor9).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Switch switch2 = (Switch) findViewById(R.id.timetable_colorSwitch);
                setColor(Pressed, Hour, (Button) findViewById(R.id.timetable_hourColor9), switch2.isChecked(), null, "#7CFC00", Name);
            }
        });

        findViewById(R.id.timetable_hourColor10).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Switch switch2 = (Switch) findViewById(R.id.timetable_colorSwitch);
                setColor(Pressed, Hour, (Button) findViewById(R.id.timetable_hourColor10), switch2.isChecked(), null, "#90EE90", Name);
            }
        });

        findViewById(R.id.timetable_hourColor11).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Switch switch2 = (Switch) findViewById(R.id.timetable_colorSwitch);
                setColor(Pressed, Hour, (Button) findViewById(R.id.timetable_hourColor11), switch2.isChecked(), null, "#6B8E23", Name);
            }
        });

        findViewById(R.id.timetable_hourColor12).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Switch switch2 = (Switch) findViewById(R.id.timetable_colorSwitch);
                setColor(Pressed, Hour, (Button) findViewById(R.id.timetable_hourColor12), switch2.isChecked(), null, "#A0522D", Name);
            }
        });






        findViewById(R.id.timetable_hourColor13).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Switch switch2 = (Switch) findViewById(R.id.timetable_colorSwitch);
                setColor(Pressed, Hour, (Button) findViewById(R.id.timetable_hourColor13), switch2.isChecked(), null, "#0000FF", Name);
            }
        });

        findViewById(R.id.timetable_hourColor14).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Switch switch2 = (Switch) findViewById(R.id.timetable_colorSwitch);
                setColor(Pressed, Hour, (Button) findViewById(R.id.timetable_hourColor14), switch2.isChecked(), null, "#00BFFF", Name);
            }
        });

        findViewById(R.id.timetable_hourColor15).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Switch switch2 = (Switch) findViewById(R.id.timetable_colorSwitch);
                setColor(Pressed, Hour, (Button) findViewById(R.id.timetable_hourColor15), switch2.isChecked(), null, "#483D8B", Name);
            }
        });

        findViewById(R.id.timetable_hourColor16).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Switch switch2 = (Switch) findViewById(R.id.timetable_colorSwitch);
                setColor(Pressed, Hour, (Button) findViewById(R.id.timetable_hourColor16), switch2.isChecked(), null, "#8A2BE2", Name);
            }
        });

        findViewById(R.id.timetable_hourColor17).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Switch switch2 = (Switch) findViewById(R.id.timetable_colorSwitch);
                setColor(Pressed, Hour, (Button) findViewById(R.id.timetable_hourColor17), switch2.isChecked(), null, "#B0E0E6", Name);
            }
        });

        findViewById(R.id.timetable_hourColor18).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Switch switch2 = (Switch) findViewById(R.id.timetable_colorSwitch);
                setColor(Pressed, Hour, (Button) findViewById(R.id.timetable_hourColor18), switch2.isChecked(), null, "#5F9EA0", Name);
            }
        });
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












}
