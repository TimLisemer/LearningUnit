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
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.android.gms.analytics.Tracker;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import learningunit.learningunit.Objects.API.OnBackPressedListener;
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

                Gson gson = new Gson();
                ArrayList<Homework> eventlist;
                String json = FirstScreen.tinyDB.getString("Homework");
                if(json.equals("")){
                    eventlist = new ArrayList<Homework>();
                }else {
                    Type type = new TypeToken<ArrayList<Homework>>() {
                    }.getType();
                    eventlist = gson.fromJson(json, type);
                }

                ArrayList<Exam> examlist;
                String json1 = FirstScreen.tinyDB.getString("Exam");
                if(json1.equals("")){
                    examlist = new ArrayList<Exam>();
                }else {
                    Type type = new TypeToken<ArrayList<Exam>>() {
                    }.getType();
                    examlist = gson.fromJson(json1, type);
                }

                ArrayList<Presentation> prelist;
                String json2 = FirstScreen.tinyDB.getString("Presentation");
                if(json2.equals("")){
                    prelist = new ArrayList<Presentation>();
                }else {
                    Type type = new TypeToken<ArrayList<Presentation>>() {
                    }.getType();
                    prelist = gson.fromJson(json2, type);
                }

                ListView nextEvent = (ListView) fragmentView.findViewById(R.id.fragment_organizer_calender_ListView);
                if(examlist.size() > 0 || prelist.size() > 0 || eventlist.size() > 0) {
                    nextEvent.setVisibility(View.VISIBLE);
                    fragmentView.findViewById(R.id.organizer_calender_info).setVisibility(View.GONE);
                    fragmentView.findViewById(R.id.organizer_calender_create).setVisibility(View.GONE);
                    nextEvent.setAdapter(new HomeCustomAdapter(examlist, eventlist, prelist, getActivity()));
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