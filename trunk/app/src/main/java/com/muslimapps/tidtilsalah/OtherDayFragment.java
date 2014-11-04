package com.muslimapps.tidtilsalah;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;

import com.muslimapps.tidtilsalah.logic.SalahTider;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class OtherDayFragment extends Fragment {
	
	private TextView fajrTidView;
	private TextView shuruqTidView;
	private TextView duhurTidView;
	private TextView asrTidView;
	private TextView maghribTidView;
	private TextView ishaTidView;
	public static int choosenCity = 0;
	public static Calendar pickedDate = Calendar.getInstance();
	public static Button buttonVaelgDato;
	public static Button buttonVaelgBy;
	
	public OtherDayFragment(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.other_day_fragment, container, false);
        
		ImageButton settingsButton = (ImageButton) rootView.findViewById(R.id.settingsButton);
		settingsButton.setOnClickListener(new View.OnClickListener() {
		    public void onClick(View v) {
				Intent i = new Intent(getActivity(), UserSettingActivity.class);
				startActivityForResult(i, 1);
		    }
		});
        
		buttonVaelgDato = (Button) rootView.findViewById(R.id.buttonVaelgDato);
		buttonVaelgDato.setOnClickListener(new View.OnClickListener() {
		    public void onClick(View v) {
		    	DialogFragment datePickerFragment = new DatePickerFragment();
		    	datePickerFragment.show(getFragmentManager(), "missiles");
		    }
		});
		buttonVaelgDato.setText("Dato: " + pickedDate.get(Calendar.DATE) + "/" + (pickedDate.get(Calendar.MONTH)+1) + "-" + pickedDate.get(Calendar.YEAR));
		
		buttonVaelgBy = (Button) rootView.findViewById(R.id.buttonVaelgBy);
		buttonVaelgBy.setOnClickListener(new View.OnClickListener() {
		    public void onClick(View v) {
		    	CityDialogFragment newFragment = new CityDialogFragment();
		        newFragment.show(getFragmentManager(), "missiles");
		    }
		});
		
		Resources res = getResources();
		String[] location = res.getStringArray(R.array.Location);
        int i;
        for(i=0; i < location.length; i++) {
            if( location[i].equals(SalahTider.getInstance().getLocation()) ) {
                choosenCity = i;
                break;
            }
        }
		buttonVaelgBy.setText("By: " + location[choosenCity]);

    	fajrTidView = (TextView) rootView.findViewById(R.id.fajrTidView);
    	shuruqTidView = (TextView) rootView.findViewById(R.id.shuruqTidView);
    	duhurTidView = (TextView) rootView.findViewById(R.id.duhurTidView);
    	asrTidView = (TextView) rootView.findViewById(R.id.asrTidView);
    	maghribTidView = (TextView) rootView.findViewById(R.id.maghribTidView);
    	ishaTidView = (TextView) rootView.findViewById(R.id.ishaTidView);
        
        Button searchButton = (Button) rootView.findViewById( R.id.searchButton );
        searchButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Calendar cal = Calendar.getInstance();
				cal.set(Calendar.DATE, pickedDate.get(Calendar.DAY_OF_MONTH));
				cal.set(Calendar.MONTH, pickedDate.get(Calendar.MONTH));
				
				Resources res = getResources();
				String[] location = res.getStringArray(R.array.Location);
				
				List<Calendar> list = SalahTider.getInstance().getSalahTiderForSpecificDay(
						location[choosenCity], cal.get((Calendar.DAY_OF_YEAR))-1);
				
		        SimpleDateFormat ft = 
		        	      new SimpleDateFormat ("HH:mm");
		        
		    	fajrTidView.setText(ft.format(list.get(0).getTime()));
		    	shuruqTidView.setText(ft.format(list.get(1).getTime()));
		    	duhurTidView.setText(ft.format(list.get(2).getTime()));
		    	asrTidView.setText(ft.format(list.get(3).getTime()));
		    	maghribTidView.setText(ft.format(list.get(4).getTime()));
		    	ishaTidView.setText(ft.format(list.get(5).getTime()));
			}
        	
        });

        
        return rootView;
    }
	
	@SuppressLint("ValidFragment")
	public static class CityDialogFragment extends DialogFragment {
		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
		    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		    builder.setTitle("Vælg by")
		           .setItems(R.array.Location, new DialogInterface.OnClickListener() {
		               public void onClick(DialogInterface dialog, int which) {
		            	    choosenCity = which;
							Resources res = getResources();
							String[] location = res.getStringArray(R.array.Location);
							buttonVaelgBy.setText("By: " + location[choosenCity]);
		           }
		    });
		    return builder.create();
		}
		
	}
	
	public static class DatePickerFragment extends DialogFragment
	implements DatePickerDialog.OnDateSetListener {

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
		// Use the current date as the default date in the picker
		final Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH);
		int day = c.get(Calendar.DAY_OF_MONTH);
	
		// Create a new instance of DatePickerDialog and return it
		return new DatePickerDialog(getActivity(), this, year, month, day);
		}
	
		public void onDateSet(DatePicker view, int year, int month, int day) {
			pickedDate.set(Calendar.YEAR, year);
			pickedDate.set(Calendar.MONTH, month);
			pickedDate.set(Calendar.DAY_OF_MONTH, day);
			buttonVaelgDato.setText("Dato: " + pickedDate.get(Calendar.DATE) + "/" + (pickedDate.get(Calendar.MONTH)+1) + "-" + pickedDate.get(Calendar.YEAR));
		}
	}
	
}
