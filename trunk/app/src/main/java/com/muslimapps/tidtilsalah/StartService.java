package com.muslimapps.tidtilsalah;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Binder;
import android.os.IBinder;
import android.preference.PreferenceManager;

import com.muslimapps.tidtilsalah.logic.Alarm;
import com.muslimapps.tidtilsalah.logic.SalahTider;

import java.util.Calendar;

public class StartService extends Service {

	private final IBinder mBinder = new LocalBinder();
	public SalahTider salahTider;
	public String deviceLocation;
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return mBinder;
	}
	
	public class LocalBinder extends Binder {
		StartService getService() {
            return StartService.this;
        }
    }
	
    @Override
    public void onCreate() {
    	//Toast.makeText(getApplication(), "created", Toast.LENGTH_LONG).show();
    	PreferenceManager.setDefaultValues(this, R.xml.settings, false);
		SharedPreferences sharedPrefs = PreferenceManager
				.getDefaultSharedPreferences(this);

		String location = sharedPrefs.getString("Location", "Aarhus");
    	
		salahTider = SalahTider.getInstance();
		salahTider.readSalahTiderListen();
		salahTider.opdaterLokation(location);
		
		
		
		Calendar alarmCalendar = Calendar.getInstance();
		
		String nextSalah;
		
		if(alarmCalendar.getTimeInMillis() < salahTider.getFajrTid().getTimeInMillis()) {
			alarmCalendar = salahTider.getFajrTid();
			salahTider.setCurrentSalah("Isha");
			nextSalah = "Fajr";
		}
		else if(alarmCalendar.getTimeInMillis() < salahTider.getShuruqTid().getTimeInMillis()) {
			alarmCalendar = salahTider.getShuruqTid();
			salahTider.setCurrentSalah("Fajr");
			nextSalah = "Shuruq";
		}
		else if(alarmCalendar.getTimeInMillis() < salahTider.getDuhurTid().getTimeInMillis()) {
			alarmCalendar = salahTider.getDuhurTid();
			salahTider.setCurrentSalah("Shuruq");
			nextSalah = "Duhur";
		}
		else if(alarmCalendar.getTimeInMillis() < salahTider.getAsrTid().getTimeInMillis()) {
			alarmCalendar = salahTider.getAsrTid();
			salahTider.setCurrentSalah("Duhur");
			nextSalah = "Asr";
		}
		else if(alarmCalendar.getTimeInMillis() < salahTider.getMaghribTid().getTimeInMillis()) {
			alarmCalendar = salahTider.getMaghribTid();
			salahTider.setCurrentSalah("Asr");
			nextSalah = "Maghrib";
		}
		else if(alarmCalendar.getTimeInMillis() < salahTider.getIshaTid().getTimeInMillis()) {
			alarmCalendar = salahTider.getIshaTid();
			salahTider.setCurrentSalah("Maghrib");
			nextSalah = "Isha";
		}
		else {
			salahTider.nyDag();
			alarmCalendar = salahTider.getFajrTid();
			salahTider.setCurrentSalah("Isha");
			nextSalah = "Fajr";
		}
		
		salahTider.setNextSalah(nextSalah);
		
		//alarmCalendar = Calendar.getInstance();
		//alarmCalendar.set(Calendar.MINUTE, alarmCalendar.get(Calendar.MINUTE ) + 0);
		
		alarmCalendar.add(Calendar.MINUTE, -(Integer.valueOf(sharedPrefs.getString("tidligNotifikation", "0"))));
		
//        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        String formattedDate = df.format(alarmCalendar.getTime());
//        Toast.makeText(getApplicationContext(), sharedPrefs.getString("tidligNotifikation", "Til tiden"), Toast.LENGTH_LONG).show();
//        Toast.makeText(getApplicationContext(), formattedDate, Toast.LENGTH_LONG).show();
		
		Intent activate = new Intent(this, Alarm.class);
		AlarmManager alarms ;
		PendingIntent alarmIntent = PendingIntent.getBroadcast(this, 0, activate, 0);
		alarms = (AlarmManager) this.getSystemService(ALARM_SERVICE);
		alarms.set(AlarmManager.RTC_WAKEUP, alarmCalendar.getTimeInMillis(), alarmIntent);

        Intent intent = new Intent(this,TidTilSalahInLineWidgetProvider.class);
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        int[] ids = AppWidgetManager.getInstance(getApplication()).getAppWidgetIds(new ComponentName(getApplication(), TidTilSalahInLineWidgetProvider.class));
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS,ids);
        sendBroadcast(intent);

        intent = new Intent(this,TidTilSalahSquareWidgetProvider.class);
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        ids = AppWidgetManager.getInstance(getApplication()).getAppWidgetIds(new ComponentName(getApplication(), TidTilSalahSquareWidgetProvider.class));
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS,ids);
        sendBroadcast(intent);
    }
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
	    // We want this service to continue running until it is explicitly
	    // stopped, so return sticky.
	    return START_STICKY;
	}
	
	@Override
	public void onDestroy() {
		//Toast.makeText(getApplication(), "destroyed", Toast.LENGTH_LONG).show();
	    // Make sure our notification is gone.
		//Toast.makeText(getApplicationContext(), "Tid til Salah service destoryed", Toast.LENGTH_LONG).show();
	}

}
