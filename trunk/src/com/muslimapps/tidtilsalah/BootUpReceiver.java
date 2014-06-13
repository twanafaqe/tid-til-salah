package com.muslimapps.tidtilsalah;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BootUpReceiver extends BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent _intent) {
        	Intent intent = new Intent(context,StartService.class);
        	context.startService(intent);
    }
}
