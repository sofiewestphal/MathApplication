package com.example.sofie.mymathapplication;

import android.os.Bundle;
import android.preference.PreferenceFragment;


public class SettingsFragment extends PreferenceFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the preferences from the preferences file in the XML folder.
        addPreferencesFromResource(R.xml.preferences);
    }
}
