package com.muslimapps.tidtilsalah.logic;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;

import com.muslimapps.tidtilsalah.MainActivity;
import com.muslimapps.tidtilsalah.R;
import com.muslimapps.tidtilsalah.TidTilSalahInLineWidgetProvider;

import java.util.Calendar;

public class Alarm extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
    	SalahTider salahTider = SalahTider.getInstance(); 
    	String alarmType = salahTider.getNextSalah();
    	String nextSalah = null;
    	Calendar nextAlarmCalendar = null;
    	String tidtil;
        String notificationText;
    	
		SharedPreferences sharedPrefs = PreferenceManager
				.getDefaultSharedPreferences(context);
		
		//Intent myIntent = new Intent(context, MainActivity.class);
		//PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, myIntent, 0);

        Intent resultIntent = new Intent(context, MainActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

		
		String sound = sharedPrefs.getString("Notificationtone", "lydløs");
		
		Uri soundUri = Uri.parse("android.resource://"
	          + context.getPackageName() + "/raw/" + sound);
		
		//Toast.makeText(context, R.raw.adhan,Toast.LENGTH_LONG).show();
		
		Notification.Builder mNotification = new Notification.Builder(context);
		String earlyNotification = sharedPrefs.getString("tidligNotifikation", "0");
		int tidligNotification = Integer.parseInt(earlyNotification);
		if(tidligNotification == 0)
		{
			tidtil = "Tid til " + alarmType;
		}
		else
		{
			tidtil = "Snart tid til " + alarmType;
		}
		mNotification.setSmallIcon(R.drawable.ic_stat_salah);
        notificationText = "Skynd dig til bøn, skynd dig til success!";
		
		
		salahTider.setCurrentSalah(salahTider.getNextSalah());
    	switch (salahTider.getNextSalah())
    	{		
			case "Fajr":
			{
			    nextSalah = "Shuruq";
			    nextAlarmCalendar = salahTider.getShuruqTid();
                notificationText = "Bøn er bedre end søvn!";
				break;
			}    	
			case "Shuruq":
			{
				tidtil = "Shuruq tid";
			    nextSalah = "Duhur";
			    nextAlarmCalendar = salahTider.getDuhurTid();
                notificationText = "Det er sunnah at bede Shuruq/Duha bøn";
			    sound = "lydløs";
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
    			mNotification.setContentText(notificationText);
    			
    			if(sound != "lydløs")
    			{    			
    				mNotification.setSound(soundUri, 1);
    			}
    			mNotification.setAutoCancel(true);
        		if(sharedPrefs.getBoolean("Vibration", true))
        		{
        			mNotification.setVibrate(new long[] { 100, 500, 75, 500, 75, 500, 1000});
        		}

                //Intent snoozeIntent = new Intent(context, MainActivity.class);
                //snoozeIntent.setAction("");
                //PendingIntent piSnooze = PendingIntent.getService(context, 0, snoozeIntent, 0);
        		
        		mNotification.setContentIntent(resultPendingIntent);
                //mNotification.addAction(R.drawable.ic_snooze,
                //        "Snooze", piSnooze);
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

            Intent wdigetIntent = new Intent(context,TidTilSalahInLineWidgetProvider.class);
            wdigetIntent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
            int[] ids = AppWidgetManager.getInstance(context).getAppWidgetIds(new ComponentName(context, TidTilSalahInLineWidgetProvider.class));
            wdigetIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS,ids);
            context.sendBroadcast(wdigetIntent);
    	}
    	
    }
}
