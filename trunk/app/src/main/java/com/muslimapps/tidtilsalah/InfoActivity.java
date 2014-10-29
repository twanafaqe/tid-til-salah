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
				Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/TidTilSalah?ref_type=bookmark"));
                startActivity(browserIntent);
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
		               

	}
}
