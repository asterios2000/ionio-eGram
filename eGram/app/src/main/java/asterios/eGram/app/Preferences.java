package asterios.eGram.app;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by asterios on 9/5/2015.
 */
public class Preferences extends Activity {

    //Setting basic preferences
    public static void setPreferences(int defaultProfile, boolean firstTime, Context eGram){
        SharedPreferences sf = PreferenceManager.getDefaultSharedPreferences(eGram);

        SharedPreferences.Editor editor = sf.edit();
        editor.clear();
        editor.putInt("defProfile", defaultProfile);
        editor.putBoolean("first", firstTime);

    	editor.apply();


    }


}
