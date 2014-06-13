package com.muslimapps.tidtilsalah;

import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.widget.Toast;


public class UserSettingActivity extends PreferenceActivity implements
OnSharedPreferenceChangeListener {
	OnSharedPreferenceChangeListener listener;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		addPreferencesFromResource(R.xml.settings);
	}
	
	@Override
	protected void onResume() {
	    super.onResume();
	    // Registers a callback to be invoked whenever a user changes a preference.
	    getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
	}

	@Override
    protected void onPause() {
        super.onPause();
        // Unregisters the listener set in onResume().
        // It's best practice to unregister listeners when your app isn't using them to cut down on
        // unnecessary system overhead. You do this in onPause().
        getPreferenceScreen()
                .getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }
	 
	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
			String key) {
		// TODO Auto-generated method stub
		//Toast.makeText(getApplicationContext(), key, Toast.LENGTH_SHORT).show();
		
	}
}
