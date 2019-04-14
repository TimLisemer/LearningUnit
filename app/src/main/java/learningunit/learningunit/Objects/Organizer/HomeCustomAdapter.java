package learningunit.learningunit.Objects.Organizer;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;

import learningunit.learningunit.R;
import learningunit.learningunit.beforeStart.FirstScreen;
import learningunit.learningunit.menu.MainActivity;

public class HomeCustomAdapter extends BaseAdapter {

    ArrayList<Homework> eventlist = new ArrayList<Homework>();
    ArrayList<Exam> examlist = new ArrayList<Exam>();
    ArrayList<Presentation> prelist = new ArrayList<Presentation>();
    ArrayList<Event> clist = new ArrayList<Event>();
    ArrayList<Certificate> certlist = new ArrayList<Certificate>();
    private LayoutInflater inflater;
    private Activity activity;
    private boolean out = false;
    private int j, g;


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
        this.g = 2;
        this.examlist = null;
        this.prelist = null;
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

        this.g = 2;
        this.j = 1;
        this.eventlist = null;
        this.prelist = null;
        this.certlist = null;
        this.inflater = (LayoutInflater.from(activity));
        this.activity = activity;
    }

    public HomeCustomAdapter( ArrayList<Presentation> prelist, Activity activity, int oof){

        ArrayList<Event> elist = new ArrayList<Event>();
        for(Presentation h : prelist){
            elist.add((Event) h);
        }
        elist = EventMethods.SortEventList(elist);
        for(Event e : elist){
            this.prelist.add((Presentation) e);
        }

        this.g = 2;
        this.j = 3;
        this.eventlist = null;
        this.examlist = null;
        this.certlist = null;
        this.inflater = (LayoutInflater.from(activity));
        this.activity = activity;
    }

    public HomeCustomAdapter(ArrayList<Exam> examlist, ArrayList<Homework> eventlist, ArrayList<Presentation> prelist, Activity activity, int jahiii){
        ArrayList<Event> exlist = new ArrayList<Event>();
        if(examlist.size() > 0) {
            for (Exam exam : examlist) {
                exlist.add((Event) exam);
            }
            exlist = EventMethods.SortEventList(exlist);
            for(Event e : exlist){
                this.examlist.add((Exam) e);
            }
        }
        ArrayList<Event> elist = new ArrayList<Event>();
        if(eventlist.size() > 0) {
            for (Homework event : eventlist) {
                elist.add((Event) event);
            }
            elist = EventMethods.SortEventList(elist);
            for(Event e : elist){
                this.eventlist.add((Homework) e);
            }
        }
        ArrayList<Event> plist = new ArrayList<Event>();
        if(prelist.size() > 0) {
            for (Presentation pre : prelist) {
                plist.add((Event) pre);
            }
            plist = EventMethods.SortEventList(plist);
            for(Event e : plist){
                this.prelist.add((Presentation) e);
            }
        }

        this.g = 1;
        this.inflater = (LayoutInflater.from(activity));
        this.activity = activity;
    }

    public HomeCustomAdapter(ArrayList<Exam> examlist, ArrayList<Homework> eventlist, ArrayList<Presentation> prelist, Activity activity){
        ArrayList<Event> clist = new ArrayList<Event>();
        if(examlist.size() > 0) {
            for (Exam exam : examlist) {
                clist.add((Event) exam);
            }
        }
        if(eventlist.size() > 0) {
            for (Homework event : eventlist) {
                clist.add((Event) event);
            }
        }
        if(prelist.size() > 0) {
            for (Presentation pre : prelist) {
                clist.add((Event) pre);
            }
        }
        this.clist = EventMethods.SortEventList(clist);

        this.g = 0;
        this.eventlist = null;
        this.examlist = null;
        this.prelist = null;
        this.inflater = (LayoutInflater.from(activity));
        this.activity = activity;
    }

    public HomeCustomAdapter(Activity activity, ArrayList<Certificate> certlist, int oof){
        this.g = 2;
        this.examlist = null;
        this.eventlist = null;
        this.prelist = null;
        this.certlist = certlist;
        this.j = 2;
        this.inflater = (LayoutInflater.from(activity));
        this.activity = activity;
    }

    @Override
    public int getCount() {
        int returner;
        if(g == 0){
            returner = 1;
        }else if(eventlist != null && examlist != null && prelist != null && g == 1) {
            returner = eventlist.size() + examlist.size() + prelist.size();
        }else if(eventlist != null){
            returner =  eventlist.size();
        }else if(examlist != null) {
            returner = examlist.size();
        }else if (certlist != null){
            returner = certlist.size();
        }else if (prelist != null){
            returner = prelist.size();
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
    public View getView(int position, View convertView, final ViewGroup parent) {
        convertView = inflater.inflate(R.layout.organizer_homework_listview, null);
        int oposition = position;
        TextView EventHeading = (TextView) convertView.findViewById(R.id.organizer_homework_ListView_EventHeading);
        if(g == 0){
            EventHeading.setVisibility(View.VISIBLE);
            if(clist.get(0) instanceof Homework){
                j = 0;
                EventHeading.setText(activity.getResources().getString(R.string.Homework));
                this.eventlist = new ArrayList<Homework>();
                this.eventlist.add((Homework) clist.get(0));
            }else if(clist.get(0) instanceof Exam){
                EventHeading.setText(activity.getResources().getString(R.string.Exam));
                j = 1;
                this.examlist = new ArrayList<Exam>();
                this.examlist.add((Exam) clist.get(0));
            }else if(clist.get(0) instanceof Presentation){
                EventHeading.setText(activity.getResources().getString(R.string.Presentations));
                j = 3;
                this.prelist = new ArrayList<Presentation>();
                this.prelist.add((Presentation) clist.get(0));
            }else{
                throw new IllegalArgumentException("HomeCustomAdapter");
            }
        }else if(g == 1){
            if(position < this.eventlist.size()){
                j = 0;
            }else if(position >= this.eventlist.size() && position - this.eventlist.size() < this.examlist.size()){
                j = 1;
                position = position - this.eventlist.size();
            }else if(position >= this.eventlist.size() && position - this.eventlist.size() >= this.examlist.size() && position - this.eventlist.size() - this.examlist.size() < this.prelist.size()){
                j = 3;
                position = position - this.eventlist.size() - this.examlist.size();
            }
        }

        if (j == 0) {
            if(g > 1){
                EventHeading.setVisibility(View.GONE);
            }else if(g == 1){
                if(position == 0){
                    EventHeading.setVisibility(View.VISIBLE);
                    EventHeading.setText(activity.getResources().getString(R.string.Homework));
                }else{
                    EventHeading.setVisibility(View.GONE);
                }
            }
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
                                Gson gson = new Gson();
                                FirstScreen.tinyDB.putString("Homework", gson.toJson(eventlist));
                            }
                        });
                        out = true;
                    } else {
                        out = false;
                        cView.findViewById(R.id.organizer_homework_ListView_SecondLayout).setVisibility(View.GONE);
                    }
                }
            });
        } else if (j == 1) {
            if(g > 1){
                EventHeading.setVisibility(View.GONE);
            }else if(g == 1){
                if(position == 0){
                    EventHeading.setVisibility(View.VISIBLE);
                    EventHeading.setText(activity.getResources().getString(R.string.Exam));
                }else{
                    EventHeading.setVisibility(View.GONE);
                }
            }
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

            final int pos = position;

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (out == false) {
                        cView.findViewById(R.id.organizer_exam_ListView_SecondLayout).setVisibility(View.VISIBLE);

                        TextView Hour = (TextView) cView.findViewById(R.id.organizer_exam_ListView_HourName);
                        Hour.setText(h.getHour().getName());

                        TextView Description = (TextView) cView.findViewById(R.id.organizer_exam_ListView_DescriptionText);
                        Description.setText(h.getDescription());

                        Button enter = (Button) cView.findViewById(R.id.organizer_exam_ListView_EnterGrade);
                        enter.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                enterGrade((Event) h, pos, examlist, null, activity);
                            }
                        });

                        out = true;
                    } else {
                        out = false;
                        cView.findViewById(R.id.organizer_exam_ListView_SecondLayout).setVisibility(View.GONE);
                    }
                }
            });
        } else if (j == 2) {
            if(g > 1){
                EventHeading.setVisibility(View.GONE);
            }
            convertView.findViewById(R.id.organizer_Exam_ListView_RootLayout).setVisibility(View.GONE);
            convertView.findViewById(R.id.organizer_homework_Certificate_RootLayout).setVisibility(View.VISIBLE);
            convertView.findViewById(R.id.organizer_homework_ListView_RootLayout).setVisibility(View.GONE);

            TextView titel = (TextView) convertView.findViewById(R.id.organizer_Certificate_ListView_Titlel);
            titel.setText(certlist.get(position).getTitle());
            TextView Description = (TextView) convertView.findViewById(R.id.organizer_Certificate_ListView_DescriptionText);
            Description.setText(certlist.get(position).getDescription());

        } else if (j == 3) {
            if(g > 1){
                EventHeading.setVisibility(View.GONE);
            }else if(g == 1){
                if(position == 0){
                    EventHeading.setVisibility(View.VISIBLE);
                    EventHeading.setText(activity.getResources().getString(R.string.Presentations));
                }else{
                    EventHeading.setVisibility(View.GONE);
                }
            }
            final View cView = convertView;
            final Presentation h = prelist.get(position);
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

            final int pos = position;

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (out == false) {
                        cView.findViewById(R.id.organizer_exam_ListView_SecondLayout).setVisibility(View.VISIBLE);

                        TextView Hour = (TextView) cView.findViewById(R.id.organizer_exam_ListView_HourName);
                        Hour.setText(h.getHour().getName());

                        TextView Description = (TextView) cView.findViewById(R.id.organizer_exam_ListView_DescriptionText);
                        Description.setText(h.getDescription());

                        Button enter = (Button) cView.findViewById(R.id.organizer_exam_ListView_EnterGrade);
                        enter.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                enterGrade((Event) h, pos, null, prelist, activity);
                            }
                        });


                        out = true;
                    } else {
                        out = false;
                        cView.findViewById(R.id.organizer_exam_ListView_SecondLayout).setVisibility(View.GONE);
                    }
                }
            });

        }
        position = oposition;
        return convertView;
    }



    public static void enterGrade(final Event event, final int pos, final ArrayList<Exam> ex, final ArrayList<Presentation> pr, Activity activity){
        final AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setCancelable(true);
        builder.setTitle(activity.getResources().getString(R.string.EnterGrade));
        builder.setMessage(activity.getResources().getString(R.string.TimetableEnterID));

        final LinearLayout editLayout = new LinearLayout(activity);
        final EditText editNumber = new EditText(activity);
        editNumber.setInputType(InputType.TYPE_CLASS_NUMBER);
        editNumber.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        MainActivity.showKeyboard(activity);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        params.setMargins(48, 8, 48, 8);
        editLayout.addView(editNumber, params);
        builder.setView(editLayout);
        MainActivity.showKeyboard(activity);

        builder.setNegativeButton(activity.getResources().getString(R.string.Back), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });


        builder.setPositiveButton(activity.getResources().getString(R.string.Ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(editNumber.getText().toString().equals("")){
                    dialog.cancel();
                }else {
                    Gson gson = new Gson();
                    if(event instanceof Exam){
                        ((Exam) event).setGrade(Integer.parseInt(editNumber.getText().toString()));
                        ex.remove(pos);
                        ex.add((Exam) event);
                        FirstScreen.tinyDB.putString("Exam", gson.toJson(ex));
                    }else if(event instanceof Presentation){
                        ((Presentation) event).setGrade(Integer.parseInt(editNumber.getText().toString()));
                        pr.remove(pos);
                        pr.add((Presentation) event);
                        FirstScreen.tinyDB.putString("Presentation", gson.toJson(pr));
                    }
                    dialog.cancel();
                }
            }
        });


        MainActivity.showKeyboard(activity);
        builder.show();
        MainActivity.showKeyboard(activity);
    }













































}
