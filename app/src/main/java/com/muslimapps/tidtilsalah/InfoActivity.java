package com.muslimapps.tidtilsalah;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;

public class InfoActivity extends FragmentActivity{
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.activity_info);
		
		Button facebookButton = (Button)findViewById(R.id.facebookButton);
		  facebookButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
                //Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/TidTilSalah?ref_type=bookmark"));
                //startActivity(browserIntent);

                try {
                    getApplicationContext().getPackageManager().getPackageInfo("com.facebook.katana", 0);
                    Intent browserIntentApp = new Intent(Intent.ACTION_VIEW, Uri.parse("fb://profile/1493410164204048"));
                    startActivity(browserIntentApp);
                } catch (Exception e) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/TidTilSalah?ref_type=bookmark"));
                    startActivity(browserIntent);
                }
			}
		});
		  
		  Button gmailButton = (Button) findViewById(R.id.GmailButton);
		  gmailButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String emailAdress = "tidtilsalah@gmail.com";
				String emailSubject = "Tid Til Salah";
				Intent intent = new Intent (Intent.ACTION_VIEW , Uri.parse("mailto:" + emailAdress));
				intent.putExtra(Intent.EXTRA_SUBJECT, emailSubject);
				startActivity(intent);
			}
		});

        Button anbefalButton = (Button) findViewById(R.id.AnButton);
                anbefalButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String anbefalstring = getResources().getString(R.string.Anbefal);
                        Intent sendIntent = new Intent(Intent.ACTION_VIEW);
                        sendIntent.setType("vnd.android-dir/mms-sms");
                        //sendIntent.setData(Uri.parse("sms:"));
                        sendIntent.putExtra("sms_body", anbefalstring);
                        startActivity(sendIntent);
                    }
                });

        Button vurdereButton = (Button) findViewById(R.id.VurderButton);
                vurdereButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
                        try {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                        } catch (android.content.ActivityNotFoundException anfe) {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                        }
                    }
                });

	}
}
