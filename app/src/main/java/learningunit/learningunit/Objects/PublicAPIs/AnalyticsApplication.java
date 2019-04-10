package learningunit.learningunit.Objects.PublicAPIs;

import android.app.Application;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;

import learningunit.learningunit.Objects.API.ManageData;
import learningunit.learningunit.R;

/**
 * This is a subclass of {@link Application} used to provide shared objects for this app, such as
 * the {@link Tracker}.
 */
public class AnalyticsApplication extends Application {

    private static GoogleAnalytics analytics;
    private static Tracker tracker;

    @Override
    public void onCreate() {
        super.onCreate();
        if(ManageData.OfflineAccount == 2) {
            startGoogleAnalytics();
        }
    }

    public void startGoogleAnalytics() {
        analytics = GoogleAnalytics.getInstance(this);
        analytics.setLocalDispatchPeriod(10);

        tracker = analytics.newTracker(R.xml.global_tracker);
        tracker.enableExceptionReporting(true);
        tracker.enableAdvertisingIdCollection(true);
        tracker.enableAutoActivityTracking(true);
    }

    public synchronized Tracker getDefaultTracker() {
        if (tracker == null) {
            startGoogleAnalytics();
        }
        return tracker;
    }
}