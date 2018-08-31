package net.sytes.csongi.fragmentpreferencechangetest;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.prefs.Preferences;

public class PreferencesActivity extends AppCompatActivity implements PreferenceExampleFragment.PreferenceXchangeListener {
    private static final String TAG = PreferencesActivity.class.getSimpleName();
    // declaring initial value for applying appropriate Theme
    private Boolean mCurrentValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Checking which Theme should be used. IMPORTANT: applying Themes MUST called BEFORE super.onCreate() and setContentView!!!
        Log.d(TAG, "onCreate:::: retrieving preferences");
        SharedPreferences mSharedPreferences= PreferenceManager.getDefaultSharedPreferences(this);
        mCurrentValue=mSharedPreferences.getBoolean("my_preference",false);
        Log.d(TAG, "onCreate:::: my_preference and mCurrentValue="+mCurrentValue);
        if(mCurrentValue){
            // we have to use simple setTheme() instead getApplication.setTheme()!!!
            setTheme(R.style.AppTheme);
            Log.d(TAG, "onCreate:::: setTheme:AppTheme");
        } else {
            setTheme(R.style.NewTheme);
            Log.d(TAG, "onCreate:::: setTheme:NewTheme");
        }


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);

        Fragment preferenceFragment = new PreferenceExampleFragment();
        getFragmentManager().beginTransaction().add(R.id.preference_container, preferenceFragment).commit();
    }

    // callback method for changing preference. It's called only if "my_preference" has changed
    @Override
    public void onXchange(Boolean value) {
        // if value differs from previous Theme, we recreate Activity
        Log.d(TAG, "onXchange:::: \n has called");
        if (value!=mCurrentValue) {
            Log.d(TAG, "onXchange:::: \n new value!=oldValue");
            mCurrentValue=value;
            recreate();
        }
    }
}
