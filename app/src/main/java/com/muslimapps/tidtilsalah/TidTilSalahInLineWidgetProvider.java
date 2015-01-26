package com.muslimapps.tidtilsalah;

import android.app.ActivityManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.widget.RemoteViews;

import com.muslimapps.tidtilsalah.logic.SalahTider;

import java.text.SimpleDateFormat;

/**
 * Created by Bilal on 24-10-2014.
 */
public class TidTilSalahInLineWidgetProvider extends AppWidgetProvider {

    public static String WIDGET_CLICK = "com.muslimapps.tidtilsalah.TIDTILSALAH_WIDGET_CLICK";

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);

        try {
            SalahTider salahTider = SalahTider.getInstance();
            SimpleDateFormat ft =
                    new SimpleDateFormat("HH:mm");
            RemoteViews views = null;

            SharedPreferences sharedPrefs = PreferenceManager
                    .getDefaultSharedPreferences(context);
            String theme = sharedPrefs.getString("Tema", "Orange");
            switch (theme) {
                case "Orange": views = new RemoteViews(context.getPackageName(), R.layout.tidtilsalah_inline_widget_orange);
                    break;
                case "Black": views = new RemoteViews(context.getPackageName(), R.layout.tidtilsalah_inline_widget_black);
                    break;
                default: views = new RemoteViews(context.getPackageName(), R.layout.tidtilsalah_inline_widget_orange);
            }

            Intent intent = new Intent(WIDGET_CLICK);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            views.setOnClickPendingIntent(R.id.widget_frame_layout, pendingIntent);

            String s = ft.format(salahTider.getFajrTid().getTime());
            views.setTextViewText(R.id.fajrTidView, ft.format(salahTider.getFajrTid().getTime()));
            views.setTextViewText(R.id.shuruqTidView, ft.format(salahTider.getShuruqTid().getTime()));
            views.setTextViewText(R.id.duhurTidView, ft.format(salahTider.getDuhurTid().getTime()));
            views.setTextViewText(R.id.asrTidView, ft.format(salahTider.getAsrTid().getTime()));
            views.setTextViewText(R.id.maghribTidView, ft.format(salahTider.getMaghribTid().getTime()));
            views.setTextViewText(R.id.ishaTidView, ft.format(salahTider.getIshaTid().getTime()));

            views.setTextColor(R.id.fajrText, Color.parseColor("#ffffff"));
            views.setTextColor(R.id.fajrTidView, Color.parseColor("#ffffff"));
            views.setTextColor(R.id.shuruqText, Color.parseColor("#ffffff"));
            views.setTextColor(R.id.shuruqTidView, Color.parseColor("#ffffff"));
            views.setTextColor(R.id.duhurText, Color.parseColor("#ffffff"));
            views.setTextColor(R.id.duhurTidView, Color.parseColor("#ffffff"));
            views.setTextColor(R.id.asrText, Color.parseColor("#ffffff"));
            views.setTextColor(R.id.asrTidView, Color.parseColor("#ffffff"));
            views.setTextColor(R.id.maghribText, Color.parseColor("#ffffff"));
            views.setTextColor(R.id.maghribTidView, Color.parseColor("#ffffff"));
            views.setTextColor(R.id.ishaText, Color.parseColor("#ffffff"));
            views.setTextColor(R.id.ishaTidView, Color.parseColor("#ffffff"));

            switch (salahTider.getCurrentSalah()) {
                case "Fajr": {
                    views.setTextColor(R.id.fajrText, Color.parseColor("#eed85b"));
                    views.setTextColor(R.id.fajrTidView, Color.parseColor("#eed85b"));
                    break;
                }
                case "Shuruq": {
                    views.setTextColor(R.id.shuruqText, Color.parseColor("#eed85b"));
                    views.setTextColor(R.id.shuruqTidView, Color.parseColor("#eed85b"));
                    break;
                }
                case "Duhur": {
                    views.setTextColor(R.id.duhurText, Color.parseColor("#eed85b"));
                    views.setTextColor(R.id.duhurTidView, Color.parseColor("#eed85b"));
                    break;
                }
                case "Asr": {
                    views.setTextColor(R.id.asrText, Color.parseColor("#eed85b"));
                    views.setTextColor(R.id.asrTidView, Color.parseColor("#eed85b"));
                    break;
                }
                case "Maghrib": {
                    views.setTextColor(R.id.maghribText, Color.parseColor("#eed85b"));
                    views.setTextColor(R.id.maghribTidView, Color.parseColor("#eed85b"));
                    break;
                }
                case "Isha": {
                    views.setTextColor(R.id.ishaText, Color.parseColor("#eed85b"));
                    views.setTextColor(R.id.ishaTidView, Color.parseColor("#eed85b"));
                    break;
                }
            }

            appWidgetManager.updateAppWidget(appWidgetIds, views);

        } catch (Exception e) {

        }

    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        if (intent.getAction().equals(WIDGET_CLICK)) {
            Intent resultIntent = new Intent(context, MainActivity.class);
            TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
            stackBuilder.addParentStack(MainActivity.class);
            stackBuilder.addNextIntent(resultIntent);
            PendingIntent resultPendingIntent =
                    stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        }
    }

    private boolean isMyServiceRunning(Class<?> serviceClass,Context context) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

}
