package com.muslimapps.tidtilsalah;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BlurMaskFilter;
import android.graphics.BlurMaskFilter.Blur;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.muslimapps.tidtilsalah.logic.SalahTider;

public class CompassFragment extends Fragment implements SensorEventListener {

	// define the display assembly compass picture
	private ImageView image;

	// record the compass picture angle turned
	private float currentDegree = 0f;

	// device sensor manager
	private SensorManager mSensorManager;
	
	public View rootView;
	public Paint paint;
	public int margin = 24;
	public int halfMargin = margin / 2;
	public Bitmap alpha;
	public Canvas canvas;
	public Bitmap src;
	public Bitmap bmp;
	public int glowRadius;

	TextView tvHeading;
	@Override
    public View onCreateView(LayoutInflater inflater, 
       ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        rootView =  inflater.inflate(R.layout.compass_fragment, 
                                 container, false);
        
		ImageButton settingsButton = (ImageButton) rootView.findViewById(R.id.settingsButton);
		settingsButton.setOnClickListener(new View.OnClickListener() {
		    public void onClick(View v) {
				Intent i = new Intent(getActivity(), UserSettingActivity.class);
				startActivityForResult(i, 1);
		    }
		});

        // the glow color
        int glowColor = Color.rgb(238, 216, 91);

        // The original image to use
        src = BitmapFactory.decodeResource(getResources(),
                R.drawable.img_kaaba);

        // extract the alpha from the source image
        alpha = src.extractAlpha();

        // The output bitmap (with the icon + glow)
        bmp = Bitmap.createBitmap(src.getWidth() + margin,
                src.getHeight() + margin, Bitmap.Config.ARGB_8888);

        // The canvas to paint on the image
        canvas = new Canvas(bmp);

        paint = new Paint();
        paint.setColor(glowColor);
        
		//Toast.makeText(getActivity(), String.valueOf(radius), Toast.LENGTH_SHORT).show();
        // outer glow
        paint.setMaskFilter(new BlurMaskFilter(100, Blur.OUTER));
        canvas.drawBitmap(alpha, halfMargin, halfMargin, paint);

        // original icon
        canvas.drawBitmap(src, halfMargin, halfMargin, null);
		
        return rootView;
   }
	
	@Override
	public void onStart() {
		super.onStart();
		

		// our compass image
		image = (ImageView) getView().findViewById(R.id.imageViewCompass);

		getActivity();
		// initialize your android device sensor capabilities
		mSensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
				//getSystemService(getActivity().SENSOR_SERVICE);
	}

	@Override
	public void onResume() {
		super.onResume();
		
		// for the system's orientation sensor registered listeners
		mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
				SensorManager.SENSOR_DELAY_GAME);
	}

	@Override
	public void onPause() {
		super.onPause();
		
		// to stop the listener and save battery
		mSensorManager.unregisterListener(this);
	}

	@Override
	public void onSensorChanged(SensorEvent event) {

		// get the angle around the z-axis rotated
		float degree = Math.round(event.values[0]) - SalahTider.getInstance().getMeccaDirection();

		// create a rotation animation (reverse turn degree degrees)
		RotateAnimation ra = new RotateAnimation(
				currentDegree, 
				-degree,
				Animation.RELATIVE_TO_SELF, 0.5f, 
				Animation.RELATIVE_TO_SELF,
				0.5f);

		// how long the animation will take place
		ra.setDuration(210);

		// set the animation after the end of the reservation status
		ra.setFillAfter(true);

		// Start the animation
		image.startAnimation(ra);
		currentDegree = -degree;
		glowRadius = (int) Math.abs(currentDegree);
		
		
		if (glowRadius < 15)
		{
			glowEffect(true);
		}
		else if(glowRadius < 360 && glowRadius > 345)
		{
			glowEffect(true);
		}
		else
		{
			glowEffect(false);
		}

	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// not in use
	}
	
	public void glowEffect(boolean glow)
	{
		if (glow) {
			((ImageView) rootView.findViewById(R.id.imageViewKaaba)).setImageBitmap(bmp);
		}
		else {
			((ImageView) rootView.findViewById(R.id.imageViewKaaba)).setImageBitmap(src);
		}
	}
}
