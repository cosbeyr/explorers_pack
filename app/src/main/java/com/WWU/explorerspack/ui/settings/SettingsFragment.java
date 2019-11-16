package com.WWU.explorerspack.ui.settings;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.SwitchPreference;
import android.widget.Toast;

import com.WWU.explorerspack.R;

public class SettingsFragment extends Fragment {

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new MyPreferenceFragment())
                .commit();
    }

    public static class MyPreferenceFragment extends PreferenceFragment {
        private Activity mActivity;

        public MyPreferenceFragment() {
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            // Load the preferences from an XML resource
            addPreferencesFromResource(R.xml.settings);
            // Get the application context
            mActivity = this.getActivity();

        /*
            SwitchPreference
                This preference will store a boolean into the SharedPreferences.
        */

            // Get the preference widgets reference
            final SwitchPreference saveToGallery = (SwitchPreference) findPreference(this.getResources()
                    .getString(R.string.save_to_gallery));

            final SwitchPreference homePageTips = (SwitchPreference) findPreference(this.getResources()
                    .getString(R.string.home_page_tips));

        /*
            void setOnPreferenceChangeListener (Preference.OnPreferenceChangeListener onPreferenceChangeListener)
                Sets the callback to be invoked when this Preference is changed by the user
                (but before the internal state has been updated).

            Parameters
                onPreferenceChangeListener Preference.OnPreferenceChangeListener: The callback to be invoked.
        */

            // SwitchPreference preference change listener
            saveToGallery.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object o) {
                    if(saveToGallery.isChecked()){
                        Toast.makeText(mActivity,"Pictures won't be saved to your gallery.",Toast.LENGTH_SHORT).show();

                        // Checked the switch programmatically
                        saveToGallery.setChecked(false);
                    }else {
                        Toast.makeText(mActivity,"Got it! We'll save pictures to your gallery.",Toast.LENGTH_SHORT).show();

                        // Unchecked the switch programmatically
                        saveToGallery.setChecked(true);
                    }
                    return false;
                }
            });

            // SwitchPreference preference change listener
            homePageTips.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object o) {
                    if(homePageTips.isChecked()){
                        Toast.makeText(mActivity,"Looks like you've mastered our app!",Toast.LENGTH_SHORT).show();

                        // Checked the switch programmatically
                        homePageTips.setChecked(false);
                    }else {
                        Toast.makeText(mActivity,"We've got you!",Toast.LENGTH_SHORT).show();

                        // Unchecked the switch programmatically
                        homePageTips.setChecked(true);
                    }
                    return false;
                }
            });
        }
    }
}



