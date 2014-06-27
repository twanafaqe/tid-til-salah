package com.muslimapps.tidtilsalah;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import com.muslimapps.tidtilsalah.logic.SalahTider;

public class OtherDayFragment extends Fragment {
	
	private TextView fajrTidView;
	private TextView shuruqTidView;
	private TextView duhurTidView;
	private TextView asrTidView;
	private TextView maghribTidView;
	private TextView ishaTidView;
	
	public OtherDayFragment(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.other_day_fragment, container, false);
         
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
        		  getActivity(), R.array.Location, android.R.layout.simple_spinner_item );
        adapter.setDropDownViewResource(R.layout.location_spinner_item);
        final Spinner locationSpinner = (Spinner) rootView.findViewById( R.id.locationSpinner );
        locationSpinner.setAdapter( adapter );
        
        final DatePicker datePicker = (DatePicker) rootView.findViewById(R.id.datePicker);
        
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
				cal.set(Calendar.DATE, datePicker.getDayOfMonth());
				cal.set(Calendar.MONTH, datePicker.getMonth());
				List<Calendar> list = SalahTider.getInstance().getSalahTiderForSpecificDay(
									(String)locationSpinner.getSelectedItem(), cal.get((Calendar.DAY_OF_YEAR))-1);
				
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
}
