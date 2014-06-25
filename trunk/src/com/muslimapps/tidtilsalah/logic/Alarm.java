package com.muslimapps.tidtilsalah.logic;

import java.util.Calendar;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;

import com.muslimapps.tidtilsalah.MainActivity;
import com.muslimapps.tidtilsalah.R;

public class Alarm extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
    	SalahTider salahTider = SalahTider.getInstance(); 
    	String alarmType = salahTider.getNextSalah();
    	String nextSalah = null;
    	Calendar nextAlarmCalendar = null;
    	
		SharedPreferences sharedPrefs = PreferenceManager
				.getDefaultSharedPreferences(context);
		
		Intent myIntent = new Intent(context, MainActivity.class);
		PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, myIntent, Intent.FLAG_ACTIVITY_NEW_TASK);
		
		String sound = sharedPrefs.getString("Notificationtone", "lydløs");
		
		Uri soundUri = Uri.parse("android.resource://"
	          + context.getPackageName() + "/raw/" + sound);
		
		//Toast.makeText(context, R.raw.adhan,Toast.LENGTH_LONG).show();
		
		Notification.Builder mNotification = new Notification.Builder(context);
		String tidtil = "Tid til " + alarmType;
		mNotification.setSmallIcon(R.drawable.ic_stat_salah);
		
		
		salahTider.setCurrentSalah(salahTider.getNextSalah());
    	switch (salahTider.getNextSalah())
    	{		
			case "Fajr":
			{
			    nextSalah = "Shuruq";
			    nextAlarmCalendar = salahTider.getShuruqTid();
				break;
			}    	
			case "Shuruq":
			{
				tidtil = "Shuruq/Doha tid";
			    nextSalah = "Duhur";
			    nextAlarmCalendar = salahTider.getDuhurTid();
				break;
			}    	
			case "Duhur":
			{
			    nextSalah = "Asr";
			    nextAlarmCalendar = salahTider.getAsrTid();
				break;
			}    	
			case "Asr":
			{
			    nextSalah = "Maghrib";
			    nextAlarmCalendar = salahTider.getMaghribTid();
				break;
			}    	
			case "Maghrib":
			{
			    nextSalah = "Isha";
			    nextAlarmCalendar = salahTider.getIshaTid();
				break;
			}    	
			case "Isha":
			{
			    salahTider.nyDag();
			    nextSalah = "Fajr";
			    nextAlarmCalendar = salahTider.getFajrTid();
				break;
			}    	
    	}
    	if (nextSalah != null && nextAlarmCalendar != null) {
    		salahTider.setNextSalah(nextSalah);
    		
    		if(sharedPrefs.getBoolean("Notification_checkbox", true))
    		{
    			mNotification.setContentTitle(tidtil);
    			mNotification.setContentText("Den mest elskede gerning hos Allah, er at udføre bønen til tiden");
    			
    			if(sound != "lydløs")
    			{    			
    				mNotification.setSound(soundUri, 1);
    			}
    			mNotification.setAutoCancel(true);
        		if(sharedPrefs.getBoolean("Vibration", true))
        		{
        			mNotification.setVibrate(new long[] { 200, 500, 200, 500, 1000 });
        		}
        		
        		mNotification.setContentIntent(pendingIntent);
    			NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
    			notificationManager.notify(0, mNotification.build()); 
    			
    		}
			
    		
//    		nextAlarmCalendar = Calendar.getInstance();
//    		nextAlarmCalendar.set(Calendar.MINUTE, nextAlarmCalendar.get(Calendar.MINUTE) + 1);
    		
    		nextAlarmCalendar.add(Calendar.MINUTE, -(Integer.valueOf(sharedPrefs.getString("tidligNotifikation", "0"))));
			
			Intent activate = new Intent(context, Alarm.class);
			AlarmManager alarms ;
			PendingIntent alarmIntent = PendingIntent.getBroadcast(context, 0, activate, 0);
			alarms = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
			alarms.set(AlarmManager.RTC_WAKEUP, nextAlarmCalendar.getTimeInMillis(), alarmIntent);
			
    	}
    	
    }
}
