package org.dawnsci.surfacescatter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.dawnsci.surfacescatter.AnalaysisMethodologies.FitPower;
import org.dawnsci.surfacescatter.AnalaysisMethodologies.Methodology;
import org.dawnsci.surfacescatter.TrackingMethodology.TrackerType1;

public class FittingParametersOutput {
	
	private static PrintWriter writer;

	public static void FittingParametersOutputTest (String title, int pt0, int pt1, int len0, int len1, Methodology bgMethod,
			TrackerType1 tracker, FitPower fitPower, int boundaryBox, int sliderPos, String sourceFile){
		
		try {
			File file = new File(title);
			//file.getParentFile().mkdirs(); 
			file.createNewFile();
			writer = new PrintWriter(file);
		} catch (FileNotFoundException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//dd/MM/yyyy
	    Date now = new Date();
	    String strDate = sdfDate.format(now);
	    
		writer.println("# Test file created: " + strDate);
		writer.println("# Headers: ");
		writer.println("#pt[0]	pt[1]	len[0]	len[1]	BgMethod	Tracker	FitPower	BoundaryBox 	 SliderPosition	File");
		
		
		writer.println(Integer.toString(pt0) +"	"+ Integer.toString(pt1) +"	"+ Integer.toString(len0) + 
			"	"+ Integer.toString(len1) + "	"+ AnalaysisMethodologies.toString(bgMethod) + "	"+  TrackingMethodology.toString(tracker) 
			+ "	"+ fitPower.toString() + "	"+ Integer.toString(boundaryBox)  + "	"+ Integer.toString(sliderPos)  + "	"+ sourceFile);
		
		writer.close();
	}
		
	
	
}
