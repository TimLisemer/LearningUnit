package learningunit.learningunit.menu;

import android.content.Context;
import android.content.SharedPreferences;

public class Darkmode {
    SharedPreferences myDarkmode ;
    public Darkmode(Context context) {
        myDarkmode = context.getSharedPreferences("filename",Context.MODE_PRIVATE);
    }
    // this method will save the nightMode State : True or False
    public void setNightModeState(Boolean state) {
        SharedPreferences.Editor editor = myDarkmode.edit();
        editor.putBoolean("NightMode",state);
        editor.commit();
    }
    // this method will load the Night Mode State
    public Boolean loadNightModeState (){
        Boolean state = myDarkmode.getBoolean("NightMode",false);
        return  state;
    }

}
