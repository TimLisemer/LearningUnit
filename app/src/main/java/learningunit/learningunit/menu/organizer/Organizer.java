package learningunit.learningunit.menu.organizer;

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
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.android.gms.analytics.Tracker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import learningunit.learningunit.Objects.API.AnalyticsApplication;
import learningunit.learningunit.Objects.Organizer.HomeFragmentMethods;
import learningunit.learningunit.R;

public class Organizer extends AppCompatActivity {

    private TextView mTextMessage;
    private Organizer.SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AnalyticsApplication application = (AnalyticsApplication) getApplication();
        mTracker = application.getDefaultTracker();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organizer);

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
                if (prevMenuItem != null)
                    prevMenuItem.setChecked(false);
                else
                    navigation.getMenu().getItem(0).setChecked(false);

                    navigation.getMenu().getItem(position).setChecked(true);
                    prevMenuItem = navigation.getMenu().getItem(position);

                    try{
                        ScrollView newEvent = (ScrollView) findViewById(R.id.fragment_organizer_home_NewEventLayout);
                        ConstraintLayout homeLayout = (ConstraintLayout) findViewById(R.id.fragment_organizer_home_MainLayout);
                        newEvent.setVisibility(View.GONE);
                        homeLayout.setVisibility(View.VISIBLE);
                        CalendarView cv = (CalendarView) findViewById(R.id.fragment_organizer_home_NewEvent_Calendar);
                        cv.setDate(new Date().getTime(), false, true);
                    }catch (Exception e){
                        throw new IllegalArgumentException(e);
                    }
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
                return fragmentView;
            }else if(getArguments().getInt(ARG_SECTION_NUMBER) == 3){
                final View fragmentView = inflater.inflate(R.layout.fragment_organizer_calendar, container, false);
                return fragmentView;
            }else{
                return null;
            }
        }
    }

//






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
