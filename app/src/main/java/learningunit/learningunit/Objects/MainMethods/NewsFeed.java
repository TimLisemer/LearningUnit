package learningunit.learningunit.Objects.MainMethods;



import org.joda.time.Interval;
import org.joda.time.Period;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;


import learningunit.learningunit.menu.MainActivity;

public class NewsFeed {

    private static ArrayList<ArrayList<String>> News = new ArrayList<ArrayList<String>>();



    private static void NewsTime(){

        MainActivity.news.setText("");
    }

    public static void CreateNews(String message, String TimePattern){ // ------> TimePattern = dd/MM/yyyy hh:mm:ss

        ArrayList content = new ArrayList<String>();
        content.add(TimePattern);
        content.add(message);

    }

    public static String ReadNews(String TimePattern){ // ------> TimePattern = dd/MM/yyyy hh:mm:ss


        for(int i = 0; i < News.size(); i++){
            ArrayList list;
            list = News.get(i);
            if(list.contains(TimePattern)){
                return list.get(1).toString();
            }
        }
        return "";
    }

    public static void DeleteNews(String TimePattern){
        for(int i = 0; i < News.size(); i++){
            ArrayList list;
            list = News.get(i);
            if(list.contains(TimePattern)){
                News.remove(i);
            }
        }
    }


    public static void startTime() {

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("CET"));

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("CET"));

        try {

            Date date1 = calendar.getTime();
            Date date2 = simpleDateFormat.parse("22/02/2019 00:00:00");
            String s = getTimeString(date1, date2);
            MainActivity.news.setText(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    public static String getTimeString (Date startDate, Date endDate){

        Interval interval =
                new Interval(startDate.getTime(), endDate.getTime());
        Period period = interval.toPeriod();

        return period.getYears() + "y, " + period.getMonths() + 1 + "m, "+ period.getDays() + "d, " +period.getHours() + "h, " + period.getMinutes() + "m, " + period.getSeconds() + "s";
        //return startDate + "                    " + endDate;
    }





    public static boolean BeforeAfter(String createdDateString, String expireDateString){        //Before = true After = false

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        dateFormat.setTimeZone(TimeZone.getTimeZone("CET"));

        Date createdConvertedDate = null, expireCovertedDate = null, todayWithZeroTime = null;
        try {
            createdConvertedDate = dateFormat.parse(createdDateString);
            expireCovertedDate = dateFormat.parse(expireDateString);

            Date today = new Date();

            todayWithZeroTime = dateFormat.parse(dateFormat.format(today));
        } catch (ParseException e) {
            e.printStackTrace();
        }


        if (createdConvertedDate.after(todayWithZeroTime)) {
            return  true;


        } else {
            return false;

        }
    }



}
