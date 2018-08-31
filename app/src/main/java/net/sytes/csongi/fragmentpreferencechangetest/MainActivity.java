package net.sytes.csongi.fragmentpreferencechangetest;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    // declaring initial value for applying appropriate Theme
    private Boolean mOriginalCurrentValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Checking which Theme should be used. IMPORTANT: applying Themes MUST called BEFORE super.onCreate() and setContentView!!!
        Log.d(TAG, "onCreate:::: retrieving preferences");
        SharedPreferences mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mOriginalCurrentValue = mSharedPreferences.getBoolean("my_preference", false);
        Log.d(TAG, "onCreate:::: my_preference and mCurrentValue=" + mOriginalCurrentValue);
        if (mOriginalCurrentValue) {
            setTheme(R.style.AppTheme);
            Log.d(TAG, "onCreate:::: setTheme:AppTheme");
        } else {
           setTheme(R.style.NewTheme);
            Log.d(TAG, "onCreate:::: setTheme:NewTheme");
        }


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    // in order to recreate Activity we must check value here because when we come back from another Activity onCreate doesn't called again
    @Override
    protected void onStart() {
        super.onStart();
        setContentView(R.layout.activity_main);
        SharedPreferences mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        Boolean mNewValue = mSharedPreferences.getBoolean("my_preference", false);
        // if value differs from previous Theme, we recreate Activity
        if(mOriginalCurrentValue!=mNewValue){
            recreate();
        }
        Button myButton = findViewById(R.id.btn_goto_preferences);
        myButton.setOnClickListener(v -> openPreferences());
    }

    private void openPreferences() {
        Intent openPreferencesIntent = new Intent(MainActivity.this, PreferencesActivity.class);
        startActivity(openPreferencesIntent);
    }


}
