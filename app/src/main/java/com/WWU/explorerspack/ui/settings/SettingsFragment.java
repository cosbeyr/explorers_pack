package com.WWU.explorerspack.ui.settings;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;
import androidx.preference.SwitchPreference;


import com.WWU.explorerspack.R;

public class SettingsFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {
    SharedPreferences sharedPreferences;
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.settings, rootKey);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
    }

    @Override
    public void onResume() {
        super.onResume();
        //unregister the preferenceChange listener
        getPreferenceScreen().getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Preference preference = findPreference(key);
        if (preference instanceof SwitchPreference && key.equals("save_to_gallery")) {
            if (((SwitchPreference) preference).isChecked()){
                Toast.makeText(getActivity(),"Got it! We'll save pictures to your gallery.",Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getActivity(),"Pictures won't be saved to your gallery.", Toast.LENGTH_SHORT).show();
            }
        } else if (preference instanceof SwitchPreference && key.equals("home_page_tips")){
            if (((SwitchPreference) preference).isChecked()){
                Toast.makeText(getActivity(),"Looks like you've mastered our app!",Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getActivity(),"We've got useful hints for you!", Toast.LENGTH_SHORT).show();
            }
        }
    }
    @Override
    public void onPause() {
        super.onPause();
        //unregister the preference change listener
        getPreferenceScreen().getSharedPreferences()
                .unregisterOnSharedPreferenceChangeListener(this);
    }

}



