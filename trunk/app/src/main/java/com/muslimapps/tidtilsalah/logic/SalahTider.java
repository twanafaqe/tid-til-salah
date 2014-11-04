package com.muslimapps.tidtilsalah.logic;

import com.muslimapps.tidtilsalah.App;

import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;



public class SalahTider {

	private Calendar fajrTid;
	private Calendar shuruqTid;
	private Calendar duhurTid;
	private Calendar asrTid;
	private Calendar maghribTid;
	private Calendar ishaTid;
	private LeasSalahTider laestSalahTider;
	private int indexIdag;
	private String nextSalah;
	private String currentSalah;
	private String location;
	
	private int fajrAdjust = 0;
	private int shuruqAdjust = 0;
	private int duhurAdjust = 0;
	private int asrAdjust = 0;
	private int maghribAdjust = 0;
	private int ishaAdjust = 0;
	
	private static SalahTider instance = null;
	
	public SalahTider()
	{
	}
	
    public static SalahTider getInstance() {
	  if(instance == null) {
			instance = new SalahTider();
	  }
	  return instance;
    }
    
	public Calendar getFajrTid()
	{
		Calendar getCalendar = Calendar.getInstance();
		getCalendar = (Calendar) fajrTid.clone();
		getCalendar.add(Calendar.MINUTE, fajrAdjust);
		return getCalendar;
	}
	
	public Calendar getShuruqTid()
	{
		Calendar getCalendar = Calendar.getInstance();
		getCalendar = (Calendar) shuruqTid.clone();
		getCalendar.add(Calendar.MINUTE, shuruqAdjust);
		return getCalendar;
	}
	public Calendar getDuhurTid()
	{
		Calendar getCalendar = Calendar.getInstance();
		getCalendar = (Calendar) duhurTid.clone();
		getCalendar.add(Calendar.MINUTE, duhurAdjust);
		return getCalendar;
	}
	public Calendar getAsrTid()
	{
		Calendar getCalendar = Calendar.getInstance();
		getCalendar = (Calendar) asrTid.clone();
		getCalendar.add(Calendar.MINUTE, asrAdjust);
		return getCalendar;
	}
	public Calendar getMaghribTid()
	{
		Calendar getCalendar = Calendar.getInstance();
		getCalendar = (Calendar) maghribTid.clone();
		getCalendar.add(Calendar.MINUTE, maghribAdjust);
		return getCalendar;
	}
	public Calendar getIshaTid()
	{
		Calendar getCalendar = Calendar.getInstance();
		getCalendar = (Calendar) ishaTid.clone();
		getCalendar.add(Calendar.MINUTE, ishaAdjust);
		return getCalendar;
	}
    
    public void setNextSalah(String _nextSalah)
    {
    	nextSalah = _nextSalah;
    }
    
    public String getNextSalah()
    {
    	return nextSalah;
    }
    
    public void setCurrentSalah(String _currentSalah)
    {
    	currentSalah = _currentSalah;
    }
    
    public String getCurrentSalah()
    {
    	return currentSalah;
    }
    
    public void setLocation(String _location)
    {
    	location = _location;
    }
    
    public String getLocation()
    {
    	return location;
    }
    
    public float getMeccaDirection()
    {
        switch(getLocation())
        {
        	case "Aarhus":
        	{
        		return (float) (135.53);
        	}
        	case "Vejle":
        	{
        		return (float) (134.31);
        	}
        	case "København":
        	{
        		return (float) (138.24);
        	}
        	case "Odense":
        	{
        		return (float) (135.17);
        	}
        	case "Grenaa":
        	{
        		return (float) (136.63);
        	}
        	
        }
        return 0;
    }
	
