package org.dawnsci.surfacescatter;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class FittingParametersInputReader {
	
	
	private static Scanner in;
	
	public static FittingParameters reader(String title){
	
		try {
			in = new Scanner(new FileReader(title));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		    
		FittingParameters fp = new FittingParameters();
	    
		
	    while (in.hasNextLine()) {
	        String next = in.nextLine();
	    	if(!next.startsWith("#")){
		    	String[] columns = next.split("	");
	            fp.setPt0(Integer.parseInt(columns[0]));
	            fp.setPt1(Integer.parseInt(columns[1]));
	            fp.setLen0(Integer.parseInt(columns[2]));
	            fp.setLen1(Integer.parseInt(columns[3]));
	            fp.setBgMethod(AnalaysisMethodologies.toMethodology(columns[4]));
	            fp.setTracker(TrackingMethodology.toTracker1(columns[5]));
	            fp.setFitPower(AnalaysisMethodologies.toFitPower(columns[6]));
		        fp.setBoundaryBox(Integer.parseInt(columns[7]));
		        fp.setSliderPos(Integer.parseInt(columns[8]));
		        fp.setFile(columns[9]);
		        
	        }
	    }
	    
	    in.close();
	    
	    return fp;
	}
}
