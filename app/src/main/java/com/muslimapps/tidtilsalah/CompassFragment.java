package com.muslimapps.tidtilsalah;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BlurMaskFilter;
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

import com.muslimapps.tidtilsalah.logic.SalahTider;

public class CompassFragment extends Fragment implements SensorEventListener {

    // define the display assembly compass picture
    private ImageView image;
    private ImageView kaabaImage;
    private Bitmap kaabaGlow;
    private Bitmap kaaba;

    // record the compass picture angle turned
    private float currentDegree = 0f;

    // device sensor manager
    private SensorManager mSensorManager;

    public View rootView;
    public TextView testView;

    TextView tvHeading;
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        rootView =  inflater.inflate(R.layout.compass_fragment,
                container, false);

        ImageButton settingsButton = (ImageButton) rootView.findViewById(R.id.settingsButton);
        testView = (TextView) rootView.findViewById(R.id.tabText2);
        settingsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), UserSettingActivity.class);
                startActivityForResult(i, 1);
            }
        });

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();


        // our compass image
        image = (ImageView) getView().findViewById(R.id.imageViewCompass);
        kaabaImage = (ImageView) getView().findViewById(R.id.imageViewKaaba);

        // An added margin to the initial image
        int margin = 24;
        int halfMargin = margin / 2;

        // the glow radius
        int glowRadius = 100;

        // the glow color
        int glowColor = Color.rgb(238, 216, 91);

        // The original image to use
        kaaba = BitmapFactory.decodeResource(getResources(),
                R.drawable.img_kaaba);

        // extract the alpha from the source image
        Bitmap alpha = kaaba.extractAlpha();

        // The output bitmap (with the icon + glow)
        kaabaGlow = Bitmap.createBitmap(kaaba.getWidth() + margin,
                kaaba.getHeight() + margin, Bitmap.Config.ARGB_8888);

        // The canvas to paint on the image
        Canvas canvas = new Canvas(kaabaGlow);

        Paint paint = new Paint();
        paint.setColor(glowColor);

        // outer glow
        paint.setMaskFilter(new BlurMaskFilter(glowRadius, BlurMaskFilter.Blur.OUTER));
        canvas.drawBitmap(alpha, halfMargin, halfMargin, paint);

        // original icon
        canvas.drawBitmap(kaaba, halfMargin, halfMargin, null);

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

        setGlow(degree < 10 && degree > -10);

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

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // not in use
    }

    void setGlow(boolean effect) {
        if (effect)
            ((ImageView) rootView.findViewById(R.id.imageViewKaaba)).setImageBitmap(kaabaGlow);
        else
            ((ImageView) rootView.findViewById(R.id.imageViewKaaba)).setImageBitmap(kaaba);
    }
}

/*package com.muslimapps.tidtilsalah;

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
	private Sensor mSensorAccelerometer;
	private Sensor mSensorMagnetometer;
	
	public View rootView;
	public Paint paint;
	public int margin = 24;
	public int halfMargin = margin / 2;
	public Bitmap alpha;
	public Canvas canvas;
	public Bitmap src;
	public Bitmap bmp;

	TextView tvHeading;
	@Override
    public View onCreateView(LayoutInflater inflater, 
       ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        rootView =  inflater.inflate(R.layout.compass_fragment, 
                                 container, false);
        

		// our compass image
		image = (ImageView) rootView.findViewById(R.id.imageViewCompass);
		valuesAccelerometer = new float[3];
		valuesMagneticField = new float[3];
	  
		matrixR = new float[9];
		matrixI = new float[9];
		matrixValues = new float[3];
        
        mSensorManager = (SensorManager) getActivity().getSystemService(getActivity().SENSOR_SERVICE);
        mSensorAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mSensorMagnetometer = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        
        
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
        
        // outer glow
        paint.setMaskFilter(new BlurMaskFilter(100, Blur.OUTER));
        canvas.drawBitmap(alpha, halfMargin, halfMargin, paint);

        // original icon
        canvas.drawBitmap(src, halfMargin, halfMargin, null);
		
        return rootView;
   }

	@Override
	public void onResume() {
		super.onResume();
		
		// for the system's orientation sensor registered listeners
		mSensorManager.registerListener(this, mSensorAccelerometer, SensorManager.SENSOR_DELAY_UI);
	    mSensorManager.registerListener(this, mSensorMagnetometer, SensorManager.SENSOR_DELAY_UI);
	}

	@Override
	public void onPause() {
		super.onPause();
		
		// to stop the listener and save battery
		mSensorManager.unregisterListener(this);
	}

	 private float[] valuesAccelerometer;
	 private float[] valuesMagneticField;
	   
	 private float[] matrixR;
	 private float[] matrixI;
	 private float[] matrixValues;
	@Override
	public void onSensorChanged(SensorEvent event) {
		  switch(event.sensor.getType()){
		  case Sensor.TYPE_ACCELEROMETER:
		   for(int i =0; i < 3; i++){
		    valuesAccelerometer[i] = event.values[i];
		   }
		   break;
		  case Sensor.TYPE_MAGNETIC_FIELD:
		   for(int i =0; i < 3; i++){
		    valuesMagneticField[i] = event.values[i];
		   }
		   break;
		  }
		    
		  boolean success = SensorManager.getRotationMatrix(
		       matrixR,
		       matrixI,
		       valuesAccelerometer,
		       valuesMagneticField);
		  
		if (success) {
			SensorManager.getOrientation(matrixR, matrixValues);
			
			// get the angle around the z-axis rotated
			double degree = Math.toDegrees(matrixValues[0]);// - SalahTider.getInstance().getMeccaDirection();
	
			degree = degree < 0 ? degree : degree;
			//Toast.makeText(getActivity(), String.valueOf(degree), Toast.LENGTH_SHORT).show();
			// create a rotation animation (reverse turn degree degrees)
			RotateAnimation ra = new RotateAnimation(
					currentDegree, 
					-(float)degree,
					Animation.RELATIVE_TO_SELF, 0.5f, 
					Animation.RELATIVE_TO_SELF,
					0.5f);
	
			// how long the animation will take place
			ra.setDuration(210);
	
			// set the animation after the end of the reservation status
			ra.setFillAfter(true);
			
			((TextView) rootView.findViewById(R.id.tabText2)).setText(String.valueOf(degree));
	
			// Start the animation
			image.startAnimation(ra);
			currentDegree = -(float)degree;
			
			if (degree < 15 || degree > 345) {
				updateGui(true);
			}
			else {
				updateGui(false);
			}
		}
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// not in use
	}
	
	void updateGui(boolean effect) {
		if (effect)
			((ImageView) rootView.findViewById(R.id.imageViewKaaba)).setImageBitmap(bmp);
		else
			((ImageView) rootView.findViewById(R.id.imageViewKaaba)).setImageBitmap(src);
	}
}
*/