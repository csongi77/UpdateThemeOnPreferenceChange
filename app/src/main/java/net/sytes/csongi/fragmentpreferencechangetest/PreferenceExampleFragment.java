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
    private PreferenceXchangeListener mPreferenceXchangeListener;

    public PreferenceExampleFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);

        CheckBoxPreference mCheckBoxPreference=(CheckBoxPreference)findPreference("my_preference");
        mCheckBoxPreference.setOnPreferenceChangeListener(this);
       /* SharedPreferences mSharedPreferences = getPreferenceManager().getSharedPreferences();
        Boolean currentSetting=mSharedPreferences.getBoolean("my_checkbox",false);
        onPreferenceChange(mCheckBoxPreference,currentSetting);*/

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mPreferenceXchangeListener = (PreferenceXchangeListener) context;
        } catch (ClassCastException e) {
            Log.e(TAG, "onAttach::::: PreferenceXchangeListener must be set in parent Activity");
        }
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        String preferenceKey=preference.getKey();
        if(preferenceKey.equals("my_preference")){
            ((CheckBoxPreference)preference).setChecked((Boolean)newValue);
            mPreferenceXchangeListener.onXchange((Boolean)newValue);
            return true;
        }
        return false;
    }

    public interface PreferenceXchangeListener {
        void onXchange(Boolean value);
    }

}
