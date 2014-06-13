package com.muslimapps.tidtilsalah.logic;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class LeasSalahTider {
	
	private List<String[]> salahTiderListe;
	public LeasSalahTider(InputStreamReader reader) {
        String next[] = {};
        salahTiderListe = new ArrayList<String[]>();
        String[] dagListe;

        try {
            CSVReader csvReader = new CSVReader(reader);
            for(;;) {
                next = csvReader.readNext();
                if(next != null) {
                	dagListe = next[0].split(";");
                	salahTiderListe.add(dagListe);
                } else {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	public List<String[]> getSalahTiderListe() {
		return salahTiderListe;
	}
}