	public void readSalahTiderListen()
	{
    	try {
    		InputStreamReader reader = new InputStreamReader(App.getContext().getAssets().open("salah-tider.csv"));
    		laestSalahTider = new com.muslimapps.tidtilsalah.logic.LeasSalahTider(reader);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	DateFormat dateFormat = new SimpleDateFormat("dd-MM");
    	Date date = new Date();
    	
    	String datoNu = dateFormat.format(date).toString();
    	String datoFraListe;
    	
    	
    	
    	for (int i = 0; i < 365; i++) {
    		datoFraListe = laestSalahTider.getSalahTiderListe().get(i)[0].toString();
    		if (datoNu.equals(datoFraListe)) {
    			indexIdag = i;
				fajrTid = Calendar.getInstance();
				fajrTid.set(Calendar.HOUR_OF_DAY, Integer.parseInt(laestSalahTider.getSalahTiderListe().get(i)[1].substring(0, 2)));
				fajrTid.set(Calendar.MINUTE, Integer.parseInt(laestSalahTider.getSalahTiderListe().get(i)[1].substring(3,5)));
				fajrTid.set(Calendar.SECOND, 0);
				shuruqTid = Calendar.getInstance();
				shuruqTid.set(Calendar.HOUR_OF_DAY, Integer.parseInt(laestSalahTider.getSalahTiderListe().get(i)[2].substring(0, 2)));
				shuruqTid.set(Calendar.MINUTE, Integer.parseInt(laestSalahTider.getSalahTiderListe().get(i)[2].substring(3,5)));
				shuruqTid.set(Calendar.SECOND, 0);
				duhurTid = Calendar.getInstance();
				duhurTid.set(Calendar.HOUR_OF_DAY, Integer.parseInt(laestSalahTider.getSalahTiderListe().get(i)[3].substring(0, 2)));
				duhurTid.set(Calendar.MINUTE, Integer.parseInt(laestSalahTider.getSalahTiderListe().get(i)[3].substring(3,5)));
				duhurTid.set(Calendar.SECOND, 0);
				asrTid = Calendar.getInstance();
				asrTid.set(Calendar.HOUR_OF_DAY, Integer.parseInt(laestSalahTider.getSalahTiderListe().get(i)[4].substring(0, 2)));
				asrTid.set(Calendar.MINUTE, Integer.parseInt(laestSalahTider.getSalahTiderListe().get(i)[4].substring(3,5)));
				asrTid.set(Calendar.SECOND, 0);
				maghribTid = Calendar.getInstance();
				maghribTid.set(Calendar.HOUR_OF_DAY, Integer.parseInt(laestSalahTider.getSalahTiderListe().get(i)[5].substring(0, 2)));
				maghribTid.set(Calendar.MINUTE, Integer.parseInt(laestSalahTider.getSalahTiderListe().get(i)[5].substring(3,5)));
				maghribTid.set(Calendar.SECOND, 0);
				ishaTid = Calendar.getInstance();
				ishaTid.set(Calendar.HOUR_OF_DAY, Integer.parseInt(laestSalahTider.getSalahTiderListe().get(i)[6].substring(0, 2)));
				ishaTid.set(Calendar.MINUTE, Integer.parseInt(laestSalahTider.getSalahTiderListe().get(i)[6].substring(3,5)));
				ishaTid.set(Calendar.SECOND, 0);
    		}
    	}
	}
	
	public void nyDag() {
		indexIdag++;
		fajrTid.add(Calendar.DATE, 1);
		fajrTid.set(Calendar.HOUR_OF_DAY, Integer.parseInt(laestSalahTider.getSalahTiderListe().get(indexIdag)[1].substring(0, 2)));
		fajrTid.set(Calendar.MINUTE, Integer.parseInt(laestSalahTider.getSalahTiderListe().get(indexIdag)[1].substring(3,5)));
		fajrTid.set(Calendar.SECOND, 0);
		shuruqTid.add(Calendar.DATE, 1);
		shuruqTid.set(Calendar.HOUR_OF_DAY, Integer.parseInt(laestSalahTider.getSalahTiderListe().get(indexIdag)[2].substring(0, 2)));
		shuruqTid.set(Calendar.MINUTE, Integer.parseInt(laestSalahTider.getSalahTiderListe().get(indexIdag)[2].substring(3,5)));
		shuruqTid.set(Calendar.SECOND, 0);
		duhurTid.add(Calendar.DATE, 1);
		duhurTid.set(Calendar.HOUR_OF_DAY, Integer.parseInt(laestSalahTider.getSalahTiderListe().get(indexIdag)[3].substring(0, 2)));
		duhurTid.set(Calendar.MINUTE, Integer.parseInt(laestSalahTider.getSalahTiderListe().get(indexIdag)[3].substring(3,5)));
		duhurTid.set(Calendar.SECOND, 0);
		asrTid.add(Calendar.DATE, 1);
		asrTid.set(Calendar.HOUR_OF_DAY, Integer.parseInt(laestSalahTider.getSalahTiderListe().get(indexIdag)[4].substring(0, 2)));
		asrTid.set(Calendar.MINUTE, Integer.parseInt(laestSalahTider.getSalahTiderListe().get(indexIdag)[4].substring(3,5)));
		asrTid.set(Calendar.SECOND, 0);
		maghribTid.add(Calendar.DATE, 1);
		maghribTid.set(Calendar.HOUR_OF_DAY, Integer.parseInt(laestSalahTider.getSalahTiderListe().get(indexIdag)[5].substring(0, 2)));
		maghribTid.set(Calendar.MINUTE, Integer.parseInt(laestSalahTider.getSalahTiderListe().get(indexIdag)[5].substring(3,5)));
		maghribTid.set(Calendar.SECOND, 0);
		ishaTid.add(Calendar.DATE, 1);
		ishaTid.set(Calendar.HOUR_OF_DAY, Integer.parseInt(laestSalahTider.getSalahTiderListe().get(indexIdag)[6].substring(0, 2)));
		ishaTid.set(Calendar.MINUTE, Integer.parseInt(laestSalahTider.getSalahTiderListe().get(indexIdag)[6].substring(3,5)));
		ishaTid.set(Calendar.SECOND, 0);
	}
	
	public void opdaterLokation(String by) {
		setLocation(by);
		switch(by) {
			case "Aarhus":
			{
				fajrAdjust = 0;
				shuruqAdjust = 0;
				duhurAdjust = 0;
				asrAdjust = 10;
				maghribAdjust = 3;
				ishaAdjust = 0;
				break;
			}
			case "København":
			{
				fajrAdjust = -9;
				shuruqAdjust = -9;
				duhurAdjust = -9;
				asrAdjust = -9;
				maghribAdjust = -9;
				ishaAdjust = -9;
				break;
			}
			case "Vejle":
			{
				fajrAdjust = 0;
				shuruqAdjust = 0;
				duhurAdjust = 0;
				asrAdjust = 10;
				maghribAdjust = 3;
				ishaAdjust = 0;
				break;
			}
			case "Aalborg":
			{
				break;
			}
			
			case "Odense":
			{
				fajrAdjust = -1;
				shuruqAdjust = -1;
				duhurAdjust = -1;
				asrAdjust = -1;
				maghribAdjust = -1;
				ishaAdjust = -1;
				break;
			}
			
			case "Grenaa":
			{
				fajrAdjust = 0;
				shuruqAdjust = 0;
				duhurAdjust = 0;
				asrAdjust = 10;
				maghribAdjust = 3;
				ishaAdjust = 0;
				break;
			}
			
			default:
			{
				break;
			}
		}
	}
	
	public List<Calendar> getSalahTiderForSpecificDay(String location, int dayOfTheYear)
	{
		String orginalLocation = getLocation();
		opdaterLokation(location);
		List<Calendar> list = new ArrayList<Calendar>();
		for(int i = 1; i < 7; i++)
		{
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(laestSalahTider.getSalahTiderListe().get(dayOfTheYear)[i].substring(0, 2)));
			cal.set(Calendar.MINUTE, Integer.parseInt(laestSalahTider.getSalahTiderListe().get(dayOfTheYear)[i].substring(3,5)));
			list.add(cal);
		}
		
		list.get(0).add(Calendar.MINUTE, fajrAdjust);
		list.get(1).add(Calendar.MINUTE, shuruqAdjust);
		list.get(2).add(Calendar.MINUTE, duhurAdjust);
		list.get(3).add(Calendar.MINUTE, asrAdjust);
		list.get(4).add(Calendar.MINUTE, maghribAdjust);
		list.get(5).add(Calendar.MINUTE, ishaAdjust);
		
		opdaterLokation(orginalLocation);
		
		return list;
	}
	
}
