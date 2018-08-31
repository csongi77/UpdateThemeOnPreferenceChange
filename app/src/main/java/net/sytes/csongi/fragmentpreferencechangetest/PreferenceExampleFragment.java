package net.sytes.csongi.fragmentpreferencechangetest;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;
import android.util.Log;

public class PreferenceExampleFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener {
    private static final String TAG = PreferenceExampleFragment.class.getSimpleName();
    // declaring PreferenceXchangeListener
    private PreferenceXchangeListener mPreferenceXchangeListener;

    public PreferenceExampleFragment() {
    }

    // declaring PreferenceXchangeListener in order to communicate with Activities
    public interface PreferenceXchangeListener {
        void onXchange(Boolean value);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);

        CheckBoxPreference mCheckBoxPreference=(CheckBoxPreference)findPreference("my_preference");
        mCheckBoxPreference.setOnPreferenceChangeListener(this);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // on Attch we assign parent Activity as PreferenceXchangeListener
        try {
            mPreferenceXchangeListener = (PreferenceXchangeListener) context;
        } catch (ClassCastException e) {
            Log.e(TAG, "onAttach::::: PreferenceXchangeListener must be set in parent Activity");
        }
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        String preferenceKey=preference.getKey();
        // only my_preference is checked in this case. Later you may add another behaviour to another preference change
        if(preferenceKey.equals("my_preference")){
            ((CheckBoxPreference)preference).setChecked((Boolean)newValue);
            // executing parent Activity's callback with the new value
            mPreferenceXchangeListener.onXchange((Boolean)newValue);
            return true;
        }
        return false;
    }

}
