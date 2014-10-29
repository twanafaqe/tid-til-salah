package com.muslimapps.tidtilsalah;


import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.muslimapps.tidtilsalah.logic.SalahTider;

public class HomeFragment extends Fragment {
	
	public SalahTider salahTider;
	private View rootView;
	private long nextSalahTime;
	private CountDownTimer countDownTimer;
	private TextView fajrTidView;
	private TextView shuruqTidView;
	private TextView duhurTidView;
	private TextView asrTidView;
	private TextView maghribTidView;
	private TextView ishaTidView;
	private TextView locationView;
	private TextView fajrText;
	private TextView shuruqText;
	private TextView duhurText;
	private TextView asrText;
	private TextView maghribText;
	private TextView ishaText;
	private TextView countDownTextView;

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        
        	rootView = inflater.inflate(R.layout.home_fragment, container, false);
        	
			ImageButton settingsButton = (ImageButton) rootView.findViewById(R.id.settingsButton);
			settingsButton.setOnClickListener(new View.OnClickListener() {
			    public void onClick(View v) {
					Intent i = new Intent(getActivity(), UserSettingActivity.class);
					startActivityForResult(i, 1);
			    }
			});
			
	    	fajrTidView = (TextView) rootView.findViewById(R.id.fajrTidView);
	    	shuruqTidView = (TextView) rootView.findViewById(R.id.shuruqTidView);
	    	duhurTidView = (TextView) rootView.findViewById(R.id.duhurTidView);
	    	asrTidView = (TextView) rootView.findViewById(R.id.asrTidView);
	    	maghribTidView = (TextView) rootView.findViewById(R.id.maghribTidView);
	    	ishaTidView = (TextView) rootView.findViewById(R.id.ishaTidView);
	    	locationView = (TextView) rootView.findViewById(R.id.locationView);
	    	
	    	
	    	fajrText = (TextView) rootView.findViewById(R.id.fajrText);
	    	shuruqText = (TextView) rootView.findViewById(R.id.shuruqText);
	    	duhurText = (TextView) rootView.findViewById(R.id.duhurText);
	    	asrText = (TextView) rootView.findViewById(R.id.asrText);
	    	maghribText = (TextView) rootView.findViewById(R.id.maghribText);
	    	ishaText = (TextView) rootView.findViewById(R.id.ishaText);
    		
	    	
        return rootView;
    }
	
	@Override
	public void onStart() {
		super.onStart();
	}
	
	@Override
	public void onResume() {
	    super.onResume();
    	salahTider = SalahTider.getInstance();
    	
        SimpleDateFormat ft = 
        	      new SimpleDateFormat ("HH:mm");
        
    	fajrTidView.setText(ft.format(salahTider.getFajrTid().getTime()));
    	shuruqTidView.setText(ft.format(salahTider.getShuruqTid().getTime()));
    	duhurTidView.setText(ft.format(salahTider.getDuhurTid().getTime()));
    	asrTidView.setText(ft.format(salahTider.getAsrTid().getTime()));
    	maghribTidView.setText(ft.format(salahTider.getMaghribTid().getTime()));
    	ishaTidView.setText(ft.format(salahTider.getIshaTid().getTime()));
    	locationView.setText(salahTider.getLocation());
    	
		fajrText.setTextColor(Color.parseColor("#ffffff"));
		fajrTidView.setTextColor(Color.parseColor("#ffffff"));
		shuruqText.setTextColor(Color.parseColor("#ffffff"));
		shuruqTidView.setTextColor(Color.parseColor("#ffffff"));
		duhurText.setTextColor(Color.parseColor("#ffffff"));
		duhurTidView.setTextColor(Color.parseColor("#ffffff"));
		asrText.setTextColor(Color.parseColor("#ffffff"));
		asrTidView.setTextColor(Color.parseColor("#ffffff"));
		maghribText.setTextColor(Color.parseColor("#ffffff"));
		maghribTidView.setTextColor(Color.parseColor("#ffffff"));
		ishaText.setTextColor(Color.parseColor("#ffffff"));
		ishaTidView.setTextColor(Color.parseColor("#ffffff"));
    	
    	switch(salahTider.getCurrentSalah())
    	{
	    	case "Fajr":
	    	{
	    		fajrText.setTextColor(Color.parseColor("#eed85b"));
	    		fajrTidView.setTextColor(Color.parseColor("#eed85b"));
	    		nextSalahTime = salahTider.getDuhurTid().getTimeInMillis();
	    		break;
	    	}
	    	case "Shuruq":
	    	{
	    		shuruqText.setTextColor(Color.parseColor("#eed85b"));
	    		shuruqTidView.setTextColor(Color.parseColor("#eed85b"));
	    		nextSalahTime = salahTider.getDuhurTid().getTimeInMillis();
	    		break;
	    	}
	    	case "Duhur":
	    	{
	    		duhurText.setTextColor(Color.parseColor("#eed85b"));
	    		duhurTidView.setTextColor(Color.parseColor("#eed85b"));
	    		nextSalahTime = salahTider.getAsrTid().getTimeInMillis();
	    		break;
	    	}
	    	case "Asr":
	    	{
	    		asrText.setTextColor(Color.parseColor("#eed85b"));
	    		asrTidView.setTextColor(Color.parseColor("#eed85b"));
	    		nextSalahTime = salahTider.getMaghribTid().getTimeInMillis();
	    		break;
	    	}
	    	case "Maghrib":
	    	{
	    		maghribText.setTextColor(Color.parseColor("#eed85b"));
	    		maghribTidView.setTextColor(Color.parseColor("#eed85b"));
	    		nextSalahTime = salahTider.getIshaTid().getTimeInMillis();
	    		break;
	    	}
	    	case "Isha":
	    	{
	    		ishaText.setTextColor(Color.parseColor("#eed85b"));
	    		ishaTidView.setTextColor(Color.parseColor("#eed85b"));
	    		nextSalahTime = salahTider.getFajrTid().getTimeInMillis();
	    		break;
	    	}
    	}
    	
		SharedPreferences sharedPrefs = PreferenceManager
				.getDefaultSharedPreferences(getActivity());

		String location = sharedPrefs.getString("Location", "NULL");
		SalahTider.getInstance().opdaterLokation(location);
    	
    	nextSalahTime -= Calendar.getInstance().getTimeInMillis();
    	
    	countDownTextView = (TextView) rootView.findViewById(R.id.countDownTextView);
    	countDownTimer = new CountDownTimer(nextSalahTime, 1000) {

    	     public void onTick(long millisUntilFinished) {
    	    	 countDownTextView.setText((millisUntilFinished / 1000 / 60 / 60) % 24 + "t, "
    	    			 					+ (millisUntilFinished / 1000 / 60) % 60 + "min, "
	    			 						+ (millisUntilFinished / 1000) % 60 + "sek");
    	     }

    	     public void onFinish() {
    	 		Intent i = getActivity().getBaseContext().getPackageManager()
    		             .getLaunchIntentForPackage( getActivity().getBaseContext().getPackageName() );
    			i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    			startActivity(i);	
    			getActivity().overridePendingTransition(0,0);
    	     }
    	  }.start();
	}
	
	@Override
	public void onPause() {
	    super.onPause();
	    countDownTimer.cancel();
	}
}
