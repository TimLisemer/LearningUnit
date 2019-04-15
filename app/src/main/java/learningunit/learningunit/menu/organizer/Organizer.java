package learningunit.learningunit.menu.organizer;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;
import com.google.android.gms.analytics.Tracker;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.joda.time.DateTime;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import learningunit.learningunit.Objects.API.OnBackPressedListener;
import learningunit.learningunit.Objects.Organizer.EventMethods;
import learningunit.learningunit.Objects.Organizer.Exam;
import learningunit.learningunit.Objects.Organizer.HomeCustomAdapter;
import learningunit.learningunit.Objects.Organizer.Homework;
import learningunit.learningunit.Objects.Organizer.Presentation;
import learningunit.learningunit.Objects.PublicAPIs.AnalyticsApplication;
import learningunit.learningunit.Objects.Organizer.DashboardFragmentMethods;
import learningunit.learningunit.Objects.Organizer.HomeFragmentMethods;
import learningunit.learningunit.R;
import learningunit.learningunit.beforeStart.FirstScreen;
import learningunit.learningunit.menu.MainActivity;

public class Organizer extends AppCompatActivity{

    private TextView mTextMessage;
    private Organizer.SectionsPagerAdapter mSectionsPagerAdapter;
    public static ViewPager mViewPager;
    private Tracker mTracker;
    private MenuItem prevMenuItem;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mViewPager.setCurrentItem(0);
                    return true;
                case R.id.navigation_dashboard:
                    mViewPager.setCurrentItem(1);
                    return true;
                case R.id.navigation_notifications:
                    mViewPager.setCurrentItem(2);
                    return true;
            }
            return false;
        }
    };

    protected static OnBackPressedListener onBackPressedListener;

    public static void setOnBackPressedListener(OnBackPressedListener onBackPressedListeners) {
        onBackPressedListener = onBackPressedListeners;
    }

    @Override
    public void onBackPressed() {
        if (onBackPressedListener != null) {
            Log.d("sasaasa", "NotNull 1");
            onBackPressedListener.doBack();
        } else {
            Log.d("sasaasa", "Null 1");
            super.onBackPressed();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        this.onBackPressedListener = null;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AnalyticsApplication application = (AnalyticsApplication) getApplication();
        mTracker = application.getDefaultTracker();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organizer);

        Button TopBack = (Button) findViewById(R.id.organizer_back);
        TopBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Organizer.this, MainActivity.class);
                startActivity(intent);
            }
        });
        setOnBackPressedListener(new OnBackPressedListener() {
            @Override
            public void doBack() {
                Intent intent = new Intent(Organizer.this, MainActivity.class);
                startActivity(intent);
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new Organizer.SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.containerOrganizer);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        mTextMessage = (TextView) findViewById(R.id.message);
        final BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                MainActivity.hideKeyboard(Organizer.this);
                if (prevMenuItem != null)
                    prevMenuItem.setChecked(false);
                else
                    navigation.getMenu().getItem(0).setChecked(false);

                    navigation.getMenu().getItem(position).setChecked(true);
                    prevMenuItem = navigation.getMenu().getItem(position);

                    final Button TopNew = (Button) findViewById(R.id.organizer_new);
                    if(position == 0){
                        TopNew.setVisibility(View.GONE);
                    }else{
                        if(position == 1){
                            new DashboardFragmentMethods(Organizer.this, (ListView) findViewById(R.id.organizer_dashboard__ListView), (TextView) findViewById(R.id.organizer_dashboard_info), (Button) findViewById(R.id.organizer_dashboard_create));
                        }
                        TopNew.setVisibility(View.VISIBLE);
                        TopNew.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mViewPager.setCurrentItem(0);
                                TopNew.setVisibility(View.GONE);
                            }
                        });
                    }
                    
                    Button TopBack = (Button) findViewById(R.id.organizer_back);
                    TopBack.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(Organizer.this, MainActivity.class);
                            startActivity(intent);
                        }
                    });

                setOnBackPressedListener(new OnBackPressedListener() {
                    @Override
                    public void doBack() {
                        Intent intent = new Intent(Organizer.this, MainActivity.class);
                        startActivity(intent);
                    }
                });

                try {
                    ConstraintLayout homeLayout = (ConstraintLayout) findViewById(R.id.fragment_organizer_home_MainLayout);
                    homeLayout.setVisibility(View.VISIBLE);
                }catch (Exception e){}

                try{
                    findViewById(R.id.fragment_organizer_home_GradeSelection).setVisibility(View.GONE);
                    findViewById(R.id.fragment_organizer_home_GradeSubSelection).setVisibility(View.GONE);
                    findViewById(R.id.fragment_organizer_home_EnterNewCertificateScrollView).setVisibility(View.GONE);
                    EditText edit1 = (EditText) findViewById(R.id.fragment_organizer_home_EnterNewCertificate_edit1);
                    EditText edit2 = (EditText) findViewById(R.id.fragment_organizer_home_EnterNewCertificate_edit2);
                    edit1.setText("");
                    edit2.setText("");
                }catch (Exception e){}

                try{
                    ScrollView newEvent = (ScrollView) findViewById(R.id.fragment_organizer_home_NewEventLayout);
                    ScrollView newHomework = (ScrollView) findViewById(R.id.fragment_organizer_home_NewHomeworkLayout);
                    ScrollView HourSelection = (ScrollView) findViewById(R.id.fragment_organizer_home_SubjectSelection_ScrollView);
                    ScrollView FinishHomework = (ScrollView) findViewById(R.id.fragment_organizer_home_HomeworkFinish_ScrollView);
                    ConstraintLayout HomeworkOverview = (ConstraintLayout) findViewById(R.id.fragment_organizer_home_HomeworkOverview_Layout);
                    ConstraintLayout HomeworkSelection = (ConstraintLayout) findViewById(R.id.fragment_organizer_home_HomeworkSelection);

                    try{

                        for(int i = 0; i < HomeFragmentMethods.downHour.length; i++){
                            HourSelection.removeView(HomeFragmentMethods.downHour[i]);
                        }
                        for(int i = 0; i < HomeFragmentMethods.downHour1.length; i++){
                            HourSelection.removeView(HomeFragmentMethods.downHour1[i]);
                        }
                        HomeFragmentMethods.HourChosen = false;
                        TextView hourinfo = (TextView) findViewById(R.id.fragment_organizer_home_NewHomework_HourInfo);
                        hourinfo.setText(getResources().getString(R.string.NoHourNameChosen));
                    }catch (Exception e){ }

                    Log.d("Jetzt", "Jetzt");
                    HomeworkOverview.setVisibility(View.GONE);
                    HomeworkSelection.setVisibility(View.GONE);
                    FinishHomework.setVisibility(View.GONE);
                    HourSelection.setVisibility(View.GONE);
                    newEvent.setVisibility(View.GONE);
                    newHomework.setVisibility(View.GONE);
                    CalendarView cv = (CalendarView) findViewById(R.id.fragment_organizer_home_NewEvent_Calendar);
                    cv.setDate(new Date().getTime(), false, true);
                }catch (Exception e){}
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        ///////////////////////////////////////////////////////////////////////
    }


    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static Organizer.PlaceholderFragment newInstance(int sectionNumber) {
            Organizer.PlaceholderFragment fragment = new Organizer.PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        Date date;

        @Override
        public View onCreateView(final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {

            if(getArguments().getInt(ARG_SECTION_NUMBER) == 1) {
                final View fragmentView = inflater.inflate(R.layout.fragment_organizer_home, container, false);
                new HomeFragmentMethods(fragmentView, getActivity());

                return fragmentView;
            }else if(getArguments().getInt(ARG_SECTION_NUMBER) == 2){
                final View fragmentView = inflater.inflate(R.layout.fragment_organizer_dashboard, container, false);
                new DashboardFragmentMethods(getActivity(), (ListView) fragmentView.findViewById(R.id.organizer_dashboard__ListView), (TextView) fragmentView.findViewById(R.id.organizer_dashboard_info), (Button) fragmentView.findViewById(R.id.organizer_dashboard_create));
                return fragmentView;
            }else if(getArguments().getInt(ARG_SECTION_NUMBER) == 3){
                final View fragmentView = inflater.inflate(R.layout.fragment_organizer_calendar, container, false);
                final CompactCalendarView compact = (CompactCalendarView) fragmentView.findViewById(R.id.organizer_calender_calendar);
                final TextView MonthInfo = (TextView) fragmentView.findViewById(R.id.organizer_calender_dateinfo);
                final SimpleDateFormat dateFormatMonth = new SimpleDateFormat("MM - yyyy", Locale.getDefault());
                final SimpleDateFormat spf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                Calendar ca = Calendar.getInstance();
                date = ca.getTime();
                compact.setCurrentSelectedDayBackgroundColor(android.graphics.Color.parseColor("#E57373"));
                MonthInfo.setText(dateFormatMonth.format(date));
                compact.setListener(new CompactCalendarView.CompactCalendarViewListener() {
                    @Override
                    public void onDayClick(Date dateClicked) {
                        List<Event> events = compact.getEvents(dateClicked);
                        if(events.size() == 0){
                            compact.setCurrentSelectedDayBackgroundColor(android.graphics.Color.parseColor("#E57373"));
                        }else if(events.size() == 1){
                            Toast.makeText(getActivity(), events.get(0).getData().toString(), Toast.LENGTH_SHORT).show();
                            compact.setCurrentSelectedDayBackgroundColor(events.get(0).getColor());
                        }else{
                            Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.MultipleEvents), Toast.LENGTH_SHORT).show();
                            compact.setCurrentSelectedDayBackgroundColor(android.graphics.Color.parseColor("#E57373"));
                            compact.setCurrentSelectedDayBackgroundColor(events.get(0).getColor());
                        }
                    }

                    @Override
                    public void onMonthScroll(Date firstDayOfNewMonth) {
                        compact.setCurrentSelectedDayBackgroundColor(android.graphics.Color.parseColor("#E57373"));
                        MonthInfo.setText(dateFormatMonth.format(firstDayOfNewMonth));
                        date = firstDayOfNewMonth;
                    }
                });
                ImageView previous = (ImageView) fragmentView.findViewById(R.id.organizer_calender_previous);
                previous.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DateTime dt = new DateTime(date);
                        compact.setCurrentSelectedDayBackgroundColor(android.graphics.Color.parseColor("#E57373"));
                        try {
                            date = dateFormatMonth.parse(dt.getMonthOfYear() - 1 + " - " + dt.getYear());
                            compact.setCurrentDate(date);
                            MonthInfo.setText(dateFormatMonth.format(date));
                        }catch (Exception e){}
                    }
                });

                ImageView next = (ImageView) fragmentView.findViewById(R.id.organizer_calender_next);
                next.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DateTime dt = new DateTime(date);
                        compact.setCurrentSelectedDayBackgroundColor(android.graphics.Color.parseColor("#E57373"));
                        try {
                            date = dateFormatMonth.parse(dt.getMonthOfYear() + 1 + " - " + dt.getYear());
                            compact.setCurrentDate(date);
                            MonthInfo.setText(dateFormatMonth.format(date));
                        }catch (Exception e){}
                    }
                });

                Gson gson = new Gson();
                ArrayList<Homework> eventlist;
                String json = FirstScreen.tinyDB.getString("Homework");
                if(json.equals("")){
                    eventlist = new ArrayList<Homework>();
                }else {
                    Type type = new TypeToken<ArrayList<Homework>>() {
                    }.getType();
                    eventlist = gson.fromJson(json, type);

                    for(Homework ha : eventlist){
                        try {
                            Date d = spf.parse(ha.getDay() + "/" + ha.getMonth() + "/" + ha.getYear());
                            Event event = new Event(android.graphics.Color.parseColor(ha.getHour().getColorCode()), d.getTime(),getResources().getString(R.string.Homework) + ": " +  ha.getTitle());
                            compact.addEvent(event);
                        }catch (Exception e){}
                    }
                }

                ArrayList<Exam> examlist;
                String json1 = FirstScreen.tinyDB.getString("Exam");
                if(json1.equals("")){
                    examlist = new ArrayList<Exam>();
                }else {
                    Type type = new TypeToken<ArrayList<Exam>>() {
                    }.getType();
                    examlist = gson.fromJson(json1, type);

                    for(Exam ex : examlist){
                        try {
                            Date d = spf.parse(ex.getDay() + "/" + ex.getMonth() + "/" + ex.getYear());
                            Event event = new Event(android.graphics.Color.parseColor(ex.getHour().getColorCode()), d.getTime(),getResources().getString(R.string.Exam) + ": " +  ex.getTitle());
                            compact.addEvent(event);
                        }catch (Exception e){}
                    }
                }

                ArrayList<Presentation> prelist;
                String json2 = FirstScreen.tinyDB.getString("Presentation");
                if(json2.equals("")){
                    prelist = new ArrayList<Presentation>();
                }else {
                    Type type = new TypeToken<ArrayList<Presentation>>() {
                    }.getType();
                    prelist = gson.fromJson(json2, type);

                    for(Presentation pr : prelist){
                        try {
                            Date d = spf.parse(pr.getDay() + "/" + pr.getMonth() + "/" + pr.getYear());
                            Event event = new Event(android.graphics.Color.parseColor(pr.getHour().getColorCode()), d.getTime(), getResources().getString(R.string.Presentations) + ": " + pr.getTitle());
                            compact.addEvent(event);
                        }catch (Exception e){}
                    }
                }

                Date d = ca.getTime();
                DateTime dateTime = new DateTime(d);
                learningunit.learningunit.Objects.Organizer.Event currentTimeEvent = new learningunit.learningunit.Objects.Organizer.Event(dateTime.getDayOfMonth(), dateTime.getMonthOfYear(), dateTime.getYear());
                ArrayList<Exam> exlist = new ArrayList<Exam>();
                if(examlist.size() > 0) {
                    for (Exam exam : examlist) {
                        if(EventMethods.isYounger((learningunit.learningunit.Objects.Organizer.Event) exam, currentTimeEvent)){
                            exlist.add(exam);
                        }
                    }
                }
                ArrayList<Presentation> plist = new ArrayList<Presentation>();
                if(prelist.size() > 0) {
                    for (Presentation presentation : prelist) {
                        if(EventMethods.isYounger((learningunit.learningunit.Objects.Organizer.Event) presentation, currentTimeEvent)){
                            plist.add(presentation);
                        }
                    }
                }
                ArrayList<Homework> hlist = new ArrayList<Homework>();
                if(eventlist.size() > 0) {
                    for (Homework ha : eventlist) {
                        if(EventMethods.isYounger((learningunit.learningunit.Objects.Organizer.Event) ha, currentTimeEvent)){
                            hlist.add(ha);
                        }
                    }
                }


                ListView nextEvent = (ListView) fragmentView.findViewById(R.id.fragment_organizer_calender_ListView);
                if(exlist.size() > 0 || plist.size() > 0 || hlist.size() > 0) {
                    nextEvent.setVisibility(View.VISIBLE);
                    fragmentView.findViewById(R.id.organizer_calender_info).setVisibility(View.GONE);
                    fragmentView.findViewById(R.id.organizer_calender_create).setVisibility(View.GONE);
                    nextEvent.setAdapter(new HomeCustomAdapter(exlist, hlist, plist, getActivity()));
                }else{
                    nextEvent.setVisibility(View.GONE);
                    fragmentView.findViewById(R.id.organizer_calender_info).setVisibility(View.VISIBLE);
                    Button create = (Button) fragmentView.findViewById(R.id.organizer_calender_create);
                    create.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mViewPager.setCurrentItem(0);
                        }
                    });
                }

                return fragmentView;
            }else{
                return null;
            }
        }
    }








    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return Organizer.PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            return 3;
        }
    }
}