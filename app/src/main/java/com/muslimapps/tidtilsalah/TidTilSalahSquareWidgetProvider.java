package com.muslimapps.tidtilsalah;

import android.app.ActivityManager;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.graphics.Color;
import android.widget.RemoteViews;

import com.muslimapps.tidtilsalah.logic.SalahTider;

import java.text.SimpleDateFormat;

/**
 * Created by Bilal on 24-10-2014.
 */
public class TidTilSalahSquareWidgetProvider extends AppWidgetProvider {
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);

        try {
            SalahTider salahTider = SalahTider.getInstance();
            SimpleDateFormat ft =
                    new SimpleDateFormat("HH:mm");
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.tidtilsalah_square_widget);
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
