package learningunit.learningunit.Objects.Organizer;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import learningunit.learningunit.R;

public class HomeCustomAdapter extends BaseAdapter {

    ArrayList<Homework> eventlist = new ArrayList<Homework>();
    ArrayList<Exam> examlist = new ArrayList<Exam>();
    ArrayList<Certificate> certlist = new ArrayList<Certificate>();
    private LayoutInflater inflater;
    private Activity activity;
    private boolean out = false;
    private int j;


    public HomeCustomAdapter(Activity activity, ArrayList<Homework> eventlist){

        ArrayList<Event> elist = new ArrayList<Event>();
        for(Homework h : eventlist){
            elist.add((Event) h);
        }
        elist = EventMethods.SortEventList(elist);
        for(Event e : elist){
            this.eventlist.add((Homework) e);
        }
        this.j = 0;
        this.examlist = null;
        certlist = null;
        this.inflater = (LayoutInflater.from(activity));
        this.activity = activity;
    }

    public HomeCustomAdapter( ArrayList<Exam> examlist, Activity activity){

        ArrayList<Event> elist = new ArrayList<Event>();
        for(Exam h : examlist){
            elist.add((Event) h);
        }
        elist = EventMethods.SortEventList(elist);
        for(Event e : elist){
            this.examlist.add((Exam) e);
        }
        this.j = 1;
        this.eventlist = null;
        certlist = null;
        this.inflater = (LayoutInflater.from(activity));
        this.activity = activity;
    }

    public HomeCustomAdapter(Activity activity, ArrayList<Certificate> certlist, int oof){
        this.examlist = null;
        this.eventlist = null;
        this.certlist = certlist;
        this.j = 2;
        this.inflater = (LayoutInflater.from(activity));
        this.activity = activity;
    }

    @Override
    public int getCount() {
        int returner;
        if(eventlist != null){
            returner =  eventlist.size();
        }else if(examlist != null) {
            returner = examlist.size();
        }else if (certlist != null){
            returner = certlist.size();
        }else{
            returner = 0;
        }
        return returner;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        if(j == 0) {
            convertView = inflater.inflate(R.layout.organizer_homework_listview, null);
            convertView.findViewById(R.id.organizer_Exam_ListView_RootLayout).setVisibility(View.GONE);
            convertView.findViewById(R.id.organizer_homework_Certificate_RootLayout).setVisibility(View.GONE);
            convertView.findViewById(R.id.organizer_homework_ListView_RootLayout).setVisibility(View.VISIBLE);

            final View cView = convertView;
            final Homework h = eventlist.get(position);

            TextView title = (TextView) convertView.findViewById(R.id.organizer_homework_ListView_Title);
            title.setText(h.getTitle());

            TextView Date = (TextView) convertView.findViewById(R.id.organizer_homework_ListView_Date);
            Date.setText(h.getDay() + "." + h.getMonth() + "." + h.getYear());

            ImageView image = (ImageView) convertView.findViewById(R.id.organizer_homework_ListView_ImageView);
            image.setBackgroundColor(android.graphics.Color.parseColor(h.getHour().getColorCode()));

            convertView.findViewById(R.id.organizer_homework_ListView_SecondLayout).setVisibility(View.GONE);

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (out == false) {
                        cView.findViewById(R.id.organizer_homework_ListView_SecondLayout).setVisibility(View.VISIBLE);

                        TextView Hour = (TextView) cView.findViewById(R.id.organizer_homework_ListView_HourName);
                        Hour.setText(h.getHour().getName());

                        TextView Description = (TextView) cView.findViewById(R.id.organizer_homework_ListView_DescriptionText);
                        Description.setText(h.getDescription());

                        final Button done = (Button) cView.findViewById(R.id.organizer_homework_ListView_Done);
                        if (h.getDone()) {
                            done.setText(activity.getResources().getString(R.string.NotDone));
                        } else {
                            done.setText(activity.getResources().getString(R.string.Done));
                        }
                        done.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (h.getDone()) {
                                    done.setText(activity.getResources().getString(R.string.Done));
                                    h.setDone(false);
                                } else {
                                    done.setText(activity.getResources().getString(R.string.NotDone));
                                    h.setDone(true);
                                }
                            }
                        });
                        out = true;
                    } else {
                        out = false;
                        cView.findViewById(R.id.organizer_homework_ListView_SecondLayout).setVisibility(View.GONE);
                    }
                }
            });
        }else if(j == 1){
            convertView = inflater.inflate(R.layout.organizer_homework_listview, null);
            final View cView = convertView;
            final Exam h = examlist.get(position);
            convertView.findViewById(R.id.organizer_Exam_ListView_RootLayout).setVisibility(View.VISIBLE);
            convertView.findViewById(R.id.organizer_homework_ListView_RootLayout).setVisibility(View.GONE);
            convertView.findViewById(R.id.organizer_homework_Certificate_RootLayout).setVisibility(View.GONE);

            TextView title = (TextView) convertView.findViewById(R.id.organizer_exam_ListView_Title);
            title.setText(h.getTitle());

            TextView Date = (TextView) convertView.findViewById(R.id.organizer_exam_ListView_Date);
            Date.setText(h.getDay() + "." + h.getMonth() + "." + h.getYear());

            ImageView image = (ImageView) convertView.findViewById(R.id.organizer_exam_ListView_ImageView);
            image.setBackgroundColor(android.graphics.Color.parseColor(h.getHour().getColorCode()));

            convertView.findViewById(R.id.organizer_exam_ListView_SecondLayout).setVisibility(View.GONE);

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (out == false) {
                        cView.findViewById(R.id.organizer_exam_ListView_SecondLayout).setVisibility(View.VISIBLE);

                        TextView Hour = (TextView) cView.findViewById(R.id.organizer_exam_ListView_HourName);
                        Hour.setText(h.getHour().getName());

                        TextView Description = (TextView) cView.findViewById(R.id.organizer_exam_ListView_DescriptionText);
                        Description.setText(h.getDescription());
                        out = true;
                    }else {
                        out = false;
                        cView.findViewById(R.id.organizer_exam_ListView_SecondLayout).setVisibility(View.GONE);
                    }
                }
            });
        } else if (j == 2) {
            convertView = inflater.inflate(R.layout.organizer_homework_listview, null);
            convertView.findViewById(R.id.organizer_Exam_ListView_RootLayout).setVisibility(View.GONE);
            convertView.findViewById(R.id.organizer_homework_Certificate_RootLayout).setVisibility(View.VISIBLE);
            convertView.findViewById(R.id.organizer_homework_ListView_RootLayout).setVisibility(View.GONE);

            TextView titel = (TextView) convertView.findViewById(R.id.organizer_Certificate_ListView_Titlel);
            titel.setText(certlist.get(position).getTitle());
            TextView Description = (TextView) convertView.findViewById(R.id.organizer_Certificate_ListView_DescriptionText);
            Description.setText(certlist.get(position).getDescription());
        }

        return convertView;
    }
}
