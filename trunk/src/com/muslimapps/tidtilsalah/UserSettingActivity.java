package com.muslimapps.tidtilsalah;

import java.io.IOException;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.widget.Toast;


public class UserSettingActivity extends PreferenceActivity implements
OnSharedPreferenceChangeListener {
	OnSharedPreferenceChangeListener listener;
	MediaPlayer playtone;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		

		addPreferencesFromResource(R.xml.settings);
		
		Preference button = (Preference)findPreference("OmButton");
		
			button.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
			                @Override
			                public boolean onPreferenceClick(Preference arg0) { 
			                    //code for what you want it to do
			                	Intent intent = new Intent(getApplicationContext(), InfoActivity.class);
			                	startActivity(intent);
			                	
			                    return true;
			                }
			            });
		
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
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }
	 
	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
			String key) {
		// TODO Auto-generated method stub
		/*if(playtone.isPlaying())
		{
			playtone.stop();
			playtone.prepareAsync();
			Toast.makeText(getApplicationContext(), "test", Toast.LENGTH_LONG).show();

		}*/		
		
		SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());   
		//Toast.makeText(getApplicationContext(), key, Toast.LENGTH_LONG).show();
		if(key.contentEquals("Notificationtone"))		
		{
			String azaanVoice = sharedPrefs.getString("Notificationtone", "lysl�s");
			if(azaanVoice != null && azaanVoice != "lydl�s")
			{
				Uri soundUri = Uri.parse("android.resource://"
				          + getApplicationContext().getPackageName() + "/raw/" + azaanVoice);
				playtone = MediaPlayer.create(getApplicationContext(), soundUri);
//				try {
//					playtone.prepare();
//				} catch (IllegalStateException | IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}	
				playtone.start();
			}
		}		
	}
}
