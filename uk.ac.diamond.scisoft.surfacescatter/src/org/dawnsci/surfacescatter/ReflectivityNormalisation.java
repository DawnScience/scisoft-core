package org.dawnsci.surfacescatter;

import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.Maths;

public class ReflectivityNormalisation {

	public static void ReflectivityNormalisation1 (CurveStitchDataPackage csdp){
		////////////////////////////////////////
		IDataset a1 =csdp.getSplicedCurveY();
		IDataset a2 =csdp.getSplicedCurveYError();
		
		double a3 = normalisationFactor(csdp.getSplicedCurveY());
		
		IDataset splicedCurveY = Maths.multiply(a1, a3);
		IDataset splicedCurveYError = Maths.multiply(a2, a3);
		
		splicedCurveY.setErrors(splicedCurveYError);
		
		csdp.setSplicedCurveY(splicedCurveY);
		csdp.setSplicedCurveYError(splicedCurveYError);
		///////////////////////////////////////////////////
		IDataset a4 =csdp.getSplicedCurveYRaw();
		IDataset a5 =csdp.getSplicedCurveYRawError();
		
		double a6 = normalisationFactor(csdp.getSplicedCurveYRaw());
		
		IDataset splicedCurveYRaw = Maths.multiply(a4, a6);
		IDataset splicedCurveYRawError = Maths.multiply(a5, a6);
		
		splicedCurveYRaw.setErrors(splicedCurveYRawError);
		
		csdp.setSplicedCurveYRaw(splicedCurveYRaw);
		csdp.setSplicedCurveYRawError(splicedCurveYRawError);
		///////////////////////////////////////////////////
		IDataset a7 =csdp.getSplicedCurveYFhkl();
		IDataset a8 =csdp.getSplicedCurveYFhklError();
		
		double a9 = normalisationFactor(csdp.getSplicedCurveYFhkl());
		
		IDataset splicedCurveYFhkl = Maths.multiply(a7, a9);
		IDataset splicedCurveYFhklError = Maths.multiply(a8, a9);
		
		splicedCurveYFhkl.setErrors(splicedCurveYFhklError);
		
		csdp.setSplicedCurveYFhkl(splicedCurveYFhkl);
		csdp.setSplicedCurveYFhklError(splicedCurveYFhklError);
		
	}
	
	
	private static double normalisationFactor(IDataset in){
		
		double output =1;
		
		double temp=0;
		
		int noToAverage = 3;
		
		for(int i = 0; i<noToAverage; i++){
			double j = in.getDouble(i);
			temp += 1/j;
		}
		
		output = temp/noToAverage;
		
		return output;
	}
	
}
